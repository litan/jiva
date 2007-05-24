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

package net.xofar.util.listener;

import net.xofar.util.XofarTestBase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestBroadcaster
        extends XofarTestBase
{
    public Mockery context = super.context;

    EventBroadcaster<String> broadcaster;

    @Before
    public void setUp()
            throws Exception
    {
        broadcaster = new NonBlockingBroadcaster<String>();
    }

    @Test
    public void testOneListener()
    {
        final EventListener<String> listener1 = context
                .mock(EventListener.class);

        final String event = "event";

        context.checking(new Expectations()
        {
            {
                one(listener1).eventFired(with(same(event)));
            }
        });

        broadcaster.addListener(listener1);
        broadcaster.broadcastEvent(event);
    }

    @Test
    public void testThreeListenerers()
    {
        final EventListener<String> listener1 = context
                .mock(EventListener.class);
        final EventListener<String> listener2 = context
                .mock(EventListener.class);
        final EventListener<String> listener3 = context
                .mock(EventListener.class);

        final String event = "event";

        context.checking(new Expectations()
        {
            {
                one(listener1).eventFired(with(same(event)));
                one(listener2).eventFired(with(same(event)));
                one(listener3).eventFired(with(same(event)));
            }
        });

        broadcaster.addListener(listener1);
        broadcaster.addListener(listener2);
        broadcaster.addListener(listener3);
        broadcaster.broadcastEvent(event);
    }

    @Test
    public void testThreeListenerersThreeEvents()
    {
        final EventListener<String> listener1 = context
                .mock(EventListener.class);
        final EventListener<String> listener2 = context
                .mock(EventListener.class);
        final EventListener<String> listener3 = context
                .mock(EventListener.class);

        final String event = "event";

        context.checking(new Expectations()
        {
            {
                exactly(3).of(listener1).eventFired(with(same(event)));
                exactly(3).of(listener2).eventFired(with(same(event)));
                exactly(3).of(listener3).eventFired(with(same(event)));
            }
        });

        broadcaster.addListener(listener1);
        broadcaster.addListener(listener2);
        broadcaster.addListener(listener3);
        broadcaster.broadcastEvent(event);
        broadcaster.broadcastEvent(event);
        broadcaster.broadcastEvent(event);
    }
}
