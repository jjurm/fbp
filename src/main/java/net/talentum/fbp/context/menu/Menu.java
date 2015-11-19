package net.talentum.fbp.context.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.talentum.fbp.context.Context;
import net.talentum.fbp.context.ContextHolder;
import net.talentum.fbp.context.ContextMenuItem;
import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.display.RedrawRequestHandler;
import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.ButtonState;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * Graphical menu usually displayed on a display; extends {@link Context}.
 * 
 * @author JJurM
 */
public class Menu extends ContextMenuItem implements RedrawRequestHandler {
	private static final Logger LOG = LogManager.getLogger();

	protected List<MenuItem> menuItems = new ArrayList<MenuItem>();

	protected int selected = 0;

	protected InlineContext activeInlineContext;

	/**
	 * Defines the lists index of first displayed {@link MenuItem}
	 */
	protected int scrollPosition = 0;

	/**
	 * Default constructor.
	 * 
	 * @param contextHolder
	 *            holder that holds this context
	 */
	public Menu(ContextHolder contextHolder) {
		super(contextHolder);
	}

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		if (activeInlineContext != null) {
			// delegate event processing to the InlineContext
			activeInlineContext.buttonStateChanged(event);
		} else if (event.getButtonState() == ButtonState.PRESSED) {
			switch (event.getButtonType()) {
			case LEFT:
				// move selection to the previous item
				selected--;
				if (selected < 0)
					selected = menuItems.size() - 1;
				adjustScrollPosition();
				break;
			case RIGHT:
				// move selection to the next item
				selected++;
				if (selected >= menuItems.size())
					selected = 0;
				adjustScrollPosition();
				break;
			case OK:
				// call selected item
				menuItems.get(selected).call(this);
				break;
			}
		}
	}

	/**
	 * This method will move scroll position if necessary to assert that the
	 * selected {@link MenuItem} will be shown.
	 */
	protected void adjustScrollPosition() {
		if (scrollPosition > selected) {
			scrollPosition = selected;
		} else if (selected - 2 > scrollPosition) {
			scrollPosition = selected - 2;
		}
	}

	@Override
	public void call(Menu menu) {
		// reset selected item index
		selected = 0;
		adjustScrollPosition();
		super.call(menu);
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
	 * Given {@code inlineContext} can be null, in which case the actual
	 * {@link InlineContext} will only be removed.
	 * 
	 * @param inlineContext
	 */
	void setInlineContext(InlineContext inlineContext) {

		// deregister handler from old inline context (if not null)
		if (activeInlineContext != null) {
			LOG.debug("menu: Returning from inline context back to menu");
			activeInlineContext.removeRedrawRequestHandler(this);
		}

		// make given context active
		activeInlineContext = inlineContext;

		// register handler in new inline context (if not null)
		if (activeInlineContext != null) {
			LOG.debug("menu: Switching to inline context " + activeInlineContext.getClass().getName());
			activeInlineContext.addRedrawRequestHandler(this);
		}

		// redraw itself (menu)
		dispatchRedrawRequest();
	}

	@Override
	public void request() {
		// this can be called from underlying InlineContexts
		dispatchRedrawRequest();
	}

	@Override
	public void renderContext(DisplayDriver displayDriver) {
		// TODO renderContext method of Menu
	}

	@Override
	public void render(DisplaySection displaySection, DisplayDriver displayDriver) {
		// TODO render method of Menu
	}

}
