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
package net.xofar.jiva;

import junit.framework.TestCase;
import net.xofar.jiva.population.BinaryDecoder;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.Range;

/**
 * @author LPant
 * 
 * Description
 */
public class TestNumberDecoder
    extends TestCase
{
  protected void setUp()
      throws Exception
  {
    
  }

  protected void tearDown()
      throws Exception
  {
    
  }
  
  public void test1()
  {
    Chromosome<Boolean> chr = Chromosome.fromBinaryGeneString("010001001011010000111110010100010");
    BinaryDecoder decoder = new BinaryDecoder(-3.0, 12.1, 18);
    Range range = new Range(0, 18);
    assertEquals(1.052426, decoder.decode(chr, range), 0.000001);
  }
  
}
