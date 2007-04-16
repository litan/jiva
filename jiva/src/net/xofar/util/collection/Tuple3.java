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

package net.xofar.util.collection;

public class Tuple3<E1, E2, E3>
{
    public E1 e1;
    public E2 e2;
    public E3 e3;

    public static <E1, E2, E3> Tuple3<E1, E2, E3> create(E1 e1, E2 e2, E3 e3)
    {
        return new Tuple3<E1, E2, E3>(e1, e2, e3);
    }

    public Tuple3(E1 e1, E2 e2, E3 e3)
    {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }
}
