package net.talentum.fbp.context;

import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.hardware.button.ButtonEvent;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * Class extending {@link Context}, used only for test purposes.
 * 
 * @author JJurM
 */
public class TestContext extends InfoContext {

	public TestContext(ContextHolder contextHolder) {
		super(contextHolder);
	}

	public boolean gotButtonEvent = false;
	public boolean calledRenderContext = false;

	@Override
	public void buttonStateChanged(ButtonEvent event) {
		gotButtonEvent = true;
		super.buttonStateChanged(event);
	}

	@Override
	public void renderContext(DisplayDriver displayDriver) {
		calledRenderContext = true;
	}

	@Override
	public void dispatchRedrawRequest() {
		// just to make this method public
		super.dispatchRedrawRequest();
	}

	@Override
	public void render(DisplaySection displaySection, DisplayDriver displayDriver) {
		// do not render
	}

}
