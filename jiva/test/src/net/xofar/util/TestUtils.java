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

package net.xofar.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.api.Action;

public class TestUtils
{

    public static <T> boolean listEquals(List<T> list1, List<T> list2)
    {
        if (list1 == list2) {
            return true;
        }

        if (list1.size() != list2.size()) {
            return false;
        }

        for (Iterator<T> iter = list1.iterator(), iter2 = list2.iterator(); iter
                .hasNext();) {
            T element1 = iter.next();
            T element2 = iter2.next();
            if (!element1.equals(element2)) {
                return false;
            }
        }
        return true;
    }

    public static <T> Action[] returnConsecutiveValues(List<T> values)
    {
        Expectations exp = new Expectations();
        List<Action> actions = new ArrayList<Action>();
        for (T value : values) {
            actions.add(exp.returnValue(value));
        }
        return actions.toArray(new Action[]{});
    }

    public static <T> Action[] returnConsecutiveValues(T... values)
    {
        Expectations exp = new Expectations();
        List<Action> actions = new ArrayList<Action>();
        for (T value : values) {
            actions.add(exp.returnValue(value));
        }
        return actions.toArray(new Action[]{});
    }

    public static <T> Matcher<T> argSequence(T... seq)
    {
        return new ArgSequenceMatcher<T>(Arrays.asList(seq));
    }

    public static <T> Matcher<T> argSequence(List<T> seq)
    {
        return new ArgSequenceMatcher<T>(seq);
    }

    static class ArgSequenceMatcher<T>
            extends BaseMatcher<T>
    {
        private Iterator<T> expected;
        private List<T> expectedValues;
        private List<T> actualValues;

        public ArgSequenceMatcher(List<T> expectedValues)
        {
            this.expectedValues = expectedValues;
            this.actualValues = new ArrayList<T>();
            expected = expectedValues.iterator();
        }

        public boolean matches(Object item)
        {
            actualValues.add((T)item);
            return expected.next().equals(item);
        }

        public void describeTo(Description description)
        {
            description.appendText("Expected Arguments: ");
            description.appendText(expectedValues.toString());
            description.appendText("Actual Arguments: ");
            description.appendText(actualValues.toString());
        }
    }

}
