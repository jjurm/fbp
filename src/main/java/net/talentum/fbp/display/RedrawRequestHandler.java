package net.talentum.fbp.display;

import net.talentum.fbp.context.Context;

/**
 * This type of handler is usually registered to {@link Context} to be
 * notified about possibility of {@link Context}s to provide updated
 * content by next redraw.
 * 
 * @author JJurM
 */
public interface RedrawRequestHandler {

	/**
	 * {@link Context} uses this method for notification of possibility
	 * of updated content.
	 */
	public void request();

}
