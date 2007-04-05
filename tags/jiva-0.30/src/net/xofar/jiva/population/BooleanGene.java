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

import net.xofar.jiva.RandomGenerator;

public class BooleanGene
        implements Gene<Boolean>
{
    Boolean allele;
    
    public void setAllele(Boolean val)
    {
        this.allele = (Boolean)val;
    }

    public Boolean getAllele()
    {
        return allele;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof BooleanGene)) {
            return false;
        }

        BooleanGene other = (BooleanGene)obj;
        return allele.equals(other.allele);
    }

    @Override
    public int hashCode()
    {
        return allele.hashCode();
    }

    @Override
    public String toString()
    {
        return allele.booleanValue() ? "1" : "0"; 
    }

    public void randomize(RandomGenerator rg)
    {
        allele = Boolean.valueOf(rg.nextBoolean());
    }

    public Gene<Boolean> getClone()
    {
        Gene<Boolean> copy =  new BooleanGene();
        copy.setAllele(getAllele());
        return copy;
    }
}
