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

import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Population;
import net.xofar.jiva.samples.Knapsack;
import net.xofar.jiva.samples.SpearsScape;

public class JeevaMain
{

    public static void main(String[] args)
    {
//        GAProblem<Boolean> problem = new Knapsack();
        GAProblem<Boolean> problem = new SpearsScape();
        run(problem);
    }
    
    private static <T> void run(GAProblem<T> problem)
    {
        Population<T> finalPop = problem.getEvolver().run(problem);
        
        Chromosome<T> fittest = finalPop.determineFittestChromosome();
        double best = fittest.getFitnessValue();
        System.out.format(
                "\nFittest Chromosome in Final generation is: %s\n",
                fittest.toString());
        System.out.format(
                "Fittest Chromosome has value: %.3f\n",
                best);
    }
}
