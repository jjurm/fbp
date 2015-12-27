package net.talentum.fbp.ui;

/**
 * Basic implementation of {@link StringContent} that works with a static array
 * of strings. Each row of characters is stored as one item of the array.
 * 
 * @author JJurM
 */
public class ArrayStringContent extends AbstractStringContent {

	private String[] rows;

	/**
	 * Default constructor, retrieves and saves array of rows.
	 * 
	 * @param rows
	 *            Array of strings, can be {@code null}.
	 */
	public ArrayStringContent(String[] rows) {
		if (rows == null)
			this.rows = new String[0];
		else
			this.rows = rows;
	}

	@Override
	protected String getRow0(int row) {
		return rows[row];
	}

	@Override
	public int getRowCount() {
		return rows.length;
	}

}
