package net.talentum.fbp.system;

import java.util.Properties;

/**
 * Class that runs the program, the only executable class in project.
 * 
 * @author JJurM
 */
public class Run {
	
	/**
	 * The main executable method of FBP.
	 * 
	 * @param args command-line arguments
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
		
		registerShutdownHook();
		setSystemProperties();
		
		Main.run(args);
		
	}
	
	/**
	 * This sets system properties to predefined values.
	 */
	private static void setSystemProperties() {

		Properties p = System.getProperties();

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
	
}
