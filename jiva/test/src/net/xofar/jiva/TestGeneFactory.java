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
 * Created on Feb 2, 2005
 */

package net.xofar.jiva;

import java.util.Arrays;

import junit.framework.TestCase;
import net.xofar.jiva.population.BooleanGene;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Gene;

/**
 * @author LPant
 * 
 * Description
 */
public class TestGeneFactory
        extends TestCase
{

    public void testAlternateGenes()
    {
        String sGenes = "010101";
        Chromosome<Boolean> actual = Chromosome.fromBinaryGeneString(sGenes);

        Gene<Boolean>[] expected = new Gene[sGenes.length()];
        expected[0] = new BooleanGene();
        expected[0].setAllele(Boolean.FALSE);
        expected[1] = new BooleanGene();
        expected[1].setAllele(Boolean.TRUE);
        expected[2] = new BooleanGene();
        expected[2].setAllele(Boolean.FALSE);
        expected[3] = new BooleanGene();
        expected[3].setAllele(Boolean.TRUE);
        expected[4] = new BooleanGene();
        expected[4].setAllele(Boolean.FALSE);
        expected[5] = new BooleanGene();
        expected[5].setAllele(Boolean.TRUE);

        Chromosome<Boolean> expectedChr = new Chromosome<Boolean>();
        expectedChr.addGenes(Arrays.asList(expected));

        assertEquals(expectedChr, actual);
    }

}
