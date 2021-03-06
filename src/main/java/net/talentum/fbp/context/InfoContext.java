package net.talentum.fbp.context;

import net.talentum.fbp.context.menu.MenuItem;
import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonState;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * Basic {@link Context} (specifically {@link ContextMenuItem}) whose purpose is
 * to inform user.
 * 
 * <p>
 * Main characteristic of this context is that it shall
 * <em>not offer any further action</em> to the user except for returning to the
 * caller menu. Pressing {@code OK} button triggers this return. The remaining
 * buttons {@code LEFT} and {@code RIGHT} can be observer and responded to, by
 * overriding methods {@link #pressedLeft()} and {@link #pressedRight()}, but
 * the child class shouldn't perform operation other than switching/scrolling
 * the content.
 * </p>
 * 
 * @author JJurM
 */
public abstract class InfoContext extends ContextMenuItem {

	/**
	 * Default constructor.
	 * 
	 * @param name
	 *            name that is used as headline and menu item label
	 * @param contextHolder
	 */
	public InfoContext(String name, ContextHolder contextHolder) {
		super(name, contextHolder);
	}

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		if (event.getButtonState() == ButtonState.PRESSED) {
			switch (event.getButtonType()) {
			case OK:
				// return to the caller menu
				back();
				break;
			case LEFT:
				pressedLeft();
			case RIGHT:
				pressedRight();
			default:
				// ignore other buttons
				break;
			}
		}
	}

	/**
	 * Returns what should be displayed when rendering as {@link MenuItem}.
	 */
	protected abstract String getLabel();

	/**
	 * Called when {@code LEFT} button is pressed. Children can override this
	 * method to specify the body.
	 */
	protected void pressedLeft() {
	};

	/**
	 * Called when {@code RIGHT} button is pressed. Children can override this
	 * method to specify the body.
	 */
	protected void pressedRight() {
	};

	@Override
	public void render(DisplaySection section, DisplayDriver display) {
		display.write(getLabel(), section);
	}

}
