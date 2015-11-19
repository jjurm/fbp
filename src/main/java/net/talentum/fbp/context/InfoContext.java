package net.talentum.fbp.context;

import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.ButtonState;

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
	public InfoContext(ContextHolder contextHolder) {
		super(contextHolder);
	}

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		if (event.getButtonState() == ButtonState.PRESSED) {
			switch (event.getButtonType()) {
			case OK:
				// return to the caller menu
				contextHolder.switchContext(callerMenu);
				break;
			default:
				// ignore other buttons
				break;
			}
		}
	}

}
