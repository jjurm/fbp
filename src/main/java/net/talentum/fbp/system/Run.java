package net.talentum.fbp.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class that runs the program, the only executable class in project.
 * 
 * @author JJurM
 */
public class Run {

	public static final Properties projectProperties = new Properties();

	private static String projectName;
	private static String projectVersion;

	/**
	 * The main executable method of FBP.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {

		run(args);

	}

	/**
	 * Elementary method to initialize the run.
	 * 
	 * @param args
	 */
	public static void run(String[] args) {

		retrieveProjectProperties();
		registerShutdownHook();

		Main.run(args);

	}

	/**
	 * This adds important shutdown hook to {@link Runtime}.
	 */
	private static void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				Main.shutdownActions();
			}
		});
	}

	/**
	 * Retrieves project properties from {@code 'project.properties'} file.
	 *
	 * @throws IOException
	 */
	private static void retrieveProjectProperties() {

		try (InputStream in = Run.class.getResourceAsStream("/project.properties")) {
			projectProperties.load(in);

			projectName = projectProperties.getProperty("project.name");
			projectVersion = projectProperties.getProperty("project.version");
		} catch (IOException e) {
			System.out.println("Could not start, missing project.properties");
			e.printStackTrace();
		}

	}

	public static String getProjectName() {
		return projectName;
	}

	public static String getProjectVersion() {
		return projectVersion;
	}

}
