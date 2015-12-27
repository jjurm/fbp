package net.talentum.fbp.ui;

/**
 * Interface providing access to a string content. The content is offered in
 * form of rows that can be rendered on display. This implies that the row
 * lengths should be adapted to the width of the display, not to be longer. And
 * this should preferably be adjusted dynamically.
 * 
 * <p>
 * Content returned by {@link StringContent} can be either static or generated
 * dynamically.
 * </p>
 * 
 * <p>
 * Basic implementation with static array of strings is
 * {@link ArrayStringContent}.
 * </p>
 * 
 * @author JJurM
 * @see ArrayStringContent
 */
public interface StringContent {

	/**
	 * Returns string associated with the given row.
	 * 
	 * @param row
	 *            row-index
	 * @return row of content at specified index
	 */
	public String getRow(int row);

	/**
	 * Returns number of rows this {@link StringContent} can provide.
	 * 
	 * @return
	 */
	public int getRowCount();

}
