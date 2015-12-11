package net.talentum.fbp.ui;

import static com.github.stefanbirkner.fishbowl.Fishbowl.exceptionThrownBy;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.stefanbirkner.fishbowl.Statement;

/**
 * Tests basic implementation of various {@link StringContent}s.
 * 
 * @author JJurM
 */
public class StringContentTest {

	@Test
	public void testArrayStringContent() {
		ArrayStringContent sc = new ArrayStringContent(
				new String[] { "Lorem ipsum ", "dolor sit amet, ", "consectetur adipiscing" });
		assertEquals(sc.getRowCount(), 3);
		assertEquals(sc.getRow(0), "Lorem ipsum ");
		assertEquals(sc.getRow(2), "consectetur adipiscing");
		assertNull(sc.getRow(3));
		assertIllegalArgument(() -> sc.getRow(-1));
	}

	@Test
	public void testAutoWrapStringContent() {
		AutoWrapStringContent sc = new AutoWrapStringContent(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
				20);
		assertEquals(sc.getRowCount(), 8);
		assertEquals(sc.getRow(0), "Lorem ipsum dolor");
		assertEquals(sc.getRow(1), "sit amet,");
		assertEquals(sc.getRow(2), "consectetur");
		assertEquals(sc.getRow(3), "adipiscing elit, sed");
		assertEquals(sc.getRow(4), "do eiusmod tempor");
		assertEquals(sc.getRow(5), "incididunt ut labore");
		assertEquals(sc.getRow(6), "et dolore magna");
		assertEquals(sc.getRow(7), "aliqua.");
		assertNull(sc.getRow(8));
		assertIllegalArgument(() -> sc.getRow(-1));
	}

	private void assertIllegalArgument(Statement r) {
		Throwable exception = exceptionThrownBy(r);
		assertThat(exception, is(instanceOf(IllegalArgumentException.class)));
		assertEquals(exception.getMessage(), "Index of row can't be negative");
	}

}
