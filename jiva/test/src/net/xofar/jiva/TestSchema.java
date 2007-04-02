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
 * Created on Feb 3, 2005
 */
package net.xofar.jiva;

import junit.framework.TestCase;
import net.xofar.jiva.Schema;
import net.xofar.jiva.population.Chromosome;

/**
 * @author LPant
 * 
 * Description
 */
public class TestSchema
    extends TestCase
{
  
  public void test1()
  {
    String sGenes = "010101";
    Chromosome<Boolean> chr = Chromosome.fromBinaryGeneString(sGenes);
    
    Schema schema = new Schema("*101*1");
    assertTrue(schema.matches(chr));
  }

  public void test2()
  {
    String sGenes = "010101";
    Chromosome<Boolean> chr = Chromosome.fromBinaryGeneString(sGenes);
    
    Schema schema = new Schema("*101*0");
    assertFalse(schema.matches(chr));
  }
  
  public void test3()
  {
    String sGenes = "010101";
    Chromosome<Boolean> chr = Chromosome.fromBinaryGeneString(sGenes);
    
    Schema schema = new Schema("010101");
    assertTrue(schema.matches(chr));
  }
  
  public void test4()
  {
    String sGenes = "010101";
    Chromosome<Boolean> chr = Chromosome.fromBinaryGeneString(sGenes);
    
    Schema schema = new Schema("******");
    assertTrue(schema.matches(chr));
  }
  
  public void test5()
  {
    String sGenes = "010101";
    Chromosome<Boolean> chr = Chromosome.fromBinaryGeneString(sGenes);
    
    Schema schema = new Schema("*****");
    assertFalse(schema.matches(chr));
  }

  public void test6()
  {
    String sGenes = "010101";
    Chromosome<Boolean> chr = Chromosome.fromBinaryGeneString(sGenes);
    
    Schema schema = new Schema("*******");
    assertFalse(schema.matches(chr));
  }
  
  public void test7()
  {
    String sGenes = "010101";
    Chromosome<Boolean> chr = Chromosome.fromBinaryGeneString(sGenes);
    
    try {
      Schema schema = new Schema("******x");
      fail("Invalid schema accepted");
    }
    catch (RuntimeException e) {
      assertTrue(true);
    }
  }
}
