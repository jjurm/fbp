package net.talentum.fbp.ui;

/**
 * Interface providing access to a string content. The content is returned in
 * form of rows which can be rendered on display. Content can be static or
 * dynamically generated.
 * 
 * @author JJurM
 */
public interface StringContent {

	/**
	 * Returns string associated with the given row.
	 * 
	 * @param row
	 *            row-index
	 * @return
	 */
	public String getRow(int row);

	/**
	 * Returns number of rows this {@link StringContent} can provide.
	 * 
	 * @return
	 */
	public int getRowCount();

}
