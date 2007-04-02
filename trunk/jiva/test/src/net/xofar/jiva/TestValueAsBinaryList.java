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
 * Created on Feb 2, 2005
 */

package net.xofar.jiva;

import junit.framework.TestCase;
import net.xofar.jiva.population.Chromosome;
import net.xofar.jiva.population.ValueAsBinaryList;

/**
 * @author LPant
 * 
 * Description
 */
public class TestValueAsBinaryList
    extends TestCase
{
  ValueAsBinaryList binValue;

  protected void setUp()
      throws Exception
  {
    binValue = new ValueAsBinaryList();
  }
  
  public void test1()
  {
    Chromosome<Boolean> chr = Chromosome.fromBinaryGeneString("010001001011010000111110010100010");
    assertEquals(70352, binValue.evaluate(chr, 0, 18));
  }
  
  public void test2()
  {
    Chromosome<Boolean> chr = Chromosome.fromBinaryGeneString("010001001011010000111110010100010");
    assertEquals(31906, binValue.evaluate(chr, 18, 33));
  }
}
