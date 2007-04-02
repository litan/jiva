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
package net.xofar.jiva.selection;


import java.util.ArrayList;
import java.util.List;

import net.xofar.jiva.RandomGenerator;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Population;


/**
 * @author Lpant
 * 
 * Selects 'howManyToSelect' elements from a supplied population based on
 * the roulette wheel algorithm
 */
public class RouletteWheelSelector<T>
        extends AbstractSelector<T>
{
    public RouletteWheelSelector(int howManyToSelect, RandomGenerator rg)
    {
        super(howManyToSelect, rg);
    }
    
    public Population<T> select(Population<T> fromPopulation)
    {
        Population<T> toPopulation = new Population<T>();
        int popSize = fromPopulation.size();
        List<Chromosome<T>> chrs = fromPopulation.getChromosomes();
        List<Double> probVals = new ArrayList<Double>();
        List<Double> cumProbs = new ArrayList<Double>();
        double fitnessSum = 0.0;
        
        for (Chromosome<T> chr : chrs) {
            double fitness = chr.getFitnessValue();
            probVals.add(fitness);
            fitnessSum += fitness;
        }
        
        for (int i = 0; i < popSize; i++) {
            double prob = probVals.get(i) / fitnessSum;
            probVals.set(i, prob);
        }
        
        cumProbs.add(probVals.get(0));
        for (int i = 1; i < popSize; i++) {
            cumProbs.add(probVals.get(i) + cumProbs.get(i-1));
        }
        
        for (int i = 0; i < howManyToSelect; i++) {
            int selected = spinWheel(cumProbs);
            toPopulation.addChromosome(chrs.get(selected).getClone());
        }
        return toPopulation;
    }

    private int spinWheel(List<Double> cumProbs)
    {
        double spinResult = rgen.nextDouble();
        int size = cumProbs.size();
        
        for (int i = 0; i < size; i++) {
            if (spinResult < cumProbs.get(i)) {
                return i;
            }
        }
        throw new IllegalStateException("Spinwheel problem");
    }
}
