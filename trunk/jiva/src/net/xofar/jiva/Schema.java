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

package net.xofar.jiva;

import java.util.List;

import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Gene;

/**
 * @author LPant
 */
public class Schema
{
    byte[] schema;

    public Schema(String schema)
    {
        this.schema = schema.getBytes();
        validateSchema();
    }

    private void validateSchema()
    {
        for (byte sElem : schema) {
            if (sElem != '0' && sElem != '1' && sElem != '*') {
                throw new RuntimeException("Invalid Schema char: " + sElem);
            }
        }
    }

    public boolean matches(Chromosome<Boolean> chr)
    {
        int len = schema.length;
        int chrLen = chr.size();
        if (len != chrLen) {
            return false;
        }
        boolean retVal = true;
        List<Gene<Boolean>> genes = chr.geneSlice(0, len);
        
        for (int i = 0; i < schema.length; i++) {
            Gene<Boolean> gene = genes.get(i);
            if (schema[i] == '*') {
                continue;
            }
            else if (schema[i] == '0') {
                retVal = ((Boolean)gene.getAllele().equals(Boolean.FALSE));
                if (!retVal) {
                    break;
                }
            }
            else {
                retVal = ((Boolean)gene.getAllele().equals(Boolean.TRUE));
                if (!retVal) {
                    break;
                }
            }
        }
        return retVal;
    }
}
