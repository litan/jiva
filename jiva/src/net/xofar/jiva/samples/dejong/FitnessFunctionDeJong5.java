/*
 * Created on Mar 3, 2005
 */
package net.xofar.jiva.samples.dejong;

import net.xofar.jiva.fitness.FitnessFunction;
import net.xofar.jiva.population.BinaryDecoder;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.ChromosomeDecoder;
import net.xofar.jiva.population.DoubleDecoder;
import net.xofar.jiva.population.Locus;
import net.xofar.jiva.population.Position;
import net.xofar.jiva.population.Range;

public class FitnessFunctionDeJong5<T>
implements FitnessFunction<T>
{
    ChromosomeDecoder xDecoder;
    ChromosomeDecoder yDecoder;
    Position xPos, yPos;
    
    double f5a[][] = {
        {-32.0,-16.0,0.0,16.0,32.0},
        {-32.0,-16.0,0.0,16.0,32.0}
      };
    
    
    public FitnessFunctionDeJong5(boolean binary)
    {
        if (binary) {
            xDecoder = new BinaryDecoder(-65.536, 65.536, 17);
            xPos = new Range(0, 17);
            yDecoder = new BinaryDecoder(-65.536, 65.536, 17);
            yPos = new Range(17, 34);
        }
        else {
            xDecoder = new DoubleDecoder();
            xPos = new Locus(0);
            yDecoder = xDecoder;
            yPos = new Locus(1);
        }
    }
    
    public double evaluate(Chromosome<T> chr)
    {
        double x = getXValue(chr);
        double y = getYValue(chr);
        return f5(x, y);
    }

    double getXValue(Chromosome<T> chr)
    {
        return xDecoder.decode(chr, xPos);
    }

    double getYValue(Chromosome<T> chr)
    {
        return yDecoder.decode(chr, yPos);
    }

    private double f5(double x, double y)
    {
        return 500.0 - (1.0 / inverseF5(x, y));
    }

    private double inverseF5(double x, double y)
    {
        double fSummation = 0;
        for (int i = 1; i < 26; i++) {
            fSummation += 1.0 / f5j(x, y, i);
        }
        return 1.0/500.0 + fSummation;
    }

    private double f5j(double x, double y, int ji)
    {
        double cj = ji;
        double firstTerm = Math.pow(x - f5a[0][(ji-1) % 5], 6);
        double secondTerm = Math.pow(y - f5a[1][(ji-1) % 5], 6);
        return cj + firstTerm + secondTerm;
    }
}
