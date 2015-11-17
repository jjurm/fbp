package net.talentum.fbp.display;

/**
 * General interface of object that can hold and <i>switch</i>
 * {@link DisplayContextRenderer}.
 * 
 * @author JJurM
 */
public interface DisplayContextRendererHolder {

	/**
	 * Return current (active) {@link DisplayContextRenderer}.
	 * 
	 * @return
	 */
	public DisplayContextRenderer getDisplayContextRenderer();

	/**
	 * Makes given {@link DisplayContextRenderer} active.
	 * 
	 * @param displayContextRenderer
	 */
	public void switchDisplayContextRenderer(DisplayContextRenderer displayContextRenderer);

}
