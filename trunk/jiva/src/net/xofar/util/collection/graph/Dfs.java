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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.xofar.util.collection.SmartMap;
import net.xofar.util.collection.graph.Graph.VertexColor;

/**
 * Class that implements the depth-first-search algorithm on a graph
 * 
 * @author lalitp
 */
class Dfs<T>
{
    private SearchResults sr;
    private SearchState ss;

    public SearchResults dfs(Graph<T> graph, GraphVisitor<T> visitor)
    {
        sr = new SearchResults();
        ss = new SearchState();
        Set<Vertex<T>> vertices = graph.getVertices();
        for (Vertex<T> vertex : vertices) {
            if (ss.isWhite(vertex)) {
                dfs(graph, vertex, visitor);
            }
        }
        return sr;
    }

    void dfs(Graph<T> graph, Vertex<T> vertex, GraphVisitor<T> visitor)
    {
        ss.setGray(vertex);
        for (EdgeData<T> ed : graph.adjList(vertex)) {
            Vertex<T> adjVertex = ed.v2;
            if (ss.isWhite(adjVertex)) {
                sr.setParent(adjVertex, vertex);
                dfs(graph, adjVertex, visitor);
            }
            else if (ss.isGray(adjVertex)) {
                sr.backEdge(vertex, adjVertex);
            }
            else if (ss.isBlack(adjVertex)) {
                sr.crossOrFwdEdge(vertex, adjVertex);
            }
        }
        visitor.visit(vertex); // kind of a post-order traversal
        ss.setBlack(vertex);
    }

    class SearchState
    {
        Map<Vertex<T>, VertexColor> vState = new SmartMap<Vertex<T>, VertexColor>(
                VertexColor.WHITE);

        public boolean isBlack(Vertex<T> vertex)
        {
            return vState.get(vertex).equals(VertexColor.BLACK);
        }

        public boolean isWhite(Vertex<T> vertex)
        {
            return vState.get(vertex).equals(VertexColor.WHITE);
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

    public class SearchResults
    {
        Map<Vertex<T>, Vertex<T>> parents = new HashMap<Vertex<T>, Vertex<T>>();
        Map<Vertex<T>, Vertex<T>> backEdges = new HashMap<Vertex<T>, Vertex<T>>();
        Map<Vertex<T>, Vertex<T>> crossOrFwdEdge = new HashMap<Vertex<T>, Vertex<T>>();

        public void setParent(Vertex<T> child, Vertex<T> parent)
        {
            parents.put(child, parent);
        }

        public void backEdge(Vertex<T> v1, Vertex<T> v2)
        {
            backEdges.put(v1, v2);
        }

        public void crossOrFwdEdge(Vertex<T> v1, Vertex<T> v2)
        {
            crossOrFwdEdge.put(v1, v2);
        }

        public boolean isTree()
        {
            return backEdges.isEmpty()
                    ? true
                    : false;
        }

        @Override
        public String toString()
        {
            return parents.toString();
        }
    }
}
