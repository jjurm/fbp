package net.talentum.fbp.system;

import java.util.concurrent.ExecutionException;

/**
 * Exception thrown upon a problem with program startup.
 * 
 * @author JJurM
 */
public class StartupException extends ExecutionException {
	private static final long serialVersionUID = 1L;

	public StartupException(Throwable cause) {
		super(cause);
	}
	
	public StartupException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
