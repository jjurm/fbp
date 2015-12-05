package net.talentum.fbp.system;

import org.apache.commons.configuration.HierarchicalConfiguration;

/**
 * Provides quick and convenient access to frequent configuration values.
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
