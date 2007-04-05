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

package net.xofar.jiva.operators;

import static net.xofar.jiva.TestUtils.listEquals;
import static net.xofar.jiva.TestUtils.returnConsecutiveValues;
import static org.junit.Assert.assertTrue;
import net.xofar.jiva.RandomGenerator;
import net.xofar.jiva.TestData;
import net.xofar.jiva.TestUtils;
import net.xofar.jiva.operators.CrossoverOp;
import net.xofar.jiva.population.Population;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.nonstd.UnsafeHackConcreteClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lpant
 * 
 * Description
 */
@RunWith(JMock.class)
public class TestCrossoverOp
{
    Mockery context = new Mockery()
    {
        {
            setImposteriser(new UnsafeHackConcreteClassImposteriser());
        }
    };

    Population<Boolean> pop;
    CrossoverOp<Boolean> crossover;

    @Before
    public void xsetUp()
            throws Exception
    {
        pop = new Population<Boolean>();
        crossover = new CrossoverOp<Boolean>(0.5);
        crossover.setShuffler(TestData.noOpShuffler());
    }

    @Test
    public void testCrossover()
    {
        pop.setChromosomes(TestData.getTestChromosomes());
        final RandomGenerator rg = context.mock(RandomGenerator.class);

        context.checking(new Expectations()
        {
            {
                atLeast(1).of(rg).nextDouble();
                will(onConsecutiveCalls(returnConsecutiveValues(0.31, 0.66,
                        0.71, 0.39)));
                one(rg).nextInt(with(any(int.class)));
                will(returnValue(2));
            }
        });

        crossover.setRandomGenerator(rg);
        crossover.operate(pop);
        assertTrue(TestUtils.listEquals(TestData.getExpectedChromosomes(), pop
                .getChromosomes()));
    }

    @Test
    public void testOddNumberCrossoverWithAdd()
    {
        pop.setChromosomes(TestData.getTestChromosomes());

        final RandomGenerator rg = context.mock(RandomGenerator.class);

        context.checking(new Expectations()
        {
            {
                // pc calls
                atLeast(1).of(rg).nextDouble();
                will(onConsecutiveCalls(returnConsecutiveValues(0.31, 0.45,
                        0.71, 0.39)));

                // add to odd?
                one(rg).nextBoolean();
                will(returnValue(true));

                // calls:
                // which one to add to odd
                // crossover site
                // crossover site
                atLeast(1).of(rg).nextInt(with(any(int.class)));
                will(onConsecutiveCalls(returnConsecutiveValues(0, 2, 3)));
            }
        });

        crossover.setRandomGenerator(rg);
        crossover.operate(pop);
        assertTrue(TestUtils.listEquals(TestData.getExpectedOddChromosomes(),
                pop.getChromosomes()));
    }

    @Test
    public void testOddNumberCrossoverWithRemove()
    {
        pop.setChromosomes(TestData.getTestChromosomes());

        final RandomGenerator rg = context.mock(RandomGenerator.class);

        context.checking(new Expectations()
        {
            {
                // pc calls
                atLeast(1).of(rg).nextDouble();
                will(onConsecutiveCalls(returnConsecutiveValues(0.31, 0.45,
                        0.71, 0.39)));

                // add to odd?
                one(rg).nextBoolean();
                will(returnValue(false));

                // calls:
                // which one to remove
                // crossover site
                atLeast(1).of(rg).nextInt(with(any(int.class)));
                will(onConsecutiveCalls(returnConsecutiveValues(1, 4)));
            }
        });

        crossover.setRandomGenerator(rg);
        crossover.operate(pop);
        assertTrue(listEquals(TestData.getExpectedOddChromosomesWithRemove(),
                pop.getChromosomes()));
    }

}
