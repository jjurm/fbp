package net.talentum.fbp.display;

/**
 * This type of handler is usually registered to {@link DisplayContextRenderer} to be
 * notified about possibility of {@link DisplayContextRenderer}s to provide updated
 * content by next redraw.
 * 
 * @author JJurM
 */
public interface RedrawRequestHandler {

	/**
	 * {@link DisplayContextRenderer} uses this method for notification of possibility
	 * of updated content.
	 */
	public void request();

}
