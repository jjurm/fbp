package net.talentum.fbp.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * Utility class that provides convenient index cycling through defined range.
 * Implementation is thread-safe.
 * 
 * @author JJurM
 */
public class RangeIndex {

	protected int min;
	protected int max;

	protected AtomicInteger index;

	protected IntUnaryOperator incrementer = new IncrementOperator();
	protected IntUnaryOperator decrementer = new DecrementOperator();
	protected IntUnaryOperator incrementerIfNotMax = new IncrementIfNotMaxOperator();
	protected IntUnaryOperator decrementerIfNotMin = new DecrementIfNotMinOperator();

	/**
	 * Default constructor. The initial value of {@code index} is {@code min}.
	 * 
	 * @param min
	 *            Starting (the lowest) index, including.
	 * @param max
	 *            Ending (the highest) index, including.
	 * @throws IllegalArgumentException
	 *             when {@code min > max}
	 */
	public RangeIndex(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("From index can't be higher than To index");
		}
		this.min = min;
		this.max = max;
		this.index = new AtomicInteger(min);
	}

	/**
	 * Alternative constructor, with {@code min} parameter of default value 0.
	 * 
	 * @param max
	 *            Ending (the highest) index, including.
	 * @throws IllegalArgumentException
	 *             when {@code to < 0}
	 */
	public RangeIndex(int max) {
		this(0, max);
	}

	/**
	 * Returns true if the given number is in interval {@code [min, max]}.
	 * 
	 * @param num
	 * @return
	 */
	public boolean inRange(int num) {
		return min <= num && num <= max;
	}

	/**
	 * Increments index. If {@code index > max}, then it becomes {@code min}.
	 * 
	 * @return updated value of {@code index}
	 */
	public int increment() {
		return index.updateAndGet(incrementer);
	}

	/**
	 * Decrements index. If {@code index < min}, then in becomes {@code max}.
	 * 
	 * @return updated value of {@code index}
	 */
	public int decrement() {
		return index.updateAndGet(decrementer);
	}

	/**
	 * Increments index, if its value is less than {@code max}. Thread-safe.
	 * 
	 * @return
	 */
	public int incrementIfNotMax() {
		return index.updateAndGet(incrementerIfNotMax);
	}

	/**
	 * Decrements index, if its value is more than {@code min}. Thread-safe.
	 * 
	 * @return
	 */
	public int decrementIfNotMin() {
		return index.updateAndGet(decrementerIfNotMin);
	}

	/**
	 * Returns actual index.
	 */
	public int get() {
		return index.get();
	}

	/**
	 * Sets actual index to the given index.
	 * 
	 * @param index
	 * @return updated index
	 * @throws IllegalArgumentException
	 *             when {@code index} is out of range.
	 */
	public int set(int index) {
		if (!inRange(index)) {
			throw new IllegalArgumentException(String.format("Index must be in range [%d, %d]", min, max));
		}
		this.index.set(index);
		return index;
	}

	/**
	 * Unary operator that will increment the value according to the range.
	 * 
	 * @author JJurM
	 */
	protected class IncrementOperator implements IntUnaryOperator {
		@Override
		public int applyAsInt(int operand) {
			if (operand < max) {
				return operand + 1;
			} else {
				return min;
			}
		}
	}

	/**
	 * Unary operator that will decrement the value according to the range.
	 * 
	 * @author JJurM
	 */
	protected class DecrementOperator implements IntUnaryOperator {
		@Override
		public int applyAsInt(int operand) {
			if (operand > min) {
				return operand - 1;
			} else {
				return max;
			}
		}
	}

	/**
	 * Unary operator that will increment the value only if it's less than
	 * {@code max}.
	 * 
	 * @author JJurM
	 */
	protected class IncrementIfNotMaxOperator implements IntUnaryOperator {
		@Override
		public int applyAsInt(int operand) {
			if (operand < max) {
				return operand + 1;
			} else {
				return operand;
			}
		}
	}

	/**
	 * Unary operator that will decrement the value only if it's more than
	 * {@code min}.
	 * 
	 * @author JJurM
	 */
	protected class DecrementIfNotMinOperator implements IntUnaryOperator {
		@Override
		public int applyAsInt(int operand) {
			if (operand > min) {
				return operand - 1;
			} else {
				return operand;
			}
		}
	}

}
