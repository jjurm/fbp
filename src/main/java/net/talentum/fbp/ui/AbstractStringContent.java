package net.talentum.fbp.ui;

/**
 * Abstract implementation of {@link StringContent} that provides some basic and
 * common behavior. Provides check, if the index of requested row is within
 * bounds.
 * 
 * @author JJurM
 */
public abstract class AbstractStringContent implements StringContent {

	/**
	 * {@inheritDoc}
	 * <p>
	 * This implementation returns {@code null} if {@code row >= getRowCount()}.
	 * </p>
	 * 
	 * @throws IllegalArgumentException
	 *             when {@code row} is negative
	 */
	@Override
	public final String getRow(int row) {
		if (row >= getRowCount()) {
			return null;
		} else if (row < 0) {
			throw new IllegalArgumentException("Index of row can't be negative");
		} else {
			return getRow0(row);
		}
	}

	/**
	 * Protected method which must be overridden by child classes.
	 * <p>
	 * Implementation of this method does not have to care about indices out of
	 * interval {@code [0, getRowCount() - 1]}.
	 * 
	 * @param row
	 *            row-index
	 * @return row of content at specified index
	 */
	protected abstract String getRow0(int row);

}
