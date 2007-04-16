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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import net.xofar.util.collection.SmartMap;
import net.xofar.util.collection.graph.Graph.VertexColor;

/**
 * Class that implements the breadth-first-search algorithm on a graph
 * 
 * @author lalitp
 */
class Bfs<T>
{

    public SearchResults<T> bfs(Graph<T> graph, Vertex<T> root,
            GraphVisitor<T> visitor)
    {
        Deque<Vertex<T>> open = new ArrayDeque<Vertex<T>>();
        SearchResults<T> sr = new SearchResults<T>();
        SearchState ss = new SearchState();

        ss.setGray(root);
        open.addLast(root);
        while (!open.isEmpty()) {
            Vertex<T> vertex = open.removeFirst();
            visitor.visit(vertex);
            ss.setBlack(vertex);

            for (EdgeData<T> ed : graph.adjList(vertex)) {
                Vertex<T> adjVertex = ed.v2;
                if (ss.isBlack(adjVertex)) {
                    continue;
                }
                if (ss.isGray(adjVertex)) {
                    continue;
                }
                ss.setGray(adjVertex);
                sr.setParent(adjVertex, vertex);
                open.addLast(adjVertex);
            }
        }
        return sr;
    }

    class SearchState
    {
        Map<Vertex<T>, VertexColor> vState = new SmartMap<Vertex<T>, VertexColor>(
                VertexColor.WHITE);

        public boolean isBlack(Vertex<T> vertex)
        {
            return vState.get(vertex).equals(VertexColor.BLACK);
        }

        public boolean isGray(Vertex<T> vertex)
        {
            return vState.get(vertex).equals(VertexColor.GRAY);
        }

        public void setGray(Vertex<T> vertex)
        {
            vState.put(vertex, VertexColor.GRAY);
        }

        public void setBlack(Vertex<T> vertex)
        {
            vState.put(vertex, VertexColor.BLACK);
        }

    }

    public static class SearchResults<T>
    {
        Map<Vertex<T>, Vertex<T>> parents = new HashMap<Vertex<T>, Vertex<T>>();

        public void setParent(Vertex<T> child, Vertex<T> parent)
        {
            parents.put(child, parent);
        }

        @Override
        public String toString()
        {
            return parents.toString();
        }
    }
}
