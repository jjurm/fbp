package net.talentum.fbp.logging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.Booleans;

/**
 * This {@link Appender} stacks logged events until
 * {@link #setAppender(Appender)} is called, when all stacked events are logged
 * to the given appender. From then on, every next event will be directly logged
 * to the stored appender.
 * 
 * @author JJurM
 */
@Plugin(name = "Stack", category = "Core", elementType = "appender", printObject = true)
public class StackAppender extends AbstractAppender {
	private static final Logger LOG = LogManager.getLogger();
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 * 
	 * @param name
	 */
	protected StackAppender(String name, boolean ignoreExceptions) {
		super(name, null, null, ignoreExceptions);
	}

	/**
	 * Plugin factory method provided for Log4j2.
	 * 
	 * @param layout
	 * @param filter
	 * @param name
	 * @param ignore
	 * @return created {@link StackAppender} or {@code null} on failure.
	 */
	@PluginFactory
	public static StackAppender createAppender(@PluginElement("Layout") Layout<? extends Serializable> layout,
			@PluginElement("Filters") final Filter filter, @PluginAttribute("name") final String name,
			@PluginAttribute(value = "ignoreExceptions", defaultBoolean = true) final String ignore) {

		if (name == null) {
			LOG.error("log4j2: No name provided for StackAppender");
			return null;
		}
		final boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
		return new StackAppender(name, ignoreExceptions);
	}

	protected List<LogEvent> logEvents = new ArrayList<LogEvent>();
	protected Appender appender;

	@Override
	public void append(LogEvent event) {
		if (appender != null) {
			appender.append(event);
		} else {
			logEvents.add(event);
		}
	}

	/**
	 * Sets current appender to the new one. Stacked events and every new events
	 * will be logged directly to the given appender.
	 * 
	 * @param appender
	 *            new {@link Appender}
	 */
	public void setAppender(Appender appender) {
		this.appender = appender;
		for (LogEvent event : logEvents) {
			appender.append(event);
		}
		logEvents.clear();
	}

}
