package net.talentum.fbp.logging;

import org.apache.logging.log4j.Level;

/**
 * Class for creation and enumeration of custom {@link Level}s.
 * <ul>
 * <li><b>DIAG</b> - informational logs of extremely high importance (
 * {@code intValue}: 150 )</li>
 * </ul>
 * 
 * @author JJurM
 */
public class Levels {

	public static final Level DIAG = Level.getLevel("DIAG");

}
