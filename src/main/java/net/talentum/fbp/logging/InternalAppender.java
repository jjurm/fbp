package net.talentum.fbp.logging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * Only stores log for internal purposes of the program, does not write them
 * anywhere.
 * <p>
 * This class is singleton, so there exists globally at most one instance of
 * {@link InternalAppender}. Name of the appender is always {@code "Internal"}.
 * </p>
 * 
 * @author JJurM
 */
@Plugin(name = "Internal", category = "Core", elementType = "appender", printObject = true)
public class InternalAppender extends AbstractAppender {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * List where {@link LogEvent}s are stored. Can be
	 */
	static List<LogEvent> logEvents = new ArrayList<LogEvent>();

	private static InternalAppender instance;

	/**
	 * Default constructor.
	 * 
	 * @param name
	 * @param filter
	 * @param ignoreExceptions
	 */
	private InternalAppender(Filter filter, boolean ignoreExceptions) {
		super("Internal", filter, null, ignoreExceptions);
	}

	/**
	 * This is getter method returning the only instance of
	 * {@link InternalAppender}, if it was already created, otherwise
	 * {@code null}.
	 * 
	 * @return
	 */
	public static InternalAppender getInstance() {
		return instance;
	}

	/**
	 * Plugin factory method provided for Log4j2. Returns always the same
	 * instance, as long as this class is singleton.
	 * <p>
	 * Arguments {@code layout} and {@code name} are ignored.
	 * </p>
	 * 
	 * @param layout
	 * @param filter
	 * @param name
	 * @param ignore
	 * @return
	 * @see InternalAppender
	 */
	@PluginFactory
	public static InternalAppender createAppender(@PluginElement("Layout") Layout<? extends Serializable> layout,
			@PluginElement("Filters") final Filter filter, @PluginAttribute("name") final String name,
			@PluginAttribute(value = "ignoreExceptions", defaultBoolean = true) final String ignore) {

		final boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);

		if (instance == null) {
			LOG.trace("log4j2: Creating instance of InternalAppender");
			instance = new InternalAppender(filter, ignoreExceptions);
		}
		return instance;
	}

	@Override
	public void append(LogEvent event) {
		logEvents.add(event);
	}

	/**
	 * Getter of field {@link #logEvents}. Returns unmodifiable list.
	 * 
	 * @return
	 */
	public static List<LogEvent> getLogEvents() {
		return Collections.unmodifiableList(logEvents);
	}

}
