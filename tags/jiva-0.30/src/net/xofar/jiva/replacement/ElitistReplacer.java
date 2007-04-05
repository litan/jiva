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
package net.xofar.jiva.replacement;

import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Population;

public class ElitistReplacer<T>
        extends GenerationalReplacer<T>
{
    public void replace(Population<T> currentPopulation,
            Population<T> selectedPolulation)
    {
        // if the best individual from the initail population is not in the
        // selected population, add it in
        // then call into the GenerationalReplacer
        Chromosome<T> oldFittest = currentPopulation.determineFittestChromosome().getClone();
        double oldFittestVal = oldFittest.getFitnessValue();
        Chromosome<T> newFittest = selectedPolulation.determineFittestChromosome();
        double newFittest0 = newFittest.getFitnessValue();
        if (oldFittestVal > newFittest0) {
            selectedPolulation.addChromosome(oldFittest);
//            newFittest = selectedPolulation.determineFittestChromosome();
//            double newFittest1 = newFittest.getFitnessValue();
//
//            if (oldFittestVal != newFittest1) {
//                System.out.println("************");
//                System.out.println("Old fittest val: " + oldFittestVal);
//                System.out.println("New fittest val after evolve(): "
//                        + newFittest0);
//                System.out
//                        .println("New fittest val after add() of old fittest: "
//                                + newFittest1);
//                System.out.println("************");
//                throw new RuntimeException("Problemo");
//            }
        }
        super.replace(currentPopulation, selectedPolulation);
    }
}
