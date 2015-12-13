package net.talentum.fbp.context;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;

import net.talentum.fbp.context.menu.SelectionInlineContext;
import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.hardware.drivers.DisplayDriver;
import net.talentum.fbp.logging.InternalAppender;
import net.talentum.fbp.system.Config;
import net.talentum.fbp.util.RangeIndex;

/**
 * Context for browsing all logs, with filtering based on selected log level.
 * Contains instance class {@link LogLevelSelection} (inline context) which
 * provides ability to change level from menu.
 * 
 * @author JJurM
 */
public class LogBrowser extends InfoContext {

	protected Level level = Level.ALL;
	protected RangeIndex index;
	protected List<LogEvent> logs;

	public LogBrowser(ContextHolder contextHolder) {
		super("Log browser", contextHolder);
	}

	@Override
	protected void enter() {
		logs = InternalAppender.getLogEvents().stream().filter(event -> level.isLessSpecificThan(event.getLevel()))
				.collect(Collectors.toList());
		int max = logs.size() - 1;
		index = new RangeIndex(max);
		index.set(max);
	}

	@Override
	protected void pressedLeft() {
		// to the previous event
		index.decrement();
		dispatchRedrawRequest();
	}

	@Override
	protected void pressedRight() {
		// to the next event
		index.increment();
		dispatchRedrawRequest();
	}

	@Override
	public void render(DisplaySection section, DisplayDriver display) {
		display.write("Browse logs", section);
	}

	@Override
	public void renderContext(DisplayDriver display) {
		// render title bar
		display.wline(0, getName());

		LogEvent log = logs.get(index.get());
		String thread = log.getThreadName();
		String level = log.getLevel().name();
		String sourceClass = log.getLoggerFqcn();
		String message = log.getMessage().getFormattedMessage();

		display.wline(1, level + StringUtils.repeat(' ', Config.getDisplayColumns() - level.length() - thread.length())
				+ thread);
		display.wline(2, sourceClass);
		display.wline(3, StringUtils.left(message, Config.getDisplayColumns()));
	}

	/**
	 * Inline context binded to instance of {@link LogBrowser} that allows
	 * changing it's level from menu.
	 * 
	 * @author JJurM
	 */
	public class LogLevelSelection extends SelectionInlineContext<Level> {

		public LogLevelSelection() {
			// maps levels to options
			super(Arrays.asList(Level.values()).stream()
					.map(level -> new SelectionInlineContext.Option<Level>(level, level.name()))
					.collect(Collectors.toList()), false);
		}

		@Override
		protected void changed(Level value) {
			level = value;
		}
		
	}

}
