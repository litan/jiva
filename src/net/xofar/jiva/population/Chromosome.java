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

package net.xofar.jiva.population;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.xofar.jiva.RandomGenerator;

/**
 * A Chromosome represents an individual in Genotype space
 * 
 * @author lalitp
 */
public class Chromosome<T>
{
    // if fields are added, need to remember to update getClone()
    List<Gene<T>> genes = new ArrayList<Gene<T>>();

    private Double fitnessValue;

    public Chromosome()
    {
        this.genes = new ArrayList<Gene<T>>();
    }

    public Chromosome(List<Gene<T>> genes)
    {
        this();
        addGenes(genes);
    }

    public void addGene(Gene<T> gene)
    {
        genes.add(gene);
    }

    public void addGenes(List<Gene<T>> genes)
    {
        this.genes.addAll(genes);
    }

    public double getFitnessValue()
    {
        if (fitnessValue == null) {
            throw new IllegalStateException("Fitness has not been evaluated");
        }
        return fitnessValue;
    }

    public void setFitnessValue(Double fitnessValue)
    {
        this.fitnessValue = fitnessValue;
    }

    public int size()
    {
        return genes.size();
    }

    public Chromosome<T> getClone()
    {
        Chromosome<T> clone = new Chromosome<T>();
        initClone(clone);
        return clone;
    }

    public Chromosome<T> getClone(List<Gene<T>> genes)
    {
        Chromosome<T> clone = new Chromosome<T>();
        clone.addGenes(genes);
        return clone;
    }

    protected void initClone(Chromosome<T> clone)
    {
        List<Gene<T>> genesCopy = new ArrayList<Gene<T>>();
        for (Gene<T> gene : genes) {
            Gene<T> copyOfGene = gene.getClone();
            genesCopy.add(copyOfGene);
        }
        clone.addGenes(genesCopy);
        clone.setFitnessValue(fitnessValue);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Chromosome)) {
            return false;
        }

        Chromosome<T> other = (Chromosome<T>)obj;

        if (genes.size() != other.genes.size()) {
            return false;
        }

        for (Iterator<Gene<T>> iter = genes.iterator(), iter2 = other.genes
                .iterator(); iter.hasNext();) {
            Gene<T> element1 = iter.next();
            Gene<T> element2 = iter2.next();
            if (!element1.equals(element2)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer("[");
        for (Iterator<Gene<T>> iter = genes.iterator();;) {
            Gene<T> element = iter.next();
            sb.append(element.toString());
            if (iter.hasNext()) {
                sb.append(",");
                continue;
            }
            else {
                break;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public void randomize(RandomGenerator rg)
    {
        for (Gene<T> gene : genes) {
            gene.randomize(rg);
        }
    }

    public static Chromosome<Boolean> fromBinaryGeneString(String sGenes)
    {
        Chromosome<Boolean> chr = new Chromosome<Boolean>();
        chr.addGenes(genesFromBinaryString(sGenes));
        return chr;
    }

    private static List<Gene<Boolean>> genesFromBinaryString(String sGenes)
    {
        byte[] chars = sGenes.getBytes();
        int len = chars.length;
        List<Gene<Boolean>> genes = new ArrayList<Gene<Boolean>>();
        for (int i = 0; i < len; i++) {
            BooleanGene gene = new BooleanGene();
            if (chars[i] == '1') {
                gene.setAllele(Boolean.TRUE);
            }
            else if (chars[i] == '0') {
                gene.setAllele(Boolean.FALSE);
            }
            else {
                throw new RuntimeException("Invalid boolean value: " + chars[i]);
            }
            genes.add(gene);
        }
        return genes;
    }

    public List<Gene<T>> geneSlice(int start, int end)
    {
        return genes.subList(start, end);
    }

    public int matchingGenes(Chromosome<T> other)
    {
        int score = 0;
        for (int k = 0; k < other.size(); k++) {
            if (getGene(k).equals(other.getGene(k))) score++;
        }
        return score;
    }

    private Gene<T> getGene(int i)
    {
        return genes.get(i);
    }
    
    public T getGeneValue(int index)
    {
        return genes.get(index).getAllele();
    }

}
