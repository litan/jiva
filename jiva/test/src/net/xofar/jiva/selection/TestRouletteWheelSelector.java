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

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.xofar.jiva.RandomGenerator;
import net.xofar.jiva.TestData;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Population;
import net.xofar.jiva.selection.RouletteWheelSelector;
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
 */
@RunWith(JMock.class)
public class TestRouletteWheelSelector
{
    Mockery context = new Mockery()
    {
        {
            setImposteriser(new UnsafeHackConcreteClassImposteriser());
        }
    };

    Population<Boolean> pop;
    RouletteWheelSelector<Boolean> selector;

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
        final List<Double> probs = getRgProbsMidRange();
        List<Chromosome<Boolean>> chrs = TestData.getTestChromosomes();
        List<Chromosome<Boolean>> expected = getExpectedSelectedChromosomesMidRange(chrs);        
        testHelper(fitnesses, probs, chrs, expected);
    }

    @Test
    public void testSelectorLeftEdge()
    {

        final List<Double> fitnesses = getTestChromosomeFitnessesLeftEdge();
        final List<Double> probs = getRgProbsLeftEdge();
        List<Chromosome<Boolean>> chrs = TestData.getTestChromosomes();
        List<Chromosome<Boolean>> expected = getExpectedSelectedChromosomesLeftEdge(chrs);        
        testHelper(fitnesses, probs, chrs, expected);
    }

    @Test
    public void testSelectorRightEdge()
    {

        final List<Double> fitnesses = getTestChromosomeFitnessesRightEdge();
        final List<Double> probs = getRgProbsRightEdge();
        List<Chromosome<Boolean>> chrs = TestData.getTestChromosomes();
        List<Chromosome<Boolean>> expected = getExpectedSelectedChromosomesRightEdge(chrs);        
        testHelper(fitnesses, probs, chrs, expected);
    }

    private void testHelper(final List<Double> fitnesses,
            final List<Double> probs, List<Chromosome<Boolean>> chrs,
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
                atLeast(1).of(rg).nextDouble();
                will(onConsecutiveCalls(returnValue(probs.get(0)),
                        returnValue(probs.get(1)), returnValue(probs.get(2)),
                        returnValue(probs.get(3))));
            }
        });

        selector = new RouletteWheelSelector<Boolean>(pop.getChromosomes()
                .size(), rg);

        Population<Boolean> resultPop = selector.select(pop);
        assertTrue(TestUtils.listEquals(expected,
                resultPop.getChromosomes()));
    }

    List<Double> getRgProbsMidRange()
    {
        return Arrays.asList(0.01, 0.4, 0.7, 0.9);
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
        chrs2.add(chrs.get(0));
        chrs2.add(chrs.get(2));
        chrs2.add(chrs.get(3));
        chrs2.add(chrs.get(3));
        return chrs2;
    }


    List<Double> getRgProbsLeftEdge()
    {
        return Arrays.asList(0.01, 0.03, 0.02, 0.04);
    }

    List<Double> getTestChromosomeFitnessesLeftEdge()
    {
        // cum probs: 0.05, 0.3. 0.7, 1.0
        return Arrays.asList(0.05, 0.25, 0.4, 0.3);
    }

    List<Chromosome<Boolean>> getExpectedSelectedChromosomesLeftEdge(
            List<Chromosome<Boolean>> chrs)
    {
        List<Chromosome<Boolean>> chrs2 = new ArrayList<Chromosome<Boolean>>();
        chrs2.add(chrs.get(0));
        chrs2.add(chrs.get(0));
        chrs2.add(chrs.get(0));
        chrs2.add(chrs.get(0));
        return chrs2;
    }
    
    List<Double> getRgProbsRightEdge()
    {
        return Arrays.asList(0.8, 0.9, 0.85, 0.95);
    }

    List<Double> getTestChromosomeFitnessesRightEdge()
    {
        // cum probs: 0.05, 0.3. 0.7, 1.0
        return Arrays.asList(0.05, 0.25, 0.4, 0.3);
    }

    List<Chromosome<Boolean>> getExpectedSelectedChromosomesRightEdge(
            List<Chromosome<Boolean>> chrs)
    {
        List<Chromosome<Boolean>> chrs2 = new ArrayList<Chromosome<Boolean>>();
        chrs2.add(chrs.get(3));
        chrs2.add(chrs.get(3));
        chrs2.add(chrs.get(3));
        chrs2.add(chrs.get(3));
        return chrs2;
    }
}
