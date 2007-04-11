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

package net.xofar.jiva.fitness;

import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.ChromosomeWithLifetime;
import net.xofar.jiva.population.Population;

public class ApgaFitnessEvaluator<T>
        extends FitnessEvaluatorImpl<T>
{
    private static final int MIN_LT = 1;
    private static final int MAX_LT = 11;
    private static double eta = (MAX_LT - MIN_LT) / 2.0;

    public ApgaFitnessEvaluator(FitnessFunction<T> fitnessFunction)
    {
        super(fitnessFunction);
    }

    public void evaluate(Population<T> currentPopulation,
            Population<T> selectedPopulation)
    {
        // first determine aggregate stats
        double avgFit = -1.0, minFit = Double.MAX_VALUE, maxFit = -1.0;
        double sum = 0.0;
        int currPopSize = 0;
        if (currentPopulation != null) {
            currPopSize = currentPopulation.size();
            for (Chromosome<T> chr : currentPopulation.getChromosomes()) {
                double fitness = chr.getFitnessValue();
                if (fitness > maxFit) {
                    maxFit = fitness;
                }
                else if (fitness < minFit) {
                    minFit = fitness;
                }
                sum += fitness;
            }
        }

        for (Chromosome<T> chr : selectedPopulation.getChromosomes()) {
            double fitness = evaluateFitness(chr);
            if (fitness > maxFit) {
                maxFit = fitness;
            }
            else if (fitness < minFit) {
                minFit = fitness;
            }
            sum += fitness;
        }

        avgFit = sum / (currPopSize + selectedPopulation.size());

        // assign lifetimes
        for (Chromosome<T> chr : selectedPopulation.getChromosomes()) {
            ChromosomeWithLifetime<T> agpaChr = (ChromosomeWithLifetime<T>)chr;
            int lt;

            if (avgFit >= agpaChr.getFitnessValue()) {
                lt = (int)(MIN_LT + eta * (agpaChr.getFitnessValue() - minFit)
                        / (avgFit - minFit));
            }
            else {
                lt = (int)(0.5 * (MIN_LT + MAX_LT) + eta
                        * (agpaChr.getFitnessValue() - avgFit)
                        / (maxFit - avgFit));
            }
            agpaChr.setLifetime(lt);
        }
    }
}
