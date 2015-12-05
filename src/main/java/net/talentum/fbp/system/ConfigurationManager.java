package net.talentum.fbp.system;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.MergeCombiner;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class provides methods for checking and for loading specific
 * configuration files.
 * <p>
 * On program start, method {@link #constructConfigurations()} shall be called.
 * </p>
 * 
 * @author JJurM
 */
public class ConfigurationManager {
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * This map stores configuration objects since they are constructed.
	 */
	private static Map<String, HierarchicalConfiguration> configurations;

	private static CombinedConfiguration generalConfig;

	/**
	 * This method checks for missing or unreadable configuration files, builds
	 * associated configuration objects and finally builds general configuration
	 * file, throwing {@link ConfigurationException} in case of failure. In
	 * general configuration, keys of the configurations determine the path of
	 * their root nodes in the general configuration.
	 * 
	 * @throws ConfigurationException
	 */
	public static void init() throws ConfigurationException {
		HierarchicalConfiguration.setDefaultExpressionEngine(new XPathExpressionEngine());
		
		buildConfigurations();
		buildGeneralConfig();
	}

	private static void buildConfigurations() throws ConfigurationException {
		configurations = new HashMap<String, HierarchicalConfiguration>();
		String key;
		CombinedConfiguration comb;
		File def;

		key = "database";
		configurations.put(key, new XMLConfiguration(checkFile(key, "config/database.xml", true)));

		key = "fbp";
		comb = new CombinedConfiguration(new MergeCombiner());
		def = checkFile(key, "config/fbp.default.xml", true);
		comb.addConfiguration(new XMLConfiguration(checkFile(key, "config/fbp.xml", false)));
		comb.addConfiguration(new XMLConfiguration(def));
		configurations.put(key, comb);

		key = "hardware";
		comb = new CombinedConfiguration(new MergeCombiner());
		def = checkFile(key, "config/hardware.default.xml", true);
		comb.addConfiguration(new XMLConfiguration(checkFile(key, "config/hardware.xml", false)));
		comb.addConfiguration(new XMLConfiguration(def));
		configurations.put(key, comb);

	}

	private static void buildGeneralConfig() {
		generalConfig = new CombinedConfiguration();
		for (String key : configurations.keySet()) {
			generalConfig.addConfiguration(getConfiguration(key), key, key);
		}
	}

	private static File checkFile(String key, String filename, boolean required) throws ConfigurationException {
		File file = new File(filename);

		// check if the file exists
		if (file.exists()) {
			// check if the file can be read
			if (file.canRead()) {
				// expected point
			} else {
				// file could not be read
				if (required) {
					// last configuration file must be readable
					LOG.error(String.format("config: Existing required configuration file ('%s') is not readable: %s",
							key, filename));
					throw new ConfigurationException(String.format("unreadable file: %s", filename));
				} else {
					LOG.warn(String.format("config: Existing configuration file ('%s') is not readable: %s", key,
							filename));
				}
			}
		} else {
			// file does not exist
			if (required) {
				// last configuration must exist
				LOG.error(String.format("config: Required configuration file ('%s') is missing: %s", key, filename));
				throw new ConfigurationException(String.format("missing file: %s", filename));
			} else {
				LOG.warn(String.format("config: Configuration file ('%s') is missing: %s", key, filename));
			}
		}
		return file;
	}

	/**
	 * This will search in a map for the name and create configuration from the
	 * path.
	 * 
	 * @param name
	 *            Name of the configuration to search for in the map
	 * @return
	 */
	public static HierarchicalConfiguration getConfiguration(String name) {
		return configurations.get(name);
	}

	/**
	 * Returns general configuration.
	 * 
	 * @return
	 * @see #init()
	 */
	public static HierarchicalConfiguration getGeneralConfiguration() {
		return generalConfig;
	}

}
