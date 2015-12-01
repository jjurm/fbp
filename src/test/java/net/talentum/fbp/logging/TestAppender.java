package net.talentum.fbp.logging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

/**
 * {@link Appender} only for testing purposes, doesn't output anything, only
 * stores the {@code LogEvent}s.
 * 
 * @author JJurM
 */
@Plugin(name = "Test", category = "Core", elementType = "appender", printObject = true)
public class TestAppender extends AbstractAppender {
	private static final long serialVersionUID = 1L;

	/**
	 * List where all received {@link LogEvent}s are stored.
	 */
	private List<LogEvent> logEvents = new ArrayList<LogEvent>();
	
	protected TestAppender(String name, Filter filter) {
		super(name, filter, null);
	}
	
	/**
	 * Plugin factory method provided for Log4j2.
	 * 
	 * @param layout
	 * @param filter
	 * @param name
	 * @param ignore
	 * @return created {@link TestAppender} or {@code null} on failure.
	 */
	@PluginFactory
	public static TestAppender createAppender(@PluginElement("Layout") Layout<? extends Serializable> layout,
			@PluginElement("Filters") final Filter filter, @PluginAttribute("name") final String name,
			@PluginAttribute(value = "ignoreExceptions", defaultBoolean = true) final String ignore) {

		if (name == null) {
			return null;
		}
		return new TestAppender(name, filter);
	}

	@Override
	public void append(LogEvent event) {
		logEvents.add(event);
	}

	/**
	 * Returns unmodifiable list of {@link LogEvent}s.
	 * 
	 * @return
	 */
	public List<LogEvent> getLogEvents() {
		return Collections.unmodifiableList(logEvents);
	}
	
}
