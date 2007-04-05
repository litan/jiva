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

package net.xofar.jiva.population;

/**
 * @author Lpant
 * 
 * Description
 */
public class BinaryDecoder
        implements ChromosomeDecoder<Boolean>
{
    ValueAsBinaryList binaryValue = new ValueAsBinaryList();
    final double lower;
    final double upper;
    final double numBits;
    final double denominator;
    final double size;

    public BinaryDecoder(final double lower, final double upper,
            final double numBits)
    {
        super();
        this.lower = lower;
        this.upper = upper;
        this.numBits = numBits;
        denominator = Math.pow(2, numBits) - 1;
        this.size = upper - lower;
    }

    public double decode(Chromosome<Boolean> chr, Position pos)
    {
        Range range = (Range)pos;
        double value1 = binaryValue.evaluate(chr, range.getStart(), range.getEnd());
        double value = lower + (value1 * size / denominator);
        return value;
    }
}
