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

import java.util.Iterator;

public abstract class List<T>
        implements Iterable<T>
{
    public List<T> prepend(T elem)
    {
        return new Cons<T>(elem, this);
    }

    public abstract List<T> remove(T elem);

    public abstract T head();

    public abstract List<T> tail();

    public abstract boolean isEmpty();

    public Iterator<T> iterator()
    {
        return new Iter();
    }

    class Iter
            implements Iterator<T>
    {
        List<T> mylist;

        public Iter()
        {
            mylist = List.this;
        }

        public boolean hasNext()
        {
            return !mylist.isEmpty();
        }

        public T next()
        {
            T retVal = mylist.head();
            mylist = mylist.tail();
            return retVal;
        }

        public void remove()
        {
            throw new UnsupportedOperationException(
                    "remove() on immutable list iterator");
        }
    }

    public static <T> List<T> create()
    {
        return new Nil<T>();
    }
}


class Cons<T>
        extends List<T>
{
    private final T elem;
    private final List<T> next;

    public Cons(T elem, List<T> next)
    {
        this.elem = elem;
        this.next = next;
    }

    @Override
    public List<T> remove(T elem)
    {
        if (this.elem.equals(elem)) {
            return next;
        }
        else {
            return new Cons<T>(this.elem, next.remove(elem));
        }
    }

    @Override
    public T head()
    {
        return elem;
    }

    @Override
    public List<T> tail()
    {
        return next;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

}


class Nil<T>
        extends List<T>
{
    @Override
    public List<T> remove(T elem)
    {
        throw new UnsupportedOperationException("remove() on an empty list");
    }

    @Override
    public T head()
    {
        throw new UnsupportedOperationException("head() on an empty list");
    }

    @Override
    public List<T> tail()
    {
        throw new UnsupportedOperationException("tail() on an empty list");
    }

    @Override
    public boolean isEmpty()
    {
        return true;
    }
}
