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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.xofar.jiva.RandomGenerator;
import net.xofar.jiva.Shuffler;
import net.xofar.jiva.ShufflerImpl;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Gene;
import net.xofar.jiva.population.Population;
import net.xofar.util.collection.Pair;

/**
 * @author Lpant
 * 
 * Probabilistically combines two parents to form two offsprongs
 */
public class CrossoverOp<T>
        implements VariationOperator<T>
{

    private static final long serialVersionUID = 3689355398960722487L;
    private double pc;
    RandomGenerator rgen;
    Shuffler shuffler;

    public CrossoverOp(double pc)
    {
        this(pc, null);
    }

    public CrossoverOp(double pc, RandomGenerator rgen)
    {
        this.pc = pc;
        this.rgen = rgen;
        this.shuffler = new ShufflerImpl();
    }

    public void operate(Population<T> population)
    {
        List<Chromosome<T>> candidateChromosomes = population.getChromosomes();
        List<Chromosome<T>> selectedForCrossover = new ArrayList<Chromosome<T>>();

        Chromosome<T> chr = null;
        for (Iterator<Chromosome<T>> iter = candidateChromosomes.iterator(); iter
                .hasNext();) {
            chr = iter.next();
            if (rgen.nextDouble() < pc) {
                // select for crossover
                selectedForCrossover.add(chr);
                iter.remove();
            }
        }

        // need to take care of odd number of chrs
        if (selectedForCrossover.size() % 2 != 0) {
            // odd number of selected chromosomes
            boolean add;
            if (candidateChromosomes.size() == 0) {
                add = false;
            }
            else {
                add = rgen.nextBoolean();
            }
            if (add) {
                chr = candidateChromosomes.remove(rgen
                        .nextInt(candidateChromosomes.size()));
                selectedForCrossover.add(chr);
            }
            else {
                chr = selectedForCrossover.remove(rgen
                        .nextInt(selectedForCrossover.size()));
                candidateChromosomes.add(chr);
            }
        }

        // randomly pair selected chromosomes
        // first shuffle them
        shuffler.shuffle(selectedForCrossover);

        // then cross them over in pairs
        for (int i = 0; i < selectedForCrossover.size(); i += 2) {
            Chromosome<T> firstChr = selectedForCrossover.get(i);
            Chromosome<T> secondChr = selectedForCrossover.get(i + 1);
            Pair<Chromosome<T>, Chromosome<T>> children = crossover(firstChr,
                    secondChr);
            candidateChromosomes.add(children.e1);
            candidateChromosomes.add(children.e2);
        }
    }

    /**
     * @param firstChr
     * @param secondChr
     */
    private Pair<Chromosome<T>, Chromosome<T>> crossover(
            Chromosome<T> firstChr, Chromosome<T> secondChr)
    {
        int size = firstChr.size();
        int crossoverSite = rgen.nextInt(size);

        List<Gene<T>> gs1Head = new ArrayList<Gene<T>>(size);
        gs1Head.addAll(firstChr.geneSlice(0, crossoverSite));
        List<Gene<T>> gs1Tail = new ArrayList<Gene<T>>();
        gs1Tail.addAll(firstChr.geneSlice(crossoverSite, size));

        List<Gene<T>> gs2Head = new ArrayList<Gene<T>>(size);
        gs2Head.addAll(secondChr.geneSlice(0, crossoverSite));
        List<Gene<T>> gs2Tail = new ArrayList<Gene<T>>();
        gs2Tail.addAll(secondChr.geneSlice(crossoverSite, size));

        gs1Head.addAll(gs2Tail);
        gs2Head.addAll(gs1Tail);

        Chromosome<T> chr1 = firstChr.getClone(gs1Head);
        Chromosome<T> chr2 = firstChr.getClone(gs2Head);
        return new Pair<Chromosome<T>, Chromosome<T>>(chr1, chr2);
    }

    void setRandomGenerator(RandomGenerator generator)
    {
        this.rgen = generator;
    }

    void setShuffler(Shuffler shuffler)
    {
        this.shuffler = shuffler;
    }

}
