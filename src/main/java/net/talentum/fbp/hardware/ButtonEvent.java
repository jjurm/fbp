package net.talentum.fbp.hardware;

/**
 * Class representing event related to buttons.
 * 
 * @author JJurM
 */
public class ButtonEvent {
	
	private Button button;
	
	private ButtonState buttonState;
	
	/**
	 * Default constructor
	 * 
	 * @param button
	 * @param buttonState
	 */
	public ButtonEvent(Button button, ButtonState buttonState) {
		this.button = button;
		this.buttonState = buttonState;
	}

	// === Getters ===
	
	public Button getButton() {
		return button;
	}

	public ButtonState getButtonState() {
		return buttonState;
	}
	
}
