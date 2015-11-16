package net.talentum.fbp.display;

/**
 * General interface of object that can hold and <i>switch</i>
 * {@link DisplayContext}.
 * 
 * @author JJurM
 */
public interface DisplayContextHolder {

	/**
	 * Return current (active) {@link DisplayContext}.
	 * 
	 * @return
	 */
	public DisplayContext getDisplayContext();

	/**
	 * Makes given {@link DisplayContext} active.
	 * 
	 * @param displayContext
	 */
	public void switchDisplayContext(DisplayContext displayContext);

}
