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
 * Created on Mar 19, 2005
 */

package net.xofar.jiva;

import net.xofar.jiva.operators.TestCrossoverOp;
import net.xofar.jiva.operators.TestMutationOp;
import net.xofar.jiva.selection.TestRouletteWheelSelector;
import net.xofar.jiva.selection.TestTournamentSelector;
import net.xofar.util.collection.immutable.TestList;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestRouletteWheelSelector.class, TestTournamentSelector.class,
        TestCrossoverOp.class, TestMutationOp.class, TestGeneFactory.class,
        TestValueAsBinaryList.class, TestSchema.class, TestNumberDecoder.class,
        TestList.class})
public class AllTests
{}
