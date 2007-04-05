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
 * Created on Feb 12, 2005
 */

package net.xofar.jiva;

import java.util.ArrayList;
import java.util.List;

import net.xofar.jiva.Shuffler;
import net.xofar.jiva.population.Chromosome;

/**
 * @author Lpant
 * 
 * Description
 */
public class TestData
{
    public static List<Chromosome<Boolean>> getTestChromosomes()
    {
        List<Chromosome<Boolean>> chrs = new ArrayList<Chromosome<Boolean>>();
        Chromosome<Boolean> chr = null;
        chr = Chromosome.fromBinaryGeneString("001100");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("001101");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("101101");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("100001");
        chrs.add(chr);
        return chrs;
    }

    public static List<Chromosome<Boolean>> getExpectedChromosomes()
    {
        List<Chromosome<Boolean>> chrs = new ArrayList<Chromosome<Boolean>>();
        Chromosome<Boolean> chr = null;
        chr = Chromosome.fromBinaryGeneString("001101");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("101101");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("000001");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("101100");
        chrs.add(chr);
        return chrs;
    }

    /**
     * @return
     */
    public static List<Chromosome<Boolean>> getTestOddChromosomes()
    {
        return getTestChromosomes();
        // List<Chromosome> chrs = new ArrayList<Chromosome>();
        // chrs.addAll(getTestChromosomes());
        // Chromosome chr = Chromosome.fromBinaryGeneString("101001");
        // chrs.add(chr);
        // return chrs;
    }

    /**
     * @return
     */
    public static List<Chromosome<Boolean>> getExpectedOddChromosomes()
    {
        List<Chromosome<Boolean>> chrs = new ArrayList<Chromosome<Boolean>>();
        Chromosome<Boolean> chr = null;
        chr = Chromosome.fromBinaryGeneString("001101");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("001100");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("100101");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("101001");
        chrs.add(chr);
        return chrs;
    }

    public static List<Chromosome<Boolean>> getExpectedOddChromosomesWithRemove()
    {
        List<Chromosome<Boolean>> chrs = new ArrayList<Chromosome<Boolean>>();
        Chromosome<Boolean> chr = null;
        chr = Chromosome.fromBinaryGeneString("101101");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("001101");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("001101");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("100000");
        chrs.add(chr);
        return chrs;
    }

    public static Shuffler noOpShuffler()
    {
        return new Shuffler()
        {
            public void shuffle(List<?> list)
            {
                return;
            }
        };
    }

    public static List<Chromosome<Boolean>> getExpectedMutatedChromosomes()
    {
        List<Chromosome<Boolean>> chrs = new ArrayList<Chromosome<Boolean>>();
        Chromosome<Boolean> chr = null;
        chr = Chromosome.fromBinaryGeneString("001101");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("001101");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("101101");
        chrs.add(chr);
        chr = Chromosome.fromBinaryGeneString("100000");
        chrs.add(chr);
        return chrs;
    }
}
