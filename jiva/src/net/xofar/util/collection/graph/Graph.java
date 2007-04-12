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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import net.xofar.util.collection.Pair;

public class Graph<T>
{
    Map<Vertex<T>, List<Vertex<T>>> adjList = new HashMap<Vertex<T>, List<Vertex<T>>>();
    Set<Vertex<T>> vertices = new LinkedHashSet<Vertex<T>>();
    private boolean directed;

    enum VertexColor {
        WHITE, GRAY, BLACK
    };

    public Graph(boolean directed)
    {
        this.directed = directed;
    }

    Set<Vertex<T>> getVertices()
    {
        return vertices;
    }

    private void edge(Pair<Vertex<T>, Vertex<T>> edge)
    {
        addEdgeToAdjList(edge.e1, edge.e2);

        if (!directed) {
            addEdgeToAdjList(edge.e2, edge.e1);
        }

        if (!vertices.contains(edge.e1)) {
            vertices.add(edge.e1);
        }
        if (!vertices.contains(edge.e2)) {
            vertices.add(edge.e2);
        }
    }

    private void addEdgeToAdjList(Vertex<T> v1, Vertex<T> v2)
    {
        List<Vertex<T>> currList = adjList.get(v1);
        if (currList == null) {
            currList = new ArrayList<Vertex<T>>();
            adjList.put(v1, currList);
        }
        currList.add(v2);
    }

    List<Vertex<T>> adjList(Vertex<T> vertex)
    {
        List<Vertex<T>> adjacents = adjList.get(vertex);
        if (adjacents == null) {
            return Collections.EMPTY_LIST;
        }
        else {
            return adjacents;
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Iterator<Map.Entry<Vertex<T>, List<Vertex<T>>>> iter = adjList
                .entrySet().iterator(); iter.hasNext();) {
            Map.Entry<Vertex<T>, List<Vertex<T>>> entry = iter.next();
            Vertex<T> vertex = entry.getKey();
            List<Vertex<T>> adjacents = entry.getValue();
            for (Vertex<T> vertex2 : adjacents) {
                sb.append("(");
                sb.append(vertex.data.toString());
                sb.append(",");
                sb.append(vertex2.data.toString());
                sb.append(")");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static class Builder<T>
    {
        List<Pair<Vertex<T>, Vertex<T>>> edges;

        public Builder()
        {
            edges = new ArrayList<Pair<Vertex<T>, Vertex<T>>>();
        }

        public void edge(Vertex<T> vertex1, Vertex<T> vertex2)
        {
            edges.add(Pair.create(vertex1, vertex2));
        }

        public Graph<T> build(boolean directed)
        {
            Graph<T> graph = new Graph<T>(directed);
            for (Pair<Vertex<T>, Vertex<T>> edge : edges) {
                graph.edge(edge);
            }
            return graph;
        }

        public static Graph<String> build(String graphRep, boolean directed)
        {
            Builder<String> builder = new Builder<String>();
            Scanner scanner = new Scanner(graphRep);
            scanner.useDelimiter("[(,)\\s]");
            while (true) {
                String vs1 = nextToken(scanner);
                if (vs1 == null) {
                    break;
                }
                String vs2 = nextToken(scanner);
                if (vs2 == null) {
                    throw new IllegalArgumentException("Bad graph literal");
                }
                Vertex<String> v1 = new Vertex<String>(vs1);
                Vertex<String> v2 = new Vertex<String>(vs2);
                builder.edge(v1, v2);
            }
            return builder.build(directed);
        }

        private static String nextToken(Scanner scanner)
        {
            String token = scanner.next();
            while (token.length() == 0) {
                if (!scanner.hasNext()) {
                    return null;
                }
                token = scanner.next();
            }
            return token;
        }
    }

    public Bfs.SearchResults<T> bfs(Vertex<T> vertex, GraphVisitor<T> visitor)
    {
        Bfs<T> searcher = new Bfs<T>();
        return searcher.bfs(this, vertex, visitor);
    }

    public Dfs<T>.SearchResults dfs(Vertex<T> vertex, GraphVisitor<T> visitor)
    {
        Dfs<T> searcher = new Dfs<T>();
        return searcher.dfs(this, visitor);
    }
}
