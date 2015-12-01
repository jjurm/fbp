package net.talentum.fbp.hardware.button;


/**
 * Interface providing opportunity to receive {@link ButtonEvent}s.
 * 
 * @author JJurM
 */
public interface ButtonEventHandler {

	/**
	 * Method that is called whenever state of any observed button has changed.
	 * 
	 * @param event {@link ButtonEvent} with info about the event
	 */
	public void buttonStateChanged(ButtonEvent event);

}
