package net.talentum.fbp.util;

import java.util.List;

/**
 * Class capable of holding a {@code List<T>} and an index pointing at one of
 * those items. Involves use of {@link RangeIndex} to increment and decrement
 * the index. Recognizes list of zero length and this yields to a different
 * behavior (see docs of individual methods).
 * <p>
 * <em>Note:</em> This implementation assumes program won't change the given
 * list after passing it to the constructor.
 * </p>
 * 
 * @param <T>
 *            type of objects in the list
 * @author JJurM
 */
public class ListWalker<T> {

	protected List<T> list;
	protected RangeIndex index;
	protected boolean allowLoop;

	/**
	 * Default constructor. Stores given list and sets index to the given value.
	 * 
	 * @param list
	 *            list of items, musn't be modified after passing here
	 * @param index
	 *            initial index to use, ignored when the list is empty
	 * @param allowLoop
	 *            what should be done when user wants to cycle over first or
	 *            last element: to continue from the other side or to do nothing
	 * @throws IllegalArgumentException
	 *             when index is greater or equal to the size of the list, or
	 *             negative (ignored when the list is empty)
	 */
	public ListWalker(List<T> list, int index, boolean allowLoop) {
		this.list = list;
		if (list.size() > 0) {
			this.index = new RangeIndex(list.size() - 1);
			this.index.set(index);
		} else {
			this.index = null;
		}
		this.allowLoop = allowLoop;
	}

	/**
	 * Alternative constructor, omitting {@code index} argument.
	 * 
	 * @param list
	 *            list of items, musn't be modified after passing here
	 * @param allowLoop
	 *            what should be done when user wants to cycle over first or
	 *            last element: to continue from the other side or to do nothing
	 * @see #ListWalker(List, int, boolean)
	 */
	public ListWalker(List<T> list, boolean allowLoop) {
		this(list, 0, allowLoop);
	}

	/**
	 * Returns {@code true}, if the list has any items.
	 * 
	 * @return
	 */
	public boolean hasItems() {
		return index != null;
	}

	/**
	 * Increments index regarding {@code #allowLoop} attribute.
	 */
	public void increment() {
		if (hasItems()) {
			if (allowLoop)
				index.increment();
			else
				index.incrementIfNotMax();
		}
	}

	/**
	 * Decrements index regarding {@code #allowLoop} attribute.
	 */
	public void decrement() {
		if (hasItems()) {
			if (allowLoop)
				index.decrement();
			else
				index.decrementIfNotMin();
		}
	}

	/**
	 * Returns item on which the index is pointing, or {@code null} if there are
	 * no items in the list.
	 * 
	 * @return
	 */
	public T getSelected() {
		if (hasItems())
			return list.get(index.get());
		else
			return null;
	}

}
