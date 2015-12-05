package net.talentum.fbp.context;

import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.button.ButtonState;

/**
 * Basic {@link Context} (specifically {@link ContextMenuItem}) whose purpose is
 * to inform user. Pressing {@code OK} button generally returns to the caller
 * menu.
 * 
 * @author JJurM
 */
public abstract class InfoContext extends ContextMenuItem {

	/**
	 * Default constructor.
	 * 
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
