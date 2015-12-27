package net.talentum.fbp.logging;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import net.talentum.fbp.test.TestBase;

public class InternalAppenderTest extends TestBase {

	private Logger LOG = LogManager.getLogger();

	@Test
	public void testLogger() {
		int logCount = InternalAppender.getLogEvents().size();
		LOG.info("test");
		logCount++;
		assertThat(InternalAppender.getLogEvents().size(), is(logCount));
		assertEquals(InternalAppender.getLogEvents().get(logCount - 1).getMessage().getFormattedMessage(), "test");
	}

}
