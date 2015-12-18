package net.talentum.fbp.context;

import java.util.List;

import net.talentum.fbp.display.DisplayRenderer;
import net.talentum.fbp.hardware.drivers.DisplayDriver;
import net.talentum.fbp.util.ListWalker;

/**
 * Context for browsing individual pages. It extends {@link InfoContext} and
 * therefore offers no further action than to move left/right through the pages.
 * Pressing {@code OK} will return to the parent menu. Each page is represented
 * by a {@link DisplayRenderer}. List of pages is refreshed upon enter to the
 * browser.
 * 
 * @author JJurM
 */
public abstract class PageBrowser extends InfoContext {

	protected ListWalker<DisplayRenderer> walker;

	/**
	 * Default constructor.
	 * 
	 * @param name
	 * @param contextHolder
	 */
	public PageBrowser(String name, ContextHolder contextHolder) {
		super(name, contextHolder);
	}

	@Override
	protected void enter() {
		List<DisplayRenderer> pages = getPages();
		walker = new ListWalker<DisplayRenderer>(pages, false);
	}

	/**
	 * Returns list of pages represented by {@link DisplayRenderer}s.
	 * 
	 * @return
	 */
	protected abstract List<DisplayRenderer> getPages();

	/**
	 * Returns {@link DisplayRenderer} that should be used in case the list
	 * contains no items.
	 * 
	 * @return
	 */
	protected abstract DisplayRenderer getDefaultRenderer();

	@Override
	protected void pressedLeft() {
		// to the previous page
		walker.decrement();
		dispatchRedrawRequest();
	}

	@Override
	protected void pressedRight() {
		// to the next page
		walker.increment();
		dispatchRedrawRequest();
	}

	private DisplayRenderer getRenderer() {
		if (walker.hasItems())
			return walker.getSelected();
		else
			return getDefaultRenderer();
	}

	@Override
	public void renderContext(DisplayDriver display) {
		getRenderer().renderContext(display);
	}

}
