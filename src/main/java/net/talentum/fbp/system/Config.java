package net.talentum.fbp.system;

import org.apache.commons.configuration.HierarchicalConfiguration;

/**
 * Provides quick and convenient access to frequent configuration values.
 * 
 * <p>
 * Although many of the fields are totally constant without any possibility of
 * change due to the hardware model, developers are encouraged to use this
 * methods for clarity and self-explanation of code.
 * </p>
 * <p>
 * <em>Example:</em> this way
 * </p>
 * 
 * <pre>
 * 	for (int i = 0; i < Config.getDisplayRows(); i++) {...}
 * </pre>
 * <p>
 * should be preferred over this
 * </p>
 * 
 * <pre>
 * 	for (int i = 0; i < 4; i++) {...}
 * </pre>
 * 
 * @author JJurM
 */
public final class Config {

	// hidden constructor
	private Config() {
	}

	private static HierarchicalConfiguration config;

	private static int displayColumns;
	private static int displayRows;

	/**
	 * Fills some static fields. This method shall be called
	 */
	public static void init() {
		config = ConfigurationManager.getGeneralConfiguration();

		displayColumns = config.getInt("hardware/display/columns");
		displayRows = config.getInt("hardware/display/rows");
	}

	/**
	 * Returns general configuration.
	 * 
	 * @return
	 * @see #init()
	 */
	public static HierarchicalConfiguration get() {
		return ConfigurationManager.getGeneralConfiguration();
	}

	// === Getters ===

	public static int getDisplayColumns() {
		return displayColumns;
	}

	public static int getDisplayRows() {
		return displayRows;
	}

}
