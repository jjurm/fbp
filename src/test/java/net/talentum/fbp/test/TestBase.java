package net.talentum.fbp.test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.BeforeClass;
import org.junit.Test;

import net.talentum.fbp.system.Config;
import net.talentum.fbp.system.ConfigurationManager;
import net.talentum.fbp.system.Run;

/**
 * This base is designed for test purposes and should never appear in normal
 * run.
 * <p>
 * Every class containing tests should extends {@link TestBase}. All static
 * methods of superclass annotated with {@link BeforeClass} will be run before
 * any tests. It assures that the initialization will be done before first
 * needed.
 * </p>
 * <p>
 * This class once (upon first call) initializes configuration, to be accessible
 * within tests.
 * </p>
 * 
 * @author JJurM
 */
public class TestBase {

	private static AtomicBoolean initializationRun = new AtomicBoolean(false);

	@BeforeClass
	public static void setUpBase() throws ConfigurationException {
		// assure the code will be run only once
		if (initializationRun.compareAndSet(false, true)) {

			System.out.println("** Tests initialization **");

			// get project properties
			Run.retrieveProjectProperties();

			// initialize ConfigurationManager - creates configurations
			ConfigurationManager.init();

			// initialize Config class
			Config.init();

		}
	}

	@Test
	public void testConfiguration() {
		assertEquals(Config.getDisplayRows(), 4);
	}

}
