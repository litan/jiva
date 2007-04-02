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
 * Created on Mar 24, 2005
 */

package net.xofar.jiva.population;

import net.xofar.jiva.RandomGenerator;

public class RealGene
        implements Gene<Double>
{
    double allele;
    double min;
    double max;

    private RealGene(double allele, double min, double max)
    {
        this.allele = allele;
        this.min = min;
        this.max = max;
    }

    public RealGene()
    {}

    public void setAllele(Double val)
    {
        this.allele = val;
    }

    public Double getAllele()
    {
        return allele;
    }

    public void randomize(RandomGenerator rg)
    {
        double rand = rg.nextDouble();
        allele =  min + rand * max;
    }

    public double getMax()
    {
        return max;
    }

    public void setMax(double max)
    {
        this.max = max;
    }

    public double getMin()
    {
        return min;
    }

    public void setMin(double min)
    {
        this.min = min;
    }

    public Gene<Double> getClone()
    {
        return new RealGene(allele, min, max);
    }
}
