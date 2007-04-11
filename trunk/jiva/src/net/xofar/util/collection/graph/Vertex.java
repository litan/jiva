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

package net.xofar.util.collection.graph;

public class Vertex<T>
{
    public final T data;

    public Vertex(T data)
    {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Vertex)) {
            return false;
        }

        Vertex<T> other = (Vertex<T>)obj;
        return data.equals(other.data);
    }

    @Override
    public int hashCode()
    {
        return data.hashCode();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Vertex(");
        sb.append(data.toString());
        sb.append(")");
        return sb.toString();
    }

}
