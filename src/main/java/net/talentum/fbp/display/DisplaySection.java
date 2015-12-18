package net.talentum.fbp.display;

import org.apache.commons.lang3.StringUtils;

import net.talentum.fbp.system.Config;

/**
 * Class representing a specific section on display.
 * 
 * Display section consists of three parameters:
 * <ul>
 * <li>{@code row}: index of desired row on the display</li>
 * <li>{@code start}: index of the first column (including)</li>
 * <li>{@code end}: index of the last column (excluding)</li>
 * </ul>
 * 
 * Therefore the total number of characters within borders of the section will
 * always be {@code end-start}.
 * 
 * @author JJurM
 */
public class DisplaySection {

	protected int row;
	protected int start;
	protected int end;

	/**
	 * Default constructor.
	 * 
	 * @param row
	 *            index of desired row on the display
	 * @param start
	 *            index of the first column (including)
	 * @param end
	 *            index of the last column (excluding)
	 */
	public DisplaySection(int row, int start, int end) {
		if (start > end) {
			throw new IllegalArgumentException(String.format("Start can't be greater than End (%d, %d)", start, end));
		}
		this.row = row;
		this.start = start;
		this.end = end;
	}

	/**
	 * Alternative constructor with only {@code row} argument. Spans across
	 * entire width of the display (uses {@link Config#getDisplayColumns()}).
	 * 
	 * @param row
	 *            index of desired row on the display
	 */
	public DisplaySection(int row) {
		this(row, 0, Config.getDisplayColumns());
	}

	// === Getters ===

	public int getRow() {
		return row;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	/**
	 * Returns width of the section, equal to the number of characters included
	 * ({@code end - start}).
	 * 
	 * @return
	 */
	public int getWidth() {
		return end - start;
	}

	// === Auxiliary methods ===

	/**
	 * Returns string of length {@code end-start}. If the given string is
	 * shorter, spaces are added to right-pad the string. If the given string is
	 * longer, substring is returned.
	 * 
	 * @param string
	 * @return string of length {@code end-start}
	 */
	public String pad(String string) {
		int len = end - start;
		return StringUtils.rightPad(StringUtils.left(string, len), len);
	}

}
