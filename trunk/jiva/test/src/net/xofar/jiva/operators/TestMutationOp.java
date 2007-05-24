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

import static net.xofar.util.TestUtils.*;
import static org.junit.Assert.assertTrue;
import net.xofar.jiva.RandomGenerator;
import net.xofar.jiva.TestData;
import net.xofar.jiva.operators.MutationOp;
import net.xofar.jiva.population.Population;
import net.xofar.util.TestUtils;

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
public class TestMutationOp
{
    Mockery context = new Mockery()
    {
        {
            setImposteriser(new UnsafeHackConcreteClassImposteriser());
        }
    };

    Population<Boolean> pop;
    MutationOp mutation;

    @Before
    public void setUp()
            throws Exception
    {
        pop = new Population<Boolean>();
        mutation = new MutationOp(0.01);
    }

    @Test
    public void testMutation()
    {
        pop.setChromosomes(TestData.getTestChromosomes());

        final RandomGenerator rg = context.mock(RandomGenerator.class);

        context.checking(new Expectations()
        {
            {
                atLeast(1).of(rg).nextDouble();
                will(onConsecutiveCalls(returnConsecutiveValues(0.4, 0.4, 0.4,
                        0.4, 0.4, 0.001, 0.4, 0.4, 0.4, 0.4, 0.4, 0.4, 0.4,
                        0.4, 0.4, 0.4, 0.4, 0.4, 0.4, 0.4, 0.4, 0.4, 0.4, 0.001)));
            }
        });

        mutation.setRandomGenerator(rg);
        mutation.operate(pop);
        assertTrue(TestUtils.listEquals(TestData.getExpectedMutatedChromosomes(),
                pop.getChromosomes()));

    }
}
