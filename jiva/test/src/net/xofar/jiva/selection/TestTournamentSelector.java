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

package net.xofar.jiva.selection;

import static net.xofar.util.TestUtils.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.xofar.jiva.RandomGenerator;
import net.xofar.jiva.TestData;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Population;
import net.xofar.jiva.selection.TournamentSelector;
import net.xofar.util.TestUtils;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.nonstd.UnsafeHackConcreteClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * Description
 * 
 * @author lalitp
 */
@RunWith(JMock.class)
public class TestTournamentSelector
{
    Mockery context = new Mockery()
    {
        {
            setImposteriser(new UnsafeHackConcreteClassImposteriser());
        }
    };

    Population<Boolean> pop;
    TournamentSelector<Boolean> selector;

    @Before
    public void setUp()
            throws Exception
    {
        pop = new Population<Boolean>();
    }

    @Test
    public void testSelectorMidRange()
    {

        final List<Double> fitnesses = getTestChromosomeFitnessesMidRange();
        final List<Integer> probs = getRgProbsMidRange();
        List<Chromosome<Boolean>> chrs = TestData.getTestChromosomes();
        List<Chromosome<Boolean>> expected = getExpectedSelectedChromosomesMidRange(chrs);
        testHelper(fitnesses, probs, chrs, expected);
    }

    private void testHelper(final List<Double> fitnesses,
            final List<Integer> probs, List<Chromosome<Boolean>> chrs,
            List<Chromosome<Boolean>> expected)
    {
        pop.setChromosomes(chrs);

        int i = 0;
        for (Chromosome<Boolean> chromosome : chrs) {
            chromosome.setFitnessValue(fitnesses.get(i++));
        }

        final RandomGenerator rg = context.mock(RandomGenerator.class);
        context.checking(new Expectations()
        {
            {
                atLeast(1).of(rg).nextInt(with(any(int.class)));
                will(onConsecutiveCalls(returnConsecutiveValues(probs)));
            }
        });

        selector = new TournamentSelector<Boolean>(pop.getChromosomes().size(),
                2, rg);

        Population<Boolean> resultPop = selector.select(pop);
        assertTrue(TestUtils.listEquals(expected, resultPop.getChromosomes()));
    }

    List<Integer> getRgProbsMidRange()
    {
        return Arrays.asList(2, 0, 1, 3, 0, 1, 2, 3);
    }

    List<Double> getTestChromosomeFitnessesMidRange()
    {
        // cum probs: 0.1, 0.3. 0.6, 1.0
        return Arrays.asList(0.1, 0.2, 0.3, 0.4);
    }

    List<Chromosome<Boolean>> getExpectedSelectedChromosomesMidRange(
            List<Chromosome<Boolean>> chrs)
    {
        List<Chromosome<Boolean>> chrs2 = new ArrayList<Chromosome<Boolean>>();
        chrs2.add(chrs.get(2));
        chrs2.add(chrs.get(3));
        chrs2.add(chrs.get(1));
        chrs2.add(chrs.get(3));
        return chrs2;
    }
}
