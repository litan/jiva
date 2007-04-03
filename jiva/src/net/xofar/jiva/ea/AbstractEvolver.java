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

package net.xofar.jiva.ea;

import net.xofar.jiva.GAProblem;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Population;
import net.xofar.util.listener.EventBroadcaster;
import net.xofar.util.listener.EventListener;
import net.xofar.util.listener.NonBlockingBroadcaster;

import org.apache.log4j.Logger;

public abstract class AbstractEvolver<T>
        implements Evolver<T>
{
    private static Logger log = Logger.getLogger(AbstractEvolver.class);
    EventBroadcaster<Population<T>> broadcaster = new NonBlockingBroadcaster<Population<T>>();

    public Population<T> run(GAProblem<T> problem)
    {
        return run(problem, new EventListener<Population<T>>()
        {
            public void eventFired(Population<T> event)
            {
            // ignore
            }
        });
    }

    public Population<T> run(GAProblem<T> problem,
            EventListener<Population<T>> listener)
    {
        int numEvolutions = problem.getNumEvolutions();
        Evolver<T> evolver = this;
        broadcaster.addListener(listener);
        Population<T> pop = getInitialPopulation(problem);

        double bestSoFar = -1.0;
        double best = -1.0;

        evolver.init(pop);
        for (int i = 0; i < numEvolutions; i++) {
            if (log.isInfoEnabled()) {
                log.info(i + " - Population size: " + pop.size());
            }
            if (log.isDebugEnabled()) {
                Chromosome<T> fittest = pop.determineFittestChromosome();
                best = fittest.getFitnessValue();
                log.debug(i
                        + " - Fittest Chromosome has value (before evolution)"
                        + best);
            }
            evolver.evolve(pop);
            Chromosome<T> fittest = pop.getFittest();
            best = fittest.getFitnessValue();
            if (log.isInfoEnabled()) {
                log.info(i + " - Fittest Chromosome has value: " + best);
            }
            if (log.isInfoEnabled()) {
                log.info(i + " - Avg Population Fitness: "
                        + pop.getAvgFitness());
            }
            if (best > bestSoFar) {
                bestSoFar = best;
            }
        }
        return pop;
    }

    private Population<T> getInitialPopulation(GAProblem<T> problem)
    {
        int populationSize = problem.getPopulationSize();
        Population<T> pop = new Population<T>();
        Chromosome<T> proto = problem.getPrototypeChromosome();

        for (int i = 0; i < populationSize; i++) {
            Chromosome<T> chr = proto.getClone();
            chr.randomize(problem.getRandomGenerator());
            pop.addChromosome(chr);
        }
        return pop;
    }
}
