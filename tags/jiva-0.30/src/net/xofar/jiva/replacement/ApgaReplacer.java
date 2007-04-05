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

import java.util.Iterator;

import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.ChromosomeWithLifetime;
import net.xofar.jiva.population.Population;

public class ApgaReplacer<T>
        implements Replacer<T>
{
    public void replace(Population<T> currentPopulation,
            Population<T> selectedPopulation)
    {
        boolean oneBestFound = false;
        double maxFit = currentPopulation.determineFittestChromosome().getFitnessValue();
        for (Iterator<Chromosome<T>> iter = currentPopulation.getChromosomes().iterator(); iter
                .hasNext();) {
            Chromosome<T> chr = iter.next();
            ChromosomeWithLifetime<T> agpaChr = (ChromosomeWithLifetime<T>)chr;
            if (agpaChr.getFitnessValue() == maxFit) {
                if (oneBestFound) {
                    agpaChr.setLifetime(agpaChr.getLifetime() - 1);
                }
                else {
                    oneBestFound = true;
                }
            }
            else {
                agpaChr.setLifetime(agpaChr.getLifetime() - 1);
            }
            if (agpaChr.getLifetime() == 0) {
                iter.remove();
            }
        }

        for (Chromosome<T> chr : selectedPopulation.getChromosomes()) {
            currentPopulation.addChromosome(chr);
        }
    }
}
