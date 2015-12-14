package net.talentum.fbp.context.menu;

import java.util.List;

import net.talentum.fbp.context.ContextHolder;
import net.talentum.fbp.context.HomeScreen;

/**
 * Main menu is different from general {@link BasicMenu} class. It always
 * returns to the {@link HomeScreen} and therefore does not need to catch the
 * caller menu reference. It follows that {@link HomeScreen} will explicitly
 * call {@link #enter()} method just before switching active context to menu.
 * 
 * @author JJurM
 */
public class MainMenu extends BasicMenu {

	private HomeScreen homeScreen;

	/**
	 * Constructor should be not used directly, see {@link BasicMenu.Builder}.
	 * 
	 * @param homeScreen
	 *            reference to the {@link HomeScreen}
	 * @param name
	 * @param contextHolder
	 * @param menuItems
	 * @see BasicMenu.Builder
	 */
	protected MainMenu(HomeScreen homeScreen, String name, ContextHolder contextHolder, List<MenuItem> menuItems) {
		super(name, contextHolder, menuItems);
		this.homeScreen = homeScreen;
	}

	/**
	 * Return general constructor of {@link MainMenu}, intended to be used in
	 * {@link BasicMenu.Builder}.
	 * 
	 * @param homeScreen
	 * @return
	 */
	public static BasicMenu.GeneralConstructor<MainMenu> getConstructor(HomeScreen homeScreen) {
		return (name, contextHolder, menuItems) -> new MainMenu(homeScreen, name, contextHolder, menuItems);
	}

	/**
	 * Getter for stored instance of {@link HomeScreen}, that is once set in
	 * constructor.
	 * 
	 * @return
	 */
	protected HomeScreen getHomeScreen() {
		return homeScreen;
	}

	@Override
	public void enter() {
		// make the method public
		super.enter();
	}

}
