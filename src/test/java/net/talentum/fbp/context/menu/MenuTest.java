package net.talentum.fbp.context.menu;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;

import net.talentum.fbp.context.UIManager;
import net.talentum.fbp.hardware.ButtonEvent;
import net.talentum.fbp.hardware.ButtonType;
import net.talentum.fbp.hardware.drivers.NullDisplayDriver;

/**
 * Tests basic logic of {@link Menu}.
 * 
 * @author JJurM
 */
public class MenuTest {

	private UIManager uiManager;
	private Menu menu1;
	private Menu menu2;

	@Before
	public void constructContextEnvironment() {
		uiManager = new UIManager(new NullDisplayDriver());
		menu1 = new Menu(uiManager, null);
		menu2 = new Menu(uiManager, menu1);

		menu1.menuItems.add(new TestMenuItem());
		menu1.menuItems.add(menu2);
		menu1.menuItems.add(new BackMenuItem());

		for (int i = 0; i < 4; i++) {
			menu2.menuItems.add(new TestMenuItem());
		}
		menu2.menuItems.add(new BackMenuItem());

		uiManager.switchContext(menu1);
	}

	private boolean called(MenuItem menuItem) {
		return ((TestMenuItem) menuItem).called;
	}

	@Test
	public void testObjectReferences() {
		assertNotNull(menu1.contextHolder);
		assertNotNull(menu1.contextHolder.getContext());
		assertNotNull(menu2.contextHolder);
		assertNotNull(menu2.contextHolder.getContext());
		assertNotNull(menu2.parent);
	}
	
	@Test
	public void testMenuBehavior1() {
		assertFalse(called(menu1.menuItems.get(0)));

		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.OK));
		assertTrue(called(menu1.menuItems.get(0)));
		assertThat(menu1.selected, is(0));

		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.RIGHT));
		assertThat(menu1.selected, is(1));
		assertSame(uiManager.getContext(), menu1);

		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.OK));
		assertSame(uiManager.getContext(), menu2);

		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.RIGHT));
		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.RIGHT));
		assertThat(menu2.selected, is(2));
		assertThat(menu2.scrollPosition, is(0));
		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.RIGHT));
		assertThat(menu2.selected, is(3));
		assertThat(menu2.scrollPosition, is(1));

		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.LEFT));
		assertThat(menu2.selected, is(2));
		assertThat(menu2.scrollPosition, is(1));
		assertFalse(called(menu2.menuItems.get(2)));
		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.OK));
		assertTrue(called(menu2.menuItems.get(2)));

		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.RIGHT));
		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.RIGHT));
		assertThat(menu2.scrollPosition, is(2));

		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.RIGHT));
		assertThat(menu2.selected, is(0));
		assertThat(menu2.scrollPosition, is(0));

		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.LEFT));
		assertThat(menu2.selected, is(4));
		assertThat(menu2.scrollPosition, is(2));

		assertSame(uiManager.getContext(), menu2);
		assertThat(menu2.menuItems.get(menu2.selected), IsInstanceOf.instanceOf(BackMenuItem.class));
		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.OK));
		assertSame(uiManager.getContext(), menu1);

		assertThat(menu1.selected, is(1));
		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.RIGHT));
		uiManager.buttonStateChanged(new ButtonEvent(ButtonType.OK));
		assertSame(uiManager.getContext(), menu1);
		assertThat(menu1.selected, is(2));
	}

}
