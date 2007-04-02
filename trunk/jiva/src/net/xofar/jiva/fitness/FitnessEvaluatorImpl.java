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
 * Created on Feb 13, 2005
 */
package net.xofar.jiva.fitness;

import java.util.List;

import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Population;



/**
 * @author Lpant
 * 
 * Description
 */
public class FitnessEvaluatorImpl<T>
        implements Evaluator<T>
{
    FitnessFunction<T> fitnessFunction;
    private int evaluations;

    public FitnessEvaluatorImpl(FitnessFunction<T> fitnessFunction)
    {
        this.fitnessFunction = fitnessFunction;
    }

    public void evaluate(Population<T> currentPopulation, Population<T> population)
    {
        List<Chromosome<T>> chromosomes = population.getChromosomes();
        for (Chromosome<T> chromosome : chromosomes) {
            evaluateFitness(chromosome);
        }
    }

    protected double evaluateFitness(Chromosome<T> chr)
    {
        incrementEvaluations();
        double retVal = fitnessFunction.evaluate(chr);
        chr.setFitnessValue(retVal);
        return retVal;
    }

    private void incrementEvaluations()
    {
        evaluations++;
    }
    
    public int getEvaluations()
    {
        return evaluations;
    }
}
