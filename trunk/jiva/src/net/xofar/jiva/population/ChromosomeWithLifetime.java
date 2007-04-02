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
 * Created on Apr 11, 2005
 */
package net.xofar.jiva.population;

import java.util.List;

public class ChromosomeWithLifetime<T> extends Chromosome<T>
{
    private int lifetime;

    public ChromosomeWithLifetime(/*Environment<T> env*/)
    {
        super(/*env*/);
    }

    public int getLifetime()
    {
        return lifetime;
    }

    public void setLifetime(int lifetime)
    {
        this.lifetime = lifetime;
    }

    
    
    @Override
    public Chromosome<T> getClone(List<Gene<T>> genes)
    {
        ChromosomeWithLifetime<T> clone = new ChromosomeWithLifetime<T>();
        clone.addGenes(genes);
        return clone;
    }

    @Override
    public ChromosomeWithLifetime<T> getClone()
    {
        ChromosomeWithLifetime<T> clone = new ChromosomeWithLifetime<T>();
        initClone(clone);
        return clone;
    }

    @Override
    protected void initClone(Chromosome<T> clone)
    {
        super.initClone(clone);
        ChromosomeWithLifetime<T> copy = (ChromosomeWithLifetime<T>)clone;
        copy.setLifetime(getLifetime());
    }
}
