package net.talentum.fbp.context.menu;

import static net.talentum.fbp.system.Config.getDisplayColumns;
import static net.talentum.fbp.system.Config.getDisplayRows;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.talentum.fbp.context.Context;
import net.talentum.fbp.context.ContextHolder;
import net.talentum.fbp.context.ContextMenuItem;
import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.display.RedrawRequestHandler;
import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonState;
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

	/**
	 * Defines the lists index of first displayed {@link MenuItem}
	 */
	protected int scrollPosition = 0;
	
	protected InlineContext activeInlineContext;

	/**
	 * Default constructor.
	 * 
	 * @param contextHolder
	 *            holder that holds this context
	 */
	public Menu(String name, ContextHolder contextHolder) {
		super(name, contextHolder);
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
	protected void enter() {
		// reset selected item index
		selected = 0;
		adjustScrollPosition();
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

	/**
	 * Adds given item to the list of menu items. Returns reference to this
	 * object, so the creation can be chained:
	 * 
	 * <pre>
	 * new Menu(name, context)
	 * 	.addMenuItem(new MenuItem(...))
	 * 	.addMenuItem(new MenuItem(...))
	 * </pre>
	 * 
	 * @param menuItem
	 *            {@link MenuItem} to add
	 * @return reference to this {@link Menu}
	 */
	public Menu addMenuItem(MenuItem menuItem) {
		menuItems.add(menuItem);
		return this;
	}
	
	/**
	 * Switch context to the parent menu, if any. Intended to be called from {@link BackMenuItem}.
	 */
	void back() {
		// check if the menu has a parent
		if (getCallerMenu() != null)
			// switch context to the parent menu
			contextHolder.switchContext(getCallerMenu());
	}

	@Override
	public void request() {
		// this can be called from underlying InlineContexts
		dispatchRedrawRequest();
	}

	@Override
	public void render(DisplaySection section, DisplayDriver display) {
		display.write(getName(), section);
	}
	
	@Override
	public void renderContext(DisplayDriver display) {
		// render title bar
		display.wline(0, getName());

		// render remaining rows
		MenuItem item;
		for (int i = 0; i < getDisplayRows() - 1; i++) {
			item = menuItems.get(scrollPosition + i);
			if (selected == scrollPosition + i) {
				display.write(0xBC, 0, i);
			}
			item.render(new DisplaySection(1 + i, 2, getDisplayColumns()), display);
		}
	}

}
