package net.talentum.fbp.hardware;

/**
 * Class representing event related to buttons.
 * 
 * @author JJurM
 */
public class ButtonEvent {

	private ButtonType buttonType;

	private ButtonState buttonState;

	/**
	 * Default constructor.
	 * 
	 * @param button
	 * @param buttonState
	 */
	public ButtonEvent(ButtonType button, ButtonState buttonState) {
		this.buttonType = button;
		this.buttonState = buttonState;
	}

	/**
	 * Secondary constructor where {@link ButtonState} parameter has default
	 * value {@code PRESSED}.
	 * 
	 * @param button
	 */
	public ButtonEvent(ButtonType button) {
		this(button, ButtonState.PRESSED);
	}

	// === Getters ===

	public ButtonType getButtonType() {
		return buttonType;
	}

	public ButtonState getButtonState() {
		return buttonState;
	}

}
