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

import static net.xofar.util.TestUtils.*;

import java.util.ArrayList;
import java.util.List;

import net.xofar.util.XofarTestBase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestGraph
        extends XofarTestBase
{
    public Mockery context = super.context;

    @Test
    public void testBfs()
    {
        final GraphVisitor<String> visitor = context.mock(GraphVisitor.class);

        context.checking(new Expectations()
        {
            {
                exactly(4).of(visitor).visit(
                        with(argSequence(vertexSequence("A", "B", "C", "D"))));
            }

        });
        Graph<String> g = Graph.Builder.build("((A,B),(A,C),(A,D),(B,D))",
                false);
        Bfs.SearchResults<String> sr = g.bfs(new Vertex<String>("A"), visitor);
        System.out.println(sr.toString());
    }

    @Test
    public void testDfs()
    {
        final GraphVisitor<String> visitor = context.mock(GraphVisitor.class);

        context.checking(new Expectations()
        {
            {
                exactly(4).of(visitor).visit(
                        with(argSequence(vertexSequence("D", "B", "C", "A"))));
            }

        });
        Graph<String> g = Graph.Builder.build("((A,B),(A,C),(A,D),(B,D))",
                false);
        Dfs<String>.SearchResults sr = g.dfs(new Vertex<String>("A"), visitor);
        System.out.println(sr.toString());
    }

    @Test
    public void testDjikstra()
    {
        Graph<String> g = Graph.Builder.build2(
                "((s,y,5),(s,t,10),(y,t,3),(y,x,9),(y,z,2),(t,y,2),(t,x,1)"
                        + ",(z,s,7),(z,x,6),(x,z,4))", true);
        Dijkstra.SearchResults<String> sr = g.djikstra(new Vertex<String>("s"));
        System.out.println(sr.toString());
    }

    private List<Vertex<String>> vertexSequence(String... seq)
    {
        List<Vertex<String>> vseq = new ArrayList<Vertex<String>>();
        for (String data : seq) {
            vseq.add(new Vertex<String>(data));
        }
        return vseq;
    }
}
