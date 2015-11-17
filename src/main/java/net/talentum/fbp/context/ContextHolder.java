package net.talentum.fbp.context;

/**
 * General interface of object that can hold and <i>switch</i>
 * {@link Context}.
 * 
 * @author JJurM
 */
public interface ContextHolder {

	/**
	 * Return current (active) {@link Context}.
	 * 
	 * @return active context
	 */
	public Context getContext();

	/**
	 * Makes given {@link Context} active.
	 * 
	 * @param context new context
	 */
	public void switchContext(Context context);

}
