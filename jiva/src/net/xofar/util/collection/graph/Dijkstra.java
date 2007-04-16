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

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Dijkstra<T>
{
    SearchResults<T> sr;

    public SearchResults<T> search(Graph<T> graph, Vertex<T> root)
    {
        init(graph, root);
        Set<Vertex<T>> done = new HashSet<Vertex<T>>();

        Queue<Vertex<T>> queue = new PriorityQueue<Vertex<T>>(16,
                new Comparator<Vertex<T>>()
                {
                    public int compare(Vertex<T> v1, Vertex<T> v2)
                    {
                        return sr.getDistance(v1).compareTo(sr.getDistance(v2));
                    }
                });

        queue.addAll(graph.getVertices());
        while (!queue.isEmpty()) {
            Vertex<T> u = queue.remove();
            done.add(u);
            for (EdgeData<T> ed : graph.adjList(u)) {
                Vertex<T> adjVertex = ed.v2;
                relax(u, adjVertex, ed.weight);
            }
        }
        return sr;
    }

    private void relax(Vertex<T> u, Vertex<T> v, Double weight)
    {
        if (sr.getDistance(v).compareTo(sr.getDistance(u) + weight) > 0) {
            sr.setDistance(v, sr.distances.get(u) + weight);
            sr.setParent(v, u);
        }
    }

    private void init(Graph<T> graph, Vertex<T> root)
    {
        sr = new SearchResults<T>();
        Set<Vertex<T>> vertices = graph.getVertices();
        for (Vertex<T> vertex : vertices) {
            sr.setDistance(vertex, Double.MAX_VALUE);
        }
        sr.setDistance(root, 0.0);
    }

    public static class SearchResults<T>
    {
        private Map<Vertex<T>, Vertex<T>> parents = new HashMap<Vertex<T>, Vertex<T>>();
        private Map<Vertex<T>, Double> distances = new HashMap<Vertex<T>, Double>();

        public void setParent(Vertex<T> child, Vertex<T> parent)
        {
            parents.put(child, parent);
        }

        public void setDistance(Vertex<T> v2, Double d)
        {
            distances.put(v2, d);
        }

        public Double getDistance(Vertex<T> v)
        {
            return distances.get(v);
        }

        @Override
        public String toString()
        {
            return parents.toString();
        }
    }
}
