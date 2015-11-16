package net.talentum.fbp.display;

/**
 * This type of handler is usually registered to {@link DisplayContext} to be
 * notified about possibility of {@link DisplayContext}s to provide updated
 * content by next redraw.
 * 
 * @author JJurM
 */
public interface RedrawRequestHandler {

	/**
	 * {@link DisplayContext} uses this method for notification of possibility
	 * of updated content.
	 */
	public void request();

}
