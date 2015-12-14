package net.talentum.fbp.context.menu;

import static net.talentum.fbp.system.Config.getDisplayColumns;
import static net.talentum.fbp.system.Config.getDisplayRows;

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
import net.talentum.fbp.util.RangeIndex;

/**
 * Class on abstract layer bringing general base for child menu classes.
 * Graphical menu usually displayed on a display.
 * 
 * @author JJurM
 */
public abstract class AbstractMenu extends ContextMenuItem implements RedrawRequestHandler {
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Active {@link InlineContext} (if any), or {@code null}.
	 */
	protected InlineContext activeInlineContext;

	/**
	 * List of menu items, that is retrieved when entering the menu (using
	 * {@link #getMenuItems()}).
	 */
	private List<MenuItem> menuItems;

	/**
	 * Indicates which item is selected, based on its index. {@link RangeIndex}
	 * for this field is created in {@link #enter()}.
	 */
	protected RangeIndex selected;

	/**
	 * Defines the lists index of first displayed {@link MenuItem}
	 */
	protected int scrollPosition = 0;

	/**
	 * Default constructor.
	 * 
	 * @param name
	 * @param contextHolder
	 */
	public AbstractMenu(String name, ContextHolder contextHolder) {
		super(name, contextHolder);
	}

	/**
	 * Returns list of menu items in the same order in which they are supposed
	 * to be shown. Must be overridden by children.
	 * <p>
	 * This should not involve long operation as the method is called on every
	 * enter to the menu.
	 * </p>
	 * 
	 * @return
	 */
	protected abstract List<MenuItem> getMenuItems();

	/**
	 * This method will move scroll position if necessary to assert that the
	 * selected {@link MenuItem} will be shown.
	 */
	protected void adjustScrollPosition() {
		if (scrollPosition > selected.get()) {
			scrollPosition = selected.get();
		} else if (selected.get() - 2 > scrollPosition) {
			scrollPosition = selected.get() - 2;
		}
	}

	@Override
	protected void enter() {
		// reset selected item index
		menuItems = getMenuItems();
		selected = new RangeIndex(menuItems.size() - 1);
		adjustScrollPosition();
	}

	@Override
	protected void back() {
		// to make it visible from 'menu' package
		super.back();
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
	public void buttonStateChanged(ButtonEvent event) {
		if (activeInlineContext != null) {
			// delegate event processing to the InlineContext
			activeInlineContext.buttonStateChanged(event);
		} else if (event.getButtonState() == ButtonState.PRESSED) {
			switch (event.getButtonType()) {
			case LEFT:
				// move selection to the previous item
				selected.decrement();
				adjustScrollPosition();
				break;
			case RIGHT:
				// move selection to the next item
				selected.increment();
				adjustScrollPosition();
				break;
			case OK:
				// call selected item
				menuItems.get(selected.get()).call(this);
				break;
			}
		}
	}

	@Override
	public void render(DisplaySection section, DisplayDriver display) {
		display.write(getName(), section);
	}

	@Override
	public void handleRedrawRequest() {
		// this can be called from underlying InlineContexts
		dispatchRedrawRequest();
	}

	@Override
	public void renderContext(DisplayDriver display) {
		// render title bar
		display.wline(0, getName());

		// render remaining rows
		MenuItem item;
		for (int i = 0; i < getDisplayRows() - 1; i++) {
			item = menuItems.get(scrollPosition + i);
			if (selected.get() == scrollPosition + i) {
				display.write(0xBC, 0, i);
			}
			item.render(new DisplaySection(1 + i, 2, getDisplayColumns()), display);
		}
	}

}
