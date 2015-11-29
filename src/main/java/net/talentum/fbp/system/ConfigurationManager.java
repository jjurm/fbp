package net.talentum.fbp.system;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;

/**
 * This class provides methods for checking and for loading specific
 * configuration files.
 * 
 * @author JJurM
 */
public class ConfigurationManager {

	/**
	 * Map where <em>name -> path</em> pairs of configuration files are stored.
	 */
	private static Map<String, String> configFiles = new HashMap<String, String>();

	static {
		configFiles.put("database", "config/database.xml");
	}

	/**
	 * This method checks for missing or unreadable configuration files and
	 * throws {@link ConfigurationException} in case of failure.
	 * 
	 * @throws ConfigurationException
	 */
	public static void performChecks() throws ConfigurationException {
		for (Map.Entry<String, String> config : configFiles.entrySet()) {

			// check if file exists
			File f = new File(config.getValue());
			if (!f.exists()) {
				throw new ConfigurationException(
						"'" + config.getKey() + "' configuration is missing: " + config.getValue());
			}

			// check if file can be read
			if (!f.canRead()) {
				throw new ConfigurationException(
						"'" + config.getKey() + "' configuration cannot be read: " + config.getValue());
			}

		}
	}

}
