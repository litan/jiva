/*
 * Created on Feb 2, 2005
 */

package net.xofar.jiva.samples.dejong;

import net.xofar.jiva.fitness.FitnessFunction;
import net.xofar.jiva.population.BinaryDecoder;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Range;

/**
 * @author Lpant
 * 
 * Description
 */
public class FitnessFunctionAs2
        implements FitnessFunction
{
    private static final long serialVersionUID = 3258688793317095217L;
    BinaryDecoder xDecoder;
    BinaryDecoder yDecoder;
    BinaryDecoder zDecoder;
    
    Range xRange, yRange, zRange;

    public FitnessFunctionAs2()
    {
        xDecoder = new BinaryDecoder(-5.12, 5.12, 10);
        xRange = new Range(0, 10);
        yDecoder = new BinaryDecoder(-5.12, 5.12, 10);
        yRange = new Range(10, 20);
        zDecoder = new BinaryDecoder(-5.12, 5.12, 10);
        zRange = new Range(20, 30);
    }

    public double evaluate(Chromosome chr)
    {
        double x = getXValue(chr);
        double y = getYValue(chr);
        double z = getZValue(chr);
        return function(x, y, z);
    }

    double getXValue(Chromosome chr)
    {
        return xDecoder.decode(chr, xRange);
    }

    double getYValue(Chromosome chr)
    {
        return yDecoder.decode(chr, yRange);
    }

    double getZValue(Chromosome chr)
    {
        return zDecoder.decode(chr, zRange);
    }

    private double function(double x, double y, double z)
    {
        double retVal = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
        return retVal;
        // return Math.max(retVal, 0.0);
    }

}
