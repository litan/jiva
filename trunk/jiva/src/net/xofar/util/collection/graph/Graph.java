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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import net.xofar.util.collection.Pair;
import net.xofar.util.collection.SmartMap;

public class Graph<T>
{
    Map<Vertex<T>, List<Vertex<T>>> adjList = new HashMap<Vertex<T>, List<Vertex<T>>>();

    enum VertexColor {
        WHITE, GRAY, BLACK
    };

    private void edge(Pair<Vertex<T>, Vertex<T>> edge)
    {
        List<Vertex<T>> currList = adjList.get(edge.e1);
        if (currList == null) {
            currList = new ArrayList<Vertex<T>>();
            adjList.put(edge.e1, currList);
        }
        currList.add(edge.e2);
    }

    private List<Vertex<T>> adjList(Vertex<T> vertex)
    {
        List<Vertex<T>> adjacents = adjList.get(vertex);
        if (adjacents == null) {
            return Collections.EMPTY_LIST;
        }
        else {
            return adjacents;
        }
    }

    public SearchResults<T> bfs(Vertex<T> root, GraphVisitor<T> visitor)
    {
        Deque<Vertex<T>> open = new ArrayDeque<Vertex<T>>();
        SearchResults<T> sr = new SearchResults<T>();

        open.addLast(root);
        while (!open.isEmpty()) {
            Vertex<T> vertex = open.removeFirst();
            visitor.visit(vertex);
            sr.setBlack(vertex);

            List<Vertex<T>> adjacents = adjList(vertex);
            for (Vertex<T> adjVertex : adjacents) {
                if (sr.isBlack(adjVertex)) {
                    continue;
                }
                if (sr.isGray(adjVertex)) {
                    continue;
                }
                sr.setGray(adjVertex);
                sr.setParent(adjVertex, vertex);
                open.addLast(adjVertex);
            }
        }
        return sr;
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

        public Graph<T> build()
        {
            Graph<T> graph = new Graph<T>();
            for (Pair<Vertex<T>, Vertex<T>> edge : edges) {
                graph.edge(edge);
            }
            return graph;
        }

        public static Graph<String> build(String graphRep)
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
            return builder.build();
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

    static class SearchResults<T>
    {
        Map<Vertex<T>, Vertex<T>> parents = new HashMap<Vertex<T>, Vertex<T>>();
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
