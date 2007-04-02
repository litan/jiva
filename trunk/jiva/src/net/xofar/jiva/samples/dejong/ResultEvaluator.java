/*
 * Created on Feb 5, 2005
 */

package net.xofar.jiva.samples.dejong;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Population;



/**
 * @author LPant
 * 
 * Description
 */
public class ResultEvaluator
{
    PrintStream avgFitnessOs;
    PrintStream bestFitnessOs;
    PrintStream popSizeOs;
    PrintStream evaluations;
    
    int counter = 1;

    public ResultEvaluator()
    {
        try {
            createStreams();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void go(List individuals)
    {
        // this.individuals = individuals;
        go2(individuals);
    }

    public void stop()
    {
        try {
            avgFitnessOs.close();
            bestFitnessOs.close();
            popSizeOs.close();
            evaluations.close();
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * @param schemas
     * @throws FileNotFoundException
     */
    private void createStreams()
            throws FileNotFoundException
    {
        avgFitnessOs = new PrintStream(new FileOutputStream("avg_fitness.txt"));
        bestFitnessOs = new PrintStream(
                new FileOutputStream("best_fitness.txt"));
        popSizeOs = new PrintStream(new FileOutputStream("pop_size.txt"));
        evaluations = new PrintStream(new FileOutputStream("evals_fitness.txt"));
    }

    public void go2(List individuals)
    {
        double fitnessSum = 0;
        double bestFitness = 0;
        for (Iterator iter = individuals.iterator(); iter.hasNext();) {
            Chromosome chrom = (Chromosome)iter.next();
            double fitness = chrom.getFitnessValue();
            fitnessSum += fitness;
            if (fitness > bestFitness) {
                bestFitness = fitness;
            }
        }
        reportResults(individuals.size(), fitnessSum, bestFitness);
    }

    /**
     * @param i
     * @param fitnessSum
     * @param schemaCount
     * @param schemaFitnessSum
     */
    private void reportResults(int popSize, double fitnessSum,
            double bestFitness)
    {
        double avgFitness = fitnessSum / popSize;
        // bestFitnessOs.print(Integer.toString(counter++));
        // bestFitnessOs.print(" ");
        bestFitnessOs.println(Double.toString(bestFitness));
        avgFitnessOs.println(Double.toString(avgFitness));
        popSizeOs.println(Double.toString(popSize));
    }

    public void goNew(Population p)
    {
        double bestFitness = p.getFittest().getFitnessValue();
        bestFitnessOs.println(Double.toString(bestFitness));
        avgFitnessOs.println(Double.toString(p.getAvgFitness()));
        popSizeOs.println(Double.toString(p.size()));
        evaluations.print(Double.toString(p.getEvaluations()));
        evaluations.print("   ");
        evaluations.println(Double.toString(bestFitness));
    }
}
