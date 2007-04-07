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

package net.xofar.util.collection.immutable;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import org.junit.Before;
import org.junit.Test;

public class TestList
{
    List<Integer> list;

    @Before
    public void setUp()
            throws Exception
    {
        list = List.create();
    }

    @Test
    public void testAdd()
    {
        java.util.List<Integer> expected = Arrays.asList(1, 2, 3);

        for (ListIterator<Integer> iterator = expected.listIterator(expected
                .size()); iterator.hasPrevious();) {
            Integer x = iterator.previous();
            list = list.prepend(x);
        }

        java.util.List<Integer> actual = new ArrayList<Integer>();
        for (Integer x : list) {
            actual.add(x);
        }
        assertTrue(expected.equals(actual));
    }
    
    @Test
    public void testRemove()
    {
        java.util.List<Integer> indata = Arrays.asList(1, 2, 3);
        java.util.List<Integer> expected = Arrays.asList(1, 3);

        for (ListIterator<Integer> iterator = indata.listIterator(indata
                .size()); iterator.hasPrevious();) {
            Integer x = iterator.previous();
            list = list.prepend(x);
        }
        
        list = list.remove(2);

        java.util.List<Integer> actual = new ArrayList<Integer>();
        for (Integer x : list) {
            actual.add(x);
        }
        assertTrue(expected.equals(actual));
    }
    
    @Test
    public void testContains()
    {
        list = List.fromJclList(Arrays.asList(1, 2, 3));
        assertTrue(list.contains(2));
        assertFalse(list.contains(4));
    }
    
    @Test
    public void testTake()
    {
        list = List.fromJclList(Arrays.asList(1, 2, 3));
        assertEquals(List.fromJclList(Arrays.asList(1, 2)), list.take(2));
    }
    
    @Test
    public void testDrop()
    {
        list = List.fromJclList(Arrays.asList(1, 2, 3));
        assertEquals(List.fromJclList(Arrays.asList(3)), list.drop(2));
    }
    
    @Test
    public void testSlice()
    {
        list = List.fromJclList(Arrays.asList(1, 2, 3, 4));
        assertEquals(List.fromJclList(Arrays.asList(2, 3)), list.slice(1, 3));
    }
    
}
