/*
 * Copyright (C) 2005 Lalit Pant <pant.lalit@gmail.com>
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

/*
 * Created on Apr 9, 2005
 */
package net.xofar.jiva.replacement;

import net.xofar.jiva.population.Population;

public class GenerationalReplacer<T> implements Replacer<T>
{
    public void replace(Population<T> currentPopulation, Population<T> selectedPolulation)
    {
        currentPopulation.setChromosomes(selectedPolulation.getChromosomes());
    }
}
