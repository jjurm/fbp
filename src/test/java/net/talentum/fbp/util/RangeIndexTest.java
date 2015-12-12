package net.talentum.fbp.util;

import static com.github.stefanbirkner.fishbowl.Fishbowl.exceptionThrownBy;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests behavior of {@link RangeIndex}.
 * 
 * @author JJurM
 */
public class RangeIndexTest {

	private RangeIndex index;

	@Before
	public void constructRangeIndex() {
		index = new RangeIndex(-10, 42);
	}

	@Test
	public void testBasics1() {
		assertEquals(index.get(), -10);
		index.increment();
		assertEquals(index.get(), -9);
		index.decrement();
		index.increment();
		index.increment();
		assertEquals(index.get(), -8);
	}

	@Test
	public void testConstructor() {
		new RangeIndex(0);
		new RangeIndex(4, 4);
		new RangeIndex(Integer.MIN_VALUE, Integer.MAX_VALUE);

		Throwable exception = exceptionThrownBy(() -> new RangeIndex(5, 4));
		assertThat(exception, is(instanceOf(IllegalArgumentException.class)));

		exception = exceptionThrownBy(() -> new RangeIndex(-5));
		assertThat(exception, is(instanceOf(IllegalArgumentException.class)));
	}

	@Test
	public void testIncrementAndDecrementOverRange() {
		index.decrement();
		assertEquals(index.get(), 42);
		assertEquals(index.decrement(), 41);
		index.increment();
		assertEquals(index.increment(), -10);
	}

	@Test
	public void testSet1() {
		index.set(5);
		assertEquals(index.get(), 5);
		index.set(42);
		assertEquals(index.increment(), -10);
		assertEquals(index.set(15), 15);
	}

}
