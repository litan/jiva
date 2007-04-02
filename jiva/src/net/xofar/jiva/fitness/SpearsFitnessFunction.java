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

/*
 * Created on Mar 31, 2005
 */

package net.xofar.jiva.fitness;

import net.xofar.jiva.RandomGenerator;
import net.xofar.jiva.population.Chromosome;

public class SpearsFitnessFunction
        implements FitnessFunction<Boolean>
{
    private Chromosome<Boolean>[] peaks;
    private double[] peakHeights;
    private int numberOfPeaks;

    public SpearsFitnessFunction(int numPeaks, double lowestHeight,
            String peakHeightsScale, Chromosome<Boolean> prototypeChr,
            RandomGenerator rg)
    {
        this.numberOfPeaks = numPeaks;
        peaks = new Chromosome[numPeaks];
        peakHeights = new double[numPeaks];

        double diff = 1.0 - lowestHeight;
        double linDiff = diff / (double)(numberOfPeaks - 1);
        double sqrtNumPeaks = Math.sqrt(numberOfPeaks);

        Chromosome<Boolean> chromosome;
        for (int j = 0; j < numberOfPeaks; j++) {
            chromosome = prototypeChr.getClone();
            chromosome.randomize(rg);
            peaks[j] = chromosome;

            if (peakHeightsScale.equals("linear"))

            // linear function
            peakHeights[j] = (numberOfPeaks == 1
                    ? 1
                    : 1 - j * linDiff);

            else if (peakHeightsScale.equals("sqrt"))

            peakHeights[j] = 1 - diff * Math.sqrt(j) / sqrtNumPeaks;

            else if (peakHeightsScale.equals("logarithmic"))
            // logarithmic function
            peakHeights[j] = (numberOfPeaks == 1
                    ? 1
                    : lowestHeight + (1 - lowestHeight)
                            * Math.log(numberOfPeaks - j)
                            / Math.log(numberOfPeaks));
        }
    }

    public double evaluate(Chromosome<Boolean> chr)
    {
        int score, highestFitness = 0;
        int nearestPeak = 0;

        for (int j = 0; j < numberOfPeaks; j++) {
            score = chr.matchingGenes(peaks[j]);
            if (score > highestFitness) {
                nearestPeak = j;
                highestFitness = score;
            }
        }
        return peakHeights[nearestPeak] * highestFitness / (double)chr.size();
    }
}
