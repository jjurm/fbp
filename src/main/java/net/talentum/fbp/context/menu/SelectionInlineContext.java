package net.talentum.fbp.context.menu;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonState;
import net.talentum.fbp.hardware.drivers.DisplayDriver;
import net.talentum.fbp.util.RangeIndex;

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

	/**
	 * Unmodifiable list of options
	 */
	protected final List<Option<T>> options;
	protected final boolean allowLoop;
	private RangeIndex index;

	/**
	 * Default constructor, takes mapping {@code T -> String}.
	 * 
	 * @param options
	 * @param allowLoop
	 *            what should be done when user wants to cycle over first or
	 *            last element: to continue from the other side or to do nothing
	 * @see SelectionInlineContext
	 */
	public SelectionInlineContext(List<Option<T>> options, boolean allowLoop) {
		this.options = Collections.unmodifiableList(options);
		this.allowLoop = allowLoop;
		this.index = new RangeIndex(options.size() - 1);
	}

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		if (event.getButtonState() == ButtonState.PRESSED) {
			switch (event.getButtonType()) {
			case OK:
				returnToMenu();
				break;
			case LEFT:
				if (allowLoop)
					index.decrement();
				else
					index.decrementIfNotMin();
				dispatchRedrawRequest();
				break;
			case RIGHT:
				if (allowLoop)
					index.increment();
				else
					index.incrementIfNotMax();
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
	protected RangeIndex index() {
		return index;
	}

	/**
	 * Returns currently selected value.
	 * 
	 * @return
	 */
	protected T selected() {
		return options.get(index.get()).value;
	}

	@Override
	protected final void called() {
		// do nothing
	}

	/**
	 * This method will be called upon every change of index. Must be
	 * overridden.
	 *
	 * @param value
	 *            New selected value
	 */
	protected abstract void changed(T value);

	@Override
	public void render(DisplaySection section, DisplayDriver display) {
		String lbl = getLabel() + ": ";
		display.write(lbl, 2, section.getRow());
		display.write(options.get(index.get()).name,
				new DisplaySection(section.getRow(), 2 + lbl.length(), section.getEnd()));
	}

	/**
	 * Label that should be display as a description of what is being selected.
	 * 
	 * @return
	 */
	protected abstract String getLabel();

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
