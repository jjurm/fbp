package net.talentum.fbp.ui;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Basic implementation of {@link StringContent} that works with a static
 * string. When constructed, it splits the string to rows according to the
 * required width. Allows specifying inner padding which adds spaces to the left
 * and the right side.
 * 
 * @author JJurM
 */
public class AutoWrapStringContent extends AbstractStringContent {

	protected String[] rows;
	protected int padding;

	/**
	 * Default constructor.
	 * 
	 * @param text
	 * @param width
	 * @param padding
	 * @see AutoWrapStringContent
	 */
	public AutoWrapStringContent(String text, int width, int padding) {
		rows = split(text, width - 2 * padding);

		if (padding > 0) {
			String padString = StringUtils.repeat(' ', padding);
			for (int i = 0; i < rows.length; i++) {
				rows[i] = StringUtils.rightPad(padString + rows[i], width);
			}
		}
	}

	/**
	 * Alternative constructor, omitting {@code padding} argument with default
	 * value of 0.
	 * 
	 * @param text
	 * @param width
	 * @see #AutoWrapStringContent(String, int)
	 */
	public AutoWrapStringContent(String text, int width) {
		this(text, width, 0);
	}

	/**
	 * Splits given string into array of strings, each of maximum width as
	 * specified.
	 * 
	 * @param text
	 * @param width
	 */
	protected static String[] split(String text, int width) {
		return WordUtils.wrap(text, width, "\n", true).split("\n");
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
