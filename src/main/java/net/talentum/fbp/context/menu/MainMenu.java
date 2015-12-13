package net.talentum.fbp.context.menu;

import net.talentum.fbp.context.ContextHolder;
import net.talentum.fbp.context.HomeScreen;

/**
 * Main menu is different from general {@link Menu} class. It always returns to
 * the {@link HomeScreen} and therefore does not need to catch the caller menu
 * reference. It follows that {@link HomeScreen}..
 * 
 * @author JJurM
 */
public class MainMenu extends Menu {

	private HomeScreen homeScreen;

	/**
	 * Default constructor.
	 * 
	 * @param homeScreen
	 *            the reference to {@link HomeScreen}
	 * @param contextHolder
	 */
	public MainMenu(HomeScreen homeScreen, ContextHolder contextHolder) {
		super("Main menu", contextHolder);
		this.homeScreen = homeScreen;
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
	
	@Override
	public MainMenu addMenuItem(MenuItem menuItem) {
		super.addMenuItem(menuItem);
		return this;
	}

}
