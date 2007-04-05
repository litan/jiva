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

import java.util.List;
import java.util.ListIterator;

import net.xofar.jiva.RandomGenerator;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Gene;
import net.xofar.jiva.population.Population;

import org.apache.log4j.Logger;

/**
 * @author Lpant
 * 
 * Description
 */
public class MutationOp
        implements VariationOperator<Boolean>
{
    private static Logger log = Logger.getLogger(MutationOp.class);
    private static final long serialVersionUID = 3834028060973414706L;

    private double pm;

    RandomGenerator rgen;

    public MutationOp(double pm)
    {
        this(pm, null);
    }

    public MutationOp(double pm, RandomGenerator rgen)
    {
        this.pm = pm;
        this.rgen = rgen;
    }

    void setRandomGenerator(RandomGenerator generator)
    {
        this.rgen = generator;
    }

    public void operate(Population<Boolean> population)
    {
        List<Chromosome<Boolean>> candidateChromosomes = population
                .getChromosomes();
        if (log.isDebugEnabled()) {
            log.debug("Candidate (Incoming) Chrs:\n"
                    + candidateChromosomes.toString());
        }

        for (ListIterator<Chromosome<Boolean>> iter = candidateChromosomes
                .listIterator(); iter.hasNext();) {
            Chromosome<Boolean> chr = iter.next();

            List<Gene<Boolean>> genes = chr.geneSlice(0, chr.size());
            for (Gene<Boolean> gene : genes) {
                if (rgen.nextDouble() < pm) {
                    gene.setAllele(gene.getAllele()
                            ? false
                            : true);
                }
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Candidate (outgoing) Chrs:\n"
                    + candidateChromosomes.toString());
        }
    }

}
