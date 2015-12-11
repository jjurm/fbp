package net.talentum.fbp.context;

import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonState;

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
				contextHolder.switchContext(callerMenu);
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

}
