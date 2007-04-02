/*
 * Copyright (C) 2007 Lalit Pant <pant.lalit@gmail.com>
 *
 * The contents of this file are subject to the GNU General Public License 
 * Version 2 or later (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.gnu.org/copyleft/gpl.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 */

package net.xofar.jiva.population;

import java.util.ArrayList;
import java.util.List;

public class Population<T>
{
    List<Chromosome<T>> chromosomes = new ArrayList<Chromosome<T>>();
    Chromosome<T> fittest;
    double avgFitness = -1.0;
    boolean statsInfoStale;
    private int evaluations;
    
    public List<Chromosome<T>> getChromosomes()
    {
        return chromosomes;
    }

    public void setChromosomes(List<Chromosome<T>> chromosomes)
    {
        this.chromosomes = chromosomes;
    }

    public Chromosome<T> determineFittestChromosome()
    {
        Chromosome<T> fittestChr = null;
        double fittestVal = Double.NEGATIVE_INFINITY;
        for (Chromosome<T> chr : chromosomes) {
            double thisFitness = chr.getFitnessValue();
            if (thisFitness > fittestVal) {
                fittestVal = thisFitness;
                fittestChr = chr;
            }
        }
        return fittestChr;
    }

    public void addChromosome(Chromosome<T> chr)
    {
        chromosomes.add(chr);
        setStatsInfoStale(true);
    }

    public int size()
    {
        return chromosomes.size();
    }
    
    public List<Double> getFitnessValues()
    {
        List<Double> vals = new ArrayList<Double>();
        for (Chromosome<T> chr : chromosomes) {
            vals.add(chr.getFitnessValue());
        }
        return vals;
    }

    public void determineStats()
    {
        fittest = null;
        double fittestVal = Double.NEGATIVE_INFINITY;
        double sum = 0;
        for (Chromosome<T> chr : chromosomes) {
            double thisFitness = chr.getFitnessValue();
            sum += thisFitness;
            if (thisFitness > fittestVal) {
                fittestVal = thisFitness;
                fittest = chr;
            }
        }
        avgFitness = sum / size();
        setStatsInfoStale(false);
    }

    public boolean isStatsInfoStale()
    {
        return statsInfoStale;
    }

    public void setStatsInfoStale(boolean statsInfoStale)
    {
        this.statsInfoStale = statsInfoStale;
    }

    public Chromosome<T> getFittest()
    {
        if (isStatsInfoStale()) {
            throw new IllegalStateException("Stats info is stale.");
        }
        return fittest;
    }

    public double getAvgFitness()
    {
        if (isStatsInfoStale()) {
            throw new IllegalStateException("Stats info is stale.");
        }
        return avgFitness;
    }

    public void setEvaluations(int evaluations)
    {
        this.evaluations = evaluations;
    }
    
    public int getEvaluations()
    {
        return evaluations;
    }
}
