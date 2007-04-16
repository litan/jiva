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
import net.xofar.util.collection.Tuple3;

public class Graph<T>
{
    Map<Vertex<T>, List<EdgeData<T>>> adjList = new HashMap<Vertex<T>, List<EdgeData<T>>>();
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

    private void edge(Tuple3<Vertex<T>, Vertex<T>, Double> edge)
    {
        addEdgeToAdjList(edge.e1, edge.e2, edge.e3);

        if (!directed) {
            addEdgeToAdjList(edge.e2, edge.e1, edge.e3);
        }

        if (!vertices.contains(edge.e1)) {
            vertices.add(edge.e1);
        }
        if (!vertices.contains(edge.e2)) {
            vertices.add(edge.e2);
        }
    }

    private void addEdgeToAdjList(Vertex<T> v1, Vertex<T> v2, Double weight)
    {
        List<EdgeData<T>> currList = adjList.get(v1);
        if (currList == null) {
            currList = new ArrayList<EdgeData<T>>();
            adjList.put(v1, currList);
        }
        currList.add(new EdgeData<T>(v2, weight));
    }

    List<EdgeData<T>> adjList(Vertex<T> vertex)
    {
        List<EdgeData<T>> adjacents = adjList.get(vertex);
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
        for (Iterator<Map.Entry<Vertex<T>, List<EdgeData<T>>>> iter = adjList
                .entrySet().iterator(); iter.hasNext();) {
            Map.Entry<Vertex<T>, List<EdgeData<T>>> entry = iter.next();
            Vertex<T> vertex = entry.getKey();
            List<EdgeData<T>> adjacents = entry.getValue();
            for (EdgeData<T> ed : adjacents) {
                sb.append("(");
                sb.append(vertex.data.toString());
                sb.append(",");
                sb.append(ed.v2.data.toString());
                sb.append(",");
                sb.append(ed.weight);
                sb.append(")");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static class Builder<T>
    {
        List<Tuple3<Vertex<T>, Vertex<T>, Double>> edges;

        public Builder()
        {
            edges = new ArrayList<Tuple3<Vertex<T>, Vertex<T>, Double>>();
        }

        public void edge(Vertex<T> vertex1, Vertex<T> vertex2, Double weight)
        {
            edges.add(Tuple3.create(vertex1, vertex2, weight));
        }

        public Graph<T> build(boolean directed)
        {
            Graph<T> graph = new Graph<T>(directed);
            for (Tuple3<Vertex<T>, Vertex<T>, Double> edge : edges) {
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
                builder.edge(v1, v2, 1.0);
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

        public static Graph<String> build2(String graphRep, boolean directed)
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
                String w = nextToken(scanner);
                if (w == null) {
                    throw new IllegalArgumentException("Bad graph literal");
                }
                Vertex<String> v1 = new Vertex<String>(vs1);
                Vertex<String> v2 = new Vertex<String>(vs2);
                builder.edge(v1, v2, Double.valueOf(w));
            }
            return builder.build(directed);
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

    public Dijkstra.SearchResults<T> djikstra(Vertex<T> vertex)
    {
        Dijkstra<T> searcher = new Dijkstra<T>();
        return searcher.search(this, vertex);
    }
}
