package net.talentum.fbp.context;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.ButtonType;
import net.talentum.fbp.hardware.drivers.NullDisplayDriver;

/**
 * Tests basic logic of {@link UIManager}.
 * 
 * @author JJurM
 */
public class UIManagerTest {

	private UIManager uiManager;
	private TestContext context;

	@Before
	public void constructContextEnvironment() {
		uiManager = new UIManager(new NullDisplayDriver());
		context = new TestContext(uiManager);
		uiManager.switchContext(context);
	}

	@Test
	public void testSwitchingContext1() {
		assertSame(context, uiManager.getContext());
		TestContext context2 = new TestContext(uiManager);
		uiManager.switchContext(context2);
		assertSame(context2, uiManager.getContext());
	}

	@Test
	public void testButtonEvent1() {
		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.RIGHT));
		assertTrue(context.gotButtonEvent);
	}

	@Test
	public void testButtonEvent2() {
		uiManager.switchContext(new TestContext(uiManager));
		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.LEFT));
		assertFalse(context.gotButtonEvent);
	}

	@Test
	public void testRedrawRequest1() {
		assertTrue(context.calledRenderContext);
	}

	@Test
	public void testRedrawRequest2() {
		context.calledRenderContext = false;
		context.dispatchRedrawRequest();
		assertTrue(context.calledRenderContext);
	}

	@Test
	public void testRedrawRequest3() {
		context.calledRenderContext = false;
		uiManager.switchContext(new TestContext(uiManager));
		context.dispatchRedrawRequest();
		assertFalse(context.calledRenderContext);
	}

	@After
	public void destroyContextEnvironment() {
		uiManager = null;
		context = null;
	}

}
