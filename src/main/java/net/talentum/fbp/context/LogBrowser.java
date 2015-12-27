package net.talentum.fbp.context;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import net.talentum.fbp.context.menu.SelectionInlineContext;
import net.talentum.fbp.display.DisplayRenderer;
import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.display.Renderers;
import net.talentum.fbp.hardware.drivers.DisplayDriver;
import net.talentum.fbp.logging.InternalAppender;
import net.talentum.fbp.system.Config;

/**
 * Context for browsing logs, with filtering based on selected log level.
 * Contains instance class {@link LogLevelSelection} (inline context) which
 * provides ability to change level from menu.
 * 
 * @author JJurM
 */
public class LogBrowser extends PageBrowser {

	// @formatter:off
	private static DateTimeFormatter dtFormatter = new DateTimeFormatterBuilder()
			.appendMonthOfYear(2)
			.appendLiteral('-')
			.appendDayOfMonth(2)
			.appendLiteral(' ')
			.appendHourOfDay(2)
			.appendLiteral(':')
			.appendMinuteOfHour(2)
			.appendLiteral(':')
			.appendSecondOfMinute(2)
			.toFormatter();
	// @formatter:on

	private int COLS = Config.getDisplayColumns();

	protected Level level = Level.ALL;

	public LogBrowser(ContextHolder contextHolder) {
		super("Log browser", contextHolder);
	}

	@Override
	protected String getLabel() {
		return "Browse logs";
	}

	@Override
	protected List<DisplayRenderer> getPages() {
		// @formatter:off
		return InternalAppender.getLogEvents()
				.stream()
				.filter(event -> level.isLessSpecificThan(event.getLevel()))
				.map(LogEventRenderer::new)
				.collect(Collectors.toList());
		// @formatter:on
	}

	private DisplayRenderer defaultRenderer = (display) -> Renderers.nothingToShow(false).render(new DisplaySection(1),
			display);

	@Override
	protected DisplayRenderer getDefaultRenderer() {
		return defaultRenderer;
	}

	/**
	 * Class capable of rendering a {@link LogEvent}, in cooperation with
	 * instance of the surrounding {@link LogBrowser}.
	 * 
	 * @author JJurM
	 */
	public class LogEventRenderer implements DisplayRenderer {

		protected LogEvent event;

		public LogEventRenderer(LogEvent event) {
			this.event = event;
		}

		@Override
		public void renderContext(DisplayDriver display) {
			// render title bar
			display.wline(0, getName());

			DateTime dateTime = new DateTime(event.getTimeMillis());
			String dt = dateTime.toString(dtFormatter);
			String level = event.getLevel().name();
			String message = event.getMessage().getFormattedMessage();

			display.wline(1, dt + StringUtils.repeat(' ', COLS - dt.length() - level.length()) + level);
			display.wline(2, StringUtils.substring(message, 0, COLS));
			display.wline(3, StringUtils.substring(message, COLS, COLS * 2));
		}

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
					.collect(Collectors.toList()), true);
		}

		@Override
		protected void changed(Level value) {
			level = value;
		}

		@Override
		public String getName() {
			return "LogLevelSelection";
		}

		@Override
		protected String getLabel() {
			return "Level";
		}

	}

}
