package net.talentum.fbp.ui;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.talentum.fbp.context.Context;
import net.talentum.fbp.context.ContextHolder;
import net.talentum.fbp.context.HomeScreen;
import net.talentum.fbp.context.LogBrowser;
import net.talentum.fbp.context.StaticInfoContext;
import net.talentum.fbp.context.menu.BackMenuItem;
import net.talentum.fbp.context.menu.BasicMenu;
import net.talentum.fbp.context.menu.MainMenu;
import net.talentum.fbp.context.menu.ShutdownMenuItem;
import net.talentum.fbp.display.RedrawRequestHandler;
import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonEventHandler;
import net.talentum.fbp.hardware.drivers.DisplayDriver;
import net.talentum.fbp.system.Run;

/**
 * Class that is responsible for rendering content on display. It does not
 * directly draw to {@link DisplayDriver}, but uses {@link Context}s instead.
 * There is always exactly one active context renderer, which renders content on
 * display and receives {@link ButtonEvent}s delegated from this class.
 * 
 * {@link UIManager} is also {@link RedrawRequestHandler} to be able to pass
 * itself to {@link Context} as handler for the redraw requests.
 * 
 * @author JJurM
 */
public class UIManager implements ButtonEventHandler, RedrawRequestHandler, ContextHolder {
	private static final Logger LOG = LogManager.getLogger();

	private DisplayDriver displayDriver;
	private HomeScreen homeScreen;

	private Context activeContext;

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	/**
	 * Default constructor.
	 * 
	 * @param displayDriver
	 */
	public UIManager(DisplayDriver displayDriver) {
		this.displayDriver = displayDriver;
	}

	/**
	 * Creates menu hierarchy and sets created instance of {@link HomeScreen} as
	 * first active context.
	 */
	public void init() {
		constructMenuHierarchy();
		switchContext(homeScreen);
	}

	/**
	 * Whole menu hierarchy is defined and created in this method. Also prepares
	 * underlying {@link Context}s.
	 */
	protected void constructMenuHierarchy() {
		LogBrowser logBrowser = new LogBrowser(this);

		// @formatter:off
		homeScreen = new HomeScreen(this);
		homeScreen.setMainMenu(
				BasicMenu.Builder.create(MainMenu.getConstructor(homeScreen), "Main menu", this)
				.item(
						BasicMenu.Builder.create("Logs", this)
						.item(logBrowser.new LogLevelSelection())
						.item(logBrowser)
						.item(new BackMenuItem())
						.build()
				)
				.item(
						BasicMenu.Builder.create("Settings", this)
						.item(new BackMenuItem())
						.build()
				)
				.item(new StaticInfoContext("About FBP", new AutoWrapStringContent("FBP project is developed with care and enthusiasm.", 1), this))
				.item(new ShutdownMenuItem())
				.item(new BackMenuItem())
				.build()
		);
		// @formatter:on
	}

	@Override
	public Context getContext() {
		return activeContext;
	}

	/**
	 * This method performs four simple steps:
	 * <ul>
	 * <li>deregisters itself (as handler) from currently active {@link Context}
	 * </li>
	 * <li>makes given context active</li>
	 * <li>registers itself as handler in newly active context</li>
	 * <li>redraws display</li>
	 * </ul>
	 * 
	 * @param context
	 */
	@Override
	public void switchContext(Context context) {
		LOG.debug(
				String.format("display: Switching context to %s: %s", context.getName(), context.getClass().getName()));

		// deregister handler from old context
		if (activeContext != null)
			activeContext.removeRedrawRequestHandler(this);

		// make given context active
		activeContext = context;

		// register handler in new context
		activeContext.addRedrawRequestHandler(this);

		// redraw context
		activeContext.renderContext(displayDriver);
	}

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		Future<?> f = buttonStateChangedAsync(event);
		try {
			f.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Future<?> buttonStateChangedAsync(ButtonEvent event) {

		return executor.submit(new ButtonEventRunnable(event));

	}

	@Override
	public void handleRedrawRequest() {
		LOG.trace("display: Redraw request");

		// redraw active context
		activeContext.renderContext(displayDriver);
	}

	/**
	 * Simply returns home.
	 */
	public void goHome() {
		switchContext(homeScreen);
	}
	
	/**
	 * Will shutdown executor.
	 */
	public void shutdown() {
		executor.shutdown();
	}

	/**
	 * Helper class to execute expected action.
	 */
	private class ButtonEventRunnable implements Runnable {

		private ButtonEvent event;

		public ButtonEventRunnable(ButtonEvent event) {
			this.event = event;
		}

		@Override
		public void run() {
			// log event
			LOG.trace(String.format("display: Button event [%s] [%s]", event.getButtonType(), event.getButtonState()),
					event);

			try {
				// delegate all button events to active context
				activeContext.buttonStateChanged(event);
			} catch (Throwable e) {
				if (Run.isNormalRun()) {
					LOG.error("Exception thrown in UIManager's executor", e);
				} else {
					throw e;
				}
			}
		}

	}

}
