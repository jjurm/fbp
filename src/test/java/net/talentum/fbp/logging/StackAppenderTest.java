package net.talentum.fbp.logging;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.Before;
import org.junit.Test;

import net.talentum.fbp.test.TestBase;

public class StackAppenderTest extends TestBase {

	private Logger LOG;
	private TestAppender testAppender;
	private StackAppender testStackAppender;

	@Before
	public void getLogger() {
		LOG = LogManager.getLogger();
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();

		testAppender = (TestAppender) config.getAppender("Test");
		testStackAppender = (StackAppender) config.getAppender("TestStack");
	}

	@Test
	public void testLogger() {
		int logCount = testAppender.getLogEvents().size();
		LOG.info("test");
		logCount++;
		assertThat(testAppender.getLogEvents().size(), is(logCount));
		assertEquals(testAppender.getLogEvents().get(logCount - 1).getMessage().getFormattedMessage(), "test");
	}

	@Test
	public void testStackAppender() {
		TestAppender testAppender2 = new TestAppender("Test2", null);
		LOG.info("test1");
		LOG.info("test2");
		assertThat(testAppender2.getLogEvents().size(), is(0));
		testStackAppender.setAppender(testAppender2);
		assertThat(testAppender2.getLogEvents().size(), is(2));
		assertEquals(testAppender.getLogEvents().get(0).getMessage().getFormattedMessage(), "test1");
		assertEquals(testAppender.getLogEvents().get(1).getMessage().getFormattedMessage(), "test2");
		LOG.info("test3");
		assertThat(testAppender2.getLogEvents().size(), is(3));
		assertEquals(testAppender.getLogEvents().get(2).getMessage().getFormattedMessage(), "test3");
	}

}
