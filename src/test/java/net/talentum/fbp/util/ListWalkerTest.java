package net.talentum.fbp.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import net.talentum.fbp.test.TestBase;

/**
 * Test behavior of {@link ListWalker}.
 * 
 * @author JJurM
 */
public class ListWalkerTest extends TestBase {

	@Test
	public void testNoLoop() {
		ListWalker<String> walker = new ListWalker<String>(Arrays.asList(new String[] { "A", "B", "C", "D" }), false);

		assertTrue(walker.hasItems());
		assertEquals(walker.getSelected(), "A");
		walker.decrement();
		assertEquals(walker.getSelected(), "A");
		walker.increment();
		assertEquals(walker.getSelected(), "B");
		walker.increment();
		walker.increment();
		walker.increment();
		assertEquals(walker.getSelected(), "D");
	}

	@Test
	public void testAllowLoop() {
		ListWalker<String> walker = new ListWalker<String>(Arrays.asList(new String[] { "X", "Y", "Z" }), true);

		assertTrue(walker.hasItems());
		walker.increment();
		assertEquals(walker.getSelected(), "Y");
		walker.decrement();
		walker.decrement();
		assertEquals(walker.getSelected(), "Z");
		walker.increment();
		assertEquals(walker.getSelected(), "X");
	}

	@Test
	public void testEmptyLoop() {
		ListWalker<Object> walker1 = new ListWalker<Object>(new ArrayList<Object>(), false);
		ListWalker<Object> walker2 = new ListWalker<Object>(new ArrayList<Object>(), true);

		assertFalse(walker1.hasItems());
		assertFalse(walker2.hasItems());
		assertNull(walker1.getSelected());
		assertNull(walker2.getSelected());

		walker1.increment();
		walker2.decrement();

		assertFalse(walker1.hasItems());
		assertFalse(walker2.hasItems());
		assertNull(walker1.getSelected());
		assertNull(walker2.getSelected());
	}

}
