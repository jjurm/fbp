package net.talentum.fbp.context.menu;

import java.util.List;
import java.util.Map;

import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonState;
import net.talentum.fbp.hardware.drivers.DisplayDriver;
import net.talentum.fbp.util.ListWalker;

/**
 * Specific inline context designed to help create inline selections in a menu.
 * It is given list of {@link Option}s, over which should it cycle. Options are
 * mapped to strings, that are rendered.
 * <p>
 * Offers abstract method {@link #changed(Object)}, that is called whenever the
 * index has changed.
 * </p>
 * 
 * @author JJurM
 * @param <T>
 *            The type of values
 * @see #SelectionInlineContext(Map, boolean)
 */
public abstract class SelectionInlineContext<T> extends InlineContext {

	private ListWalker<Option<T>> walker;

	/**
	 * Default constructor, takes mapping {@code T -> String}.
	 * 
	 * @param options
	 *            list of options
	 * @param allowLoop
	 *            what should be done when user wants to cycle over first or
	 *            last element: to continue from the other side or to do nothing
	 * @see SelectionInlineContext
	 */
	public SelectionInlineContext(List<Option<T>> options, boolean allowLoop) {
		walker = new ListWalker<Option<T>>(options, allowLoop);
	}

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		if (event.getButtonState() == ButtonState.PRESSED) {
			switch (event.getButtonType()) {
			case OK:
				returnToMenu();
				break;
			case LEFT:
				walker.increment();
				changed(selected());
				dispatchRedrawRequest();
				break;
			case RIGHT:
				walker.decrement();
				changed(selected());
				dispatchRedrawRequest();
				break;
			}
		}
	}

	/**
	 * Get {@link RangeIndex} of currently selected item. Child classes can
	 * modify the index, but call of {@link #dispatchRedrawRequest()} should not
	 * be forgotten.
	 * 
	 * @return
	 */
	/*
	 * protected RangeIndex index() { return index; }
	 */

	/**
	 * Returns currently selected value.
	 * 
	 * @return
	 */
	protected T selected() {
		return walker.getSelected().value;
	}

	@Override
	protected final void called() {
		// do nothing
	}

	/**
	 * This method will be called upon every change of index.
	 *
	 * @param value
	 *            New selected value
	 */
	protected abstract void changed(T value);

	/**
	 * Label that should be display as a description of what is being selected.
	 * 
	 * @return
	 */
	protected abstract String getLabel();

	@Override
	public void render(DisplaySection section, DisplayDriver display) {
		String lbl = getLabel() + ": ";
		display.write(lbl, 2, section.getRow());
		display.write(walker.getSelected().name,
				new DisplaySection(section.getRow(), 2 + lbl.length(), section.getEnd()));
	}

	/**
	 * Object that has associated {@code T} with String. Represent one option in
	 * a selection menu.
	 * 
	 * @author JJurM
	 * @param <T>
	 */
	public static class Option<T> {

		private T value;
		private String name;

		public Option(T value, String name) {
			this.value = value;
			this.name = name;
		}

	}

}
