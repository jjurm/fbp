package com.jjurm.projects.fbp.system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main starting class of the program.
 * 
 * @author JJurM
 */
public class Main {
	private static final Logger LOG = LogManager.getLogger();

	public static void start(String args[]) {
		
		LOG.info("Program started!");
		System.out.println("ok");
		
	}

	public static void shutdownActions() {
		
		// these actions will be performed once during shutdown
		
	}
	
}
