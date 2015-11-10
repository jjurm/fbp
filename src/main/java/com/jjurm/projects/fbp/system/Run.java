package com.jjurm.projects.fbp.system;

import java.util.Properties;

/**
 * Class that runs the program, the only executable class in project.
 * 
 * @author JJurM
 */
public class Run {
	
	public static void main(String[] args) {
		
		run(args);
		
	}
	
	public static void run(String[] args) {
		
		registerShutdownHook();
		setSystemProperties();
		
		Main.start(args);
		
	}
	
	private static void setSystemProperties() {

		Properties p = System.getProperties();
		p.setProperty("log4j.configurationFile", "config/log4j2.xml");

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
