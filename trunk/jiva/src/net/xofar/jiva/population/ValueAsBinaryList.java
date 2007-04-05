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

import java.util.List;

/**
 * @author LPant
 * 
 * Assumes that the chromosome has a list of boolean valued genes
 */
public class ValueAsBinaryList
{
    public long evaluate(Chromosome<Boolean> chr)
    {
        return evaluate(chr, 0, chr.size());
    }

    public long evaluate(Chromosome<Boolean> chr, int startIndex, int endIndex)
    {
        if (startIndex < 0 || endIndex > chr.size()) {
            throw new RuntimeException("Invalid range");
        }

        List<Gene<Boolean>> genes = chr.geneSlice(0, chr.size());

        int power = 0;
        long total = 0;
        for (int i = endIndex - 1; i >= startIndex; i--) {
            Gene<Boolean> gene = genes.get(i);
            if (gene.getAllele()) {
                total += Math.pow(2.0, (double)power);
            }
            power++;
        }
        return total;
    }
}
