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


public class MockRandomGenerator
        implements RandomGenerator
{
    int dCount = 0;
    int iCount = 0;
    int bCount = 0;
    
    public double nextDouble()
    {
        dCount++;
        if (dCount % 5 == 0) {
            return 0.0001;
        }
        else {
            return 0.1;
        }
    }

    public boolean nextBoolean()
    {
        bCount++;
        if (bCount % 2 == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public int nextInt(int i)
    {
        return i/2;
    }
}
