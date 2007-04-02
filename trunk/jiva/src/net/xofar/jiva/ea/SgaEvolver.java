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


import net.xofar.jiva.fitness.Evaluator;
import net.xofar.jiva.operators.VariationOperator;
import net.xofar.jiva.population.Population;
import net.xofar.jiva.replacement.Replacer;
import net.xofar.jiva.selection.ParentSelector;

import org.apache.log4j.Logger;


public class SgaEvolver<T>
        extends AbstractEvolver<T>
{
    private static Logger log = Logger.getLogger(SgaEvolver.class);

    ParentSelector<T> selector;
    VariationOperator<T> mutator;
    VariationOperator<T> crossator;
    Replacer<T> replacer;
    Evaluator<T> evaluator;

    public SgaEvolver(ParentSelector<T> selector, VariationOperator<T> mutator,
            VariationOperator<T> crossator, Replacer<T> replacer,
            Evaluator<T> evaluator)
    {
        this.selector = selector;
        this.mutator = mutator;
        this.crossator = crossator;
        this.replacer = replacer;
        this.evaluator = evaluator;
    }

    public void evolve(Population<T> pop)
    {
        evolveWorker(pop);
        broadcaster.broadcastEvent(pop);
    }

    protected void evolveWorker(Population<T> pop)
    {
        if (log.isDebugEnabled()) {
            log.debug("Incoming Pop. Fitness:\n" + pop.getFitnessValues());
        }
        
        Population<T> newPop = selector.select(pop);
        newPop.setStatsInfoStale(true);
        
        if (log.isDebugEnabled()) {
            log.debug("Pop. Fitness after selection:\n"
                    + newPop.getFitnessValues());
        }
        crossator.operate(newPop);
        if (log.isDebugEnabled()) {
            log.debug("Pop. Fitness after crossover:\n"
                    + newPop.getFitnessValues());
        }
        mutator.operate(newPop);
        if (log.isDebugEnabled()) {
            log.debug("Pop. Fitness after mutation:\n"
                    + newPop.getFitnessValues());
        }
        evaluator.evaluate(pop, newPop);
        replacer.replace(pop, newPop);
        pop.setEvaluations(evaluator.getEvaluations());
        pop.determineStats();
    }

    public void init(Population<T> pop)
    {
        evaluator.evaluate(null, pop);
    }
}
