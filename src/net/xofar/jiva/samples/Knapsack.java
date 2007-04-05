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

package net.xofar.jiva.samples;

import java.util.Arrays;
import java.util.List;

import net.xofar.jiva.GAProblem;
import net.xofar.jiva.ea.EvolverFactory;
import net.xofar.jiva.fitness.FitnessFunction;
import net.xofar.jiva.population.Chromosome;

public class Knapsack
        extends GAProblem<Boolean>
{
    List<Double> itemValues, itemCosts;
    Double maxCost;
    private boolean apga = true;

    public Knapsack()
    {
        // maxCost = 7.1;
        // itemValues = Arrays.asList(1.0, 5.0, 3.0, 7.0);
        // itemCosts = Arrays.asList(5.0, 2.0, 2.5, 3.0);

        // maxCost = 100.0;
        // itemValues = Arrays.asList(40.0, 35.0, 18.0, 4.0, 10.0, 2.0);
        // itemCosts = Arrays.asList(100.0, 50.0, 45.0, 20.0, 10.0, 5.0);

        maxCost = 17.0;
        itemValues = Arrays.asList(4.0, 4.0, 4.0, 4.0, 4.0, 5.0, 5.0, 5.0,
                10.0, 10.0, 11.0, 11.0, 13.0);
        itemCosts = Arrays.asList(3.0, 3.0, 3.0, 3.0, 3.0, 4.0, 4.0, 4.0, 7.0,
                7.0, 8.0, 8.0, 9.0);

        setDescription("This is a Combinitorial Optimization problem.\n\nThe general idea"
                + " is that we want to pack a Knapsack with a given set of items. Each item"
                + " has a cost (its volume) and a value assigned to it. The Knapsack has a "
                + " given (max) volume. \n\nThe problem is - to pack the Knapsack in such a way that"
                + " the value of items in the Knapsack is maximized - given the cost (volume)"
                + " constraint on the Knapsack. \n\nThe Parameters for the current problem are:\n"
                + "Max Cost: 17\n"
                + "Item Costs: 3.0, 3.0, 3.0, 3.0, 3.0, 4.0, 4.0, 4.0, 7.0, 7.0, 8.0, 8.0, 9.0\n"
                + "Item Values: 4.0, 4.0, 4.0, 4.0, 4.0, 5.0, 5.0, 5.0, 10.0, 10.0, 11.0, 11.0, 13.0");
        
        setPopulationSize(40);
        setNumEvolutions(50);
        setChromosomeSize(itemCosts.size());
        setFitnessFunction(new Fitness());
        double pm = 0.1; // 1.0 / getPopulationSize();
        double pc = 0.9;

        if (apga) {
            setPrototypeChromosome(EvolverFactory.agpaPrototypeChromosome(
                    getChromosomeSize(), randomGenerator));

            setEvolver(EvolverFactory.agpaEvolver(getFitnessFunction(),
                    randomGenerator, pm, pc, getPopulationSize()));
        }
        else {
            setPrototypeChromosome(EvolverFactory.standardPrototypeChromosome(
                    getChromosomeSize(), randomGenerator));

            setEvolver(EvolverFactory.standardEvolver(getFitnessFunction(),
                    randomGenerator, pm, pc, getPopulationSize()));
        }
    }

    class Fitness
            implements FitnessFunction<Boolean>
    {
        public double evaluate(Chromosome<Boolean> chr)
        {
            double value = 0, cost = 0;
            for (int i = 0; i < chr.size(); i++) {
                if (chr.getGeneValue(i)) {
                    if (cost + itemCosts.get(i) <= maxCost) {
                        value += itemValues.get(i);
                    }
                    else {
                        value = 0;
                    }
                    cost += itemCosts.get(i);
                }
            }
            return value;
        }
    }

    @Override
    public Double getOptimalSolution()
    {
        return 24.0;
    }

    @Override
    public String toString()
    {
        return "Knapsack Packing";
    }
}
