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

import net.xofar.jiva.GAProblem;
import net.xofar.jiva.ea.EvolverFactory;
import net.xofar.jiva.fitness.SpearsFitnessFunction;

public class SpearsScape
        extends GAProblem<Boolean>
{
    public SpearsScape()
    {
        setPopulationSize(100);
        setNumEvolutions(200);
        setEvolver(evolver);
        setChromosomeSize(100);
        setPrototypeChromosome(EvolverFactory.agpaPrototypeChromosome(
                getChromosomeSize(), randomGenerator));
        setFitnessFunction(new SpearsFitnessFunction(100, 0.5, "linear",
                getPrototypeChromosome(),
                randomGenerator));
        
        setDescription("This is a Non-linear Numerical Optimization problem based " +
        		"on an N-dimensional landscape defined by the Spears Function.\n");        

        double pm = 0.01;
        double pc = 0.9;

        setEvolver(EvolverFactory.agpaEvolver(getFitnessFunction(),
                randomGenerator, pm, pc, getPopulationSize()));
    }

    @Override
    public Double getOptimalSolution()
    {
        return 1.0;
    }

    @Override
    public String toString()
    {
        return "Spears Landscape";
    }
}
