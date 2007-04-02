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

import net.xofar.jiva.RandomGenerator;
import net.xofar.jiva.fitness.ApgaFitnessEvaluator;
import net.xofar.jiva.fitness.Evaluator;
import net.xofar.jiva.fitness.FitnessEvaluatorImpl;
import net.xofar.jiva.fitness.FitnessFunction;
import net.xofar.jiva.operators.CrossoverOp;
import net.xofar.jiva.operators.MutationOp;
import net.xofar.jiva.operators.VariationOperator;
import net.xofar.jiva.population.BooleanGene;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.ChromosomeWithLifetime;
import net.xofar.jiva.population.Gene;
import net.xofar.jiva.replacement.ApgaReplacer;
import net.xofar.jiva.replacement.GenerationalReplacer;
import net.xofar.jiva.replacement.Replacer;
import net.xofar.jiva.selection.ParentSelector;
import net.xofar.jiva.selection.RouletteWheelSelector;
import net.xofar.jiva.selection.TournamentSelector;

public class EvolverFactory
{
    public static Evolver<Boolean> agpaEvolver(
            FitnessFunction<Boolean> fitnessFunction, RandomGenerator rg,
            double pm, double pc, int popSize)
    {
        int popDelta = (int)Math.ceil(popSize / 4.0);
        ParentSelector<Boolean> sel = new TournamentSelector<Boolean>(popDelta,
                4, rg);
        Replacer<Boolean> replacer = new ApgaReplacer<Boolean>();
        Evaluator<Boolean> evaluator = new ApgaFitnessEvaluator<Boolean>(
                fitnessFunction);
        VariationOperator<Boolean> mutOp = new MutationOp(pm, rg);
        VariationOperator<Boolean> crOp = new CrossoverOp<Boolean>(pc, rg);
        Evolver<Boolean> evolver = new SgaEvolver<Boolean>(sel, mutOp, crOp,
                replacer, evaluator);
        return evolver;
    }

    public static Evolver<Boolean> standardEvolver(
            FitnessFunction<Boolean> fitnessFunction, RandomGenerator rg,
            double pm, double pc)
    {
        ParentSelector<Boolean> sel = new RouletteWheelSelector<Boolean>(100,
                rg);
        Replacer<Boolean> replacer = new GenerationalReplacer<Boolean>();
        // replacer = new ElitistReplacer();
        Evaluator<Boolean> evaluator = new FitnessEvaluatorImpl<Boolean>(
                fitnessFunction);
        VariationOperator<Boolean> mutOp = new MutationOp(pm, rg);
        VariationOperator<Boolean> crOp = new CrossoverOp<Boolean>(pc, rg);
        Evolver<Boolean> evolver = new SgaEvolver<Boolean>(sel, mutOp, crOp,
                replacer, evaluator);
        return evolver;
    }

    public static Chromosome<Boolean> agpaPrototypeChromosome(int chrSize,
            RandomGenerator rg)
    {
        Chromosome<Boolean> proto = new ChromosomeWithLifetime<Boolean>();
        for (int j = 0; j < chrSize; j++) {
            Gene<Boolean> gene = new BooleanGene();
            proto.addGene(gene);
        }
        proto.randomize(rg);
        return proto;
    }

    public static Chromosome<Boolean> standardPrototypeChromosome(int chrSize,
            RandomGenerator rg)
    {
        Chromosome<Boolean> proto = new Chromosome<Boolean>();
        for (int j = 0; j < chrSize; j++) {
            Gene<Boolean> gene = new BooleanGene();
            proto.addGene(gene);
        }
        proto.randomize(rg);
        return proto;
    }
}
