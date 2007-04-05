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

package net.xofar.jiva;

import java.util.ArrayList;
import java.util.List;

import net.xofar.jiva.ea.Evolver;
import net.xofar.jiva.fitness.FitnessFunction;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.samples.Knapsack;
import net.xofar.jiva.samples.SpearsScape;

public abstract class GAProblem<T>
{
    protected int populationSize;
    protected FitnessFunction<T> fitnessFunction;
    protected int numEvolutions;
    protected int ChromosomeSize;
    protected Evolver<T> evolver;
    protected Chromosome<T> prototypeChromosome;
    protected RandomGenerator randomGenerator;
    String description;

    public GAProblem()
    {
        randomGenerator = new StockRandomGenerator();
    }

    public int getPopulationSize()
    {
        return populationSize;
    }

    public void setPopulationSize(int populationSize)
    {
        this.populationSize = populationSize;
    }

    public FitnessFunction<T> getFitnessFunction()
    {
        return fitnessFunction;
    }

    public void setFitnessFunction(FitnessFunction<T> fitnessFunction)
    {
        this.fitnessFunction = fitnessFunction;
    }

    public int getNumEvolutions()
    {
        return numEvolutions;
    }

    public void setNumEvolutions(int numEvolutions)
    {
        this.numEvolutions = numEvolutions;
    }

    public int getChromosomeSize()
    {
        return ChromosomeSize;
    }

    public void setChromosomeSize(int chromosomeSize)
    {
        ChromosomeSize = chromosomeSize;
    }

    public Evolver<T> getEvolver()
    {
        return evolver;
    }

    public void setEvolver(Evolver<T> evolver)
    {
        this.evolver = evolver;
    }

    public Chromosome<T> getPrototypeChromosome()
    {
        return prototypeChromosome;
    }

    public void setPrototypeChromosome(Chromosome<T> prototypeChromosome)
    {
        this.prototypeChromosome = prototypeChromosome;
    }

    public RandomGenerator getRandomGenerator()
    {
        return randomGenerator;
    }

    public void setRandomGenerator(RandomGenerator randomGenerator)
    {
        this.randomGenerator = randomGenerator;
    }
    
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public static GAProblem<?>[] getProblems()
    {
        List<GAProblem<?>> problems = new ArrayList<GAProblem<?>>();
        problems.add(new Knapsack());
        problems.add(new SpearsScape());
        return problems.toArray(new GAProblem<?>[]{});
    }

    public abstract Double getOptimalSolution();
    
    @Override
    public abstract String toString();
}
