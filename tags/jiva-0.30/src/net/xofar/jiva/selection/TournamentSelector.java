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

import net.xofar.jiva.RandomGenerator;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Population;

/**
 * @author Lpant
 * 
 * Selects 'howManyToSelect' elements from a supplied population based on
 * a k-way tournament
 */
public class TournamentSelector<T>
        extends AbstractSelector<T>
{
    private int k;

    public TournamentSelector(int howManyToSelect, int k, RandomGenerator rg)
    {
        super(howManyToSelect, rg);
        this.k = k;
    }

    public Population<T> select(Population<T> fromPopulation)
    {
        Population<T> toPopulation = new Population<T>();
        for (int i = 0; i < howManyToSelect; i++) {
            toPopulation.addChromosome(selectIndividual(fromPopulation));
        }
        return toPopulation;
    }

    private Chromosome<T> selectIndividual(Population<T> fromPopulation)
    {
        Chromosome<T> bestIndividual = null, tmpIndividual;
        double bestFitness = Double.NEGATIVE_INFINITY;

        int popSize = fromPopulation.size();
        for (int i = 0; i < k; i++) {
            int counter = rgen.nextInt(popSize);
            tmpIndividual = fromPopulation.getChromosomes().get(counter);
            if (tmpIndividual.getFitnessValue() > bestFitness) {
                bestIndividual = tmpIndividual;
                bestFitness = tmpIndividual.getFitnessValue();
            }
        }
        return bestIndividual.getClone();
    }
}
