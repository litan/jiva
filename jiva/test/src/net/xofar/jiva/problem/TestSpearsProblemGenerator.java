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
 * Created on Apr 1, 2005
 */

package net.xofar.jiva.problem;

import junit.framework.TestCase;
import net.xofar.jiva.RandomGenerator;
import net.xofar.jiva.StockRandomGenerator;
import net.xofar.jiva.fitness.SpearsFitnessFunction;
import net.xofar.jiva.population.BooleanGene;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Gene;

public class TestSpearsProblemGenerator
        extends TestCase
{
    SpearsFitnessFunction func;
    Chromosome proto;
    private RandomGenerator rg;

    @Override
    protected void setUp()
            throws Exception
    {
        rg = new StockRandomGenerator();
        proto = new Chromosome();
    }

    public void test1()
    {
        for (int j = 0; j < 10; j++) {
            Gene gene = new BooleanGene();
            proto.addGene(gene);
        }
        proto.randomize(rg);

        func = new SpearsFitnessFunction(1, 10, "linear", proto, rg);
//        proto.randomize();
        System.out.println(func.evaluate(proto));
    }

}
