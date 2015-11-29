package net.talentum.fbp.system;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NameNotFoundException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

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

	/**
	 * This will search in a map for the name and create configuration from the
	 * path.
	 * 
	 * @param name
	 *            Name of the configuration to search for in the map
	 * @return
	 * @throws ConfigurationException
	 * @throws NameNotFoundException
	 *             when the name is not registered in the map
	 */
	public static HierarchicalConfiguration getConfiguration(String name)
			throws ConfigurationException, NameNotFoundException {
		String path = configFiles.get(name);
		if (path == null) {
			throw new NameNotFoundException("There is no '" + name + "' configuration file");
		}
		return new XMLConfiguration(path);
	}

}
