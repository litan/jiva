package net.xofar.util.concurrent;

import static junit.framework.Assert.*;

import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class TestFutureResult {

    @Test
    public void testResultTransfer() throws Exception {
	String expected = "Result";
	FutureResult<String> res = new FutureResult<String>();
	res.set(expected);
	assertEquals(expected, res.get());
    }

    @Test
    public void testAsyncResultTransfer() throws Exception {
	final String expected = "Result";
	final FutureResult<String> res = new FutureResult<String>();
	new Thread(new Runnable() {
	    public void run() {
		res.set(expected);
	    }
	}).start();
	assertEquals(expected, res.get());
    }

    @Test
    public void testExceptionTransfer() throws Exception {
	Exception expected = new RuntimeException("Result");
	FutureResult<String> res = new FutureResult<String>();
	res.setException(expected);
	try {
	    res.get();
	    fail("get() should have been thrown an Exception");
	} catch (ExecutionException e) {
	    assertEquals(expected, e.getCause());
	}
    }

    @Test
    public void testAsyncExceptionTransfer() throws Exception {
	final Exception expected = new RuntimeException("Result");
	final FutureResult<String> res = new FutureResult<String>();
	new Thread(new Runnable() {
	    public void run() {
		res.setException(expected);
	    }
	}).start();

	try {
	    res.get();
	    fail("get() should have been thrown an Exception");
	} catch (ExecutionException e) {
	    assertEquals(expected, e.getCause());
	}
    }
}
