package net.talentum.fbp.system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Utils {
	private static final Logger LOG = LogManager.getLogger();

	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			LOG.error(e);
			Thread.currentThread().interrupt();
		}
	}
	
	public static void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			LOG.error(e);
			Thread.currentThread().interrupt();
		}
	}
}
