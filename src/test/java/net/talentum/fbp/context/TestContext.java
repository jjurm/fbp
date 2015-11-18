package net.talentum.fbp.context;

import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * Class extending {@link Context}, used only for test purposes.
 * 
 * @author JJurM
 */
public class TestContext extends Context {

	public TestContext(ContextHolder contextHolder) {
		super(contextHolder);
	}

	boolean gotButtonEvent = false;
	boolean calledRenderContext = false;
	
	@Override
	public void buttonStateChanged(ButtonEvent event) {
		gotButtonEvent = true;
	}

	@Override
	public void renderContext(DisplayDriver displayDriver) {
		calledRenderContext = true;
	}
	
	@Override
	public void populateRedrawRequest() {
		// just to make this method public
		super.populateRedrawRequest();
	}
	
}
