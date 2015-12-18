package net.talentum.fbp.display;

import org.apache.commons.lang3.StringUtils;

/**
 * Utility class providing methods for creating common {@link ElementRenderer}s.
 * 
 * @author JJurM
 */
public class Renderers {

	/**
	 * Creates renderer that writes given string centered related to the given
	 * {@link DisplaySection}.
	 * 
	 * @param str
	 * @return
	 */
	public static ElementRenderer centered(String str) {
		return (section, display) -> display.write(StringUtils.center(str, section.getWidth()));
	}

	/**
	 * Creates renderer that displays text {@code "Nothing to show"}.
	 * 
	 * @param centered whether the output should be centered
	 * @return
	 */
	public static ElementRenderer nothingToShow(boolean centered) {
		final String str = "Nothing to show";
		if (centered)
			return (section, display) -> display.write(StringUtils.center(str, section.getWidth()), section);
		else
			return (section, display) -> display.write(str, section);
	}

}
