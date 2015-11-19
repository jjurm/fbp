package net.talentum.fbp.display;

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
	 * @param start
	 * @param end
	 */
	public DisplaySection(int row, int start, int end) {
		this.row = row;
		this.start = start;
		this.end = end;
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

}
