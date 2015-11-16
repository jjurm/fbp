package net.talentum.fbp.hardware;

/**
 * Class representing event related to buttons.
 * 
 * @author JJurM
 */
public class ButtonEvent {
	
	private ButtonType button;
	
	private ButtonState buttonState;
	
	/**
	 * Default constructor
	 * 
	 * @param button
	 * @param buttonState
	 */
	public ButtonEvent(ButtonType button, ButtonState buttonState) {
		this.button = button;
		this.buttonState = buttonState;
	}

	// === Getters ===
	
	public ButtonType getButton() {
		return button;
	}

	public ButtonState getButtonState() {
		return buttonState;
	}
	
}
