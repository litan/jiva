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
package net.xofar.jiva.operators;


import net.xofar.jiva.population.Population;

/**
 * Variation operators operate on selected individuals in a population to 
 * generate new individuals during the process of reproduction 
 * 
 * @author lalitp
 */
public interface VariationOperator<T>
{
    public void operate(Population<T> population);
}
