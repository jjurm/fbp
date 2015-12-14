package net.talentum.fbp.context.menu;

import java.util.ArrayList;
import java.util.List;

import net.talentum.fbp.context.ContextHolder;
import net.talentum.fbp.ui.UIManager;

/**
 * Basic implementation of {@link AbstractMenu}, which relies on a given list of
 * {@link MenuItem}s, that are then fed to {@link AbstractMenu} using the
 * {@link #getMenuItems()} method.
 * 
 * @author JJurM
 */
public class BasicMenu extends AbstractMenu {

	protected List<MenuItem> menuItems;

	/**
	 * Constructor should be not used directly. See {@link BasicMenu.Builder}.
	 * 
	 * @param name
	 * @param contextHolder
	 * @param menuItems
	 * @see BasicMenu.Builder
	 */
	protected BasicMenu(String name, ContextHolder contextHolder, List<MenuItem> menuItems) {
		super(name, contextHolder);
		this.menuItems = menuItems;
	}

	/**
	 * Adds given item to the list of menu items. Returns reference to this
	 * object, so the creation can be chained:
	 * 
	 * <pre>
	 * new Menu(name, context)
	 * 	.addMenuItem(new MenuItem(...))
	 * 	.addMenuItem(new MenuItem(...))
	 * </pre>
	 * 
	 * @param menuItem
	 *            {@link MenuItem} to add
	 * @return reference to this {@link BasicMenu}
	 */
	public BasicMenu addMenuItem(MenuItem menuItem) {
		menuItems.add(menuItem);
		return this;
	}

	@Override
	protected List<MenuItem> getMenuItems() {
		return menuItems;
	}

	/**
	 * Functional interface that constructs menu of any subclass of
	 * {@link BasicMenu}, given necessary arguments {@code name},
	 * {@code contextHolder} and {@code menuItems}.
	 * 
	 * @author JJurM
	 */
	@FunctionalInterface
	public static interface GeneralConstructor<T extends BasicMenu> {

		/**
		 * Constructs menu.
		 * 
		 * @param name
		 * @param contextHolder
		 * @param menuItems
		 * @return
		 * @see GeneralConstructor
		 */
		public T buildMenu(String name, ContextHolder contextHolder, List<MenuItem> menuItems);

	}

	/**
	 * Class for constructing {@link BasicMenu}. The only possible way to create
	 * a {@link BasicMenu} is using the builder:
	 * 
	 * <pre>
	 * BasicMenu.Builder.create(name, contextHolder)
	 * 	.item(...)
	 * 	.item(...)
	 * 	.build()
	 * </pre>
	 * <p>
	 * Or, using custom constructor:
	 * </p>
	 * 
	 * <pre>
	 * BasicMenu.Builder.create(customConstructor, name, contextHolder)...
	 * </pre>
	 * 
	 * <p>
	 * where {@code customConstructor} is functional interface (
	 * {@link GeneralConstructor} ). Few examples of its implementations
	 * (according to an imaginary implementation of mentioned methods):
	 * </p>
	 * <ul>
	 * <li>
	 * {@code (name, contentHolder, menuItems) -> new CustomMenu(name, contentHolder, menuItems)}
	 * </li>
	 * <li>{@code CustomMenu::new}</li>
	 * <li>{@code CustomMenu.getConstructor(...)}</li>
	 * </ul>
	 * 
	 * <p>
	 * For real examples and further explanation, see source code of:
	 * </p>
	 * <ul type="circle">
	 * <li>{@link UIManager#constructMenuHierarchy()}</li>
	 * <li>{@link MainMenu#getConstructor(HomeScreen)}</li>
	 * </ul>
	 * 
	 * @param <T>
	 *            Type of menu that will be constructed by this builder;
	 *            suitable is any subclass of {@link BasicMenu}.
	 * @author JJurM
	 */
	public static class Builder<T extends BasicMenu> {

		private GeneralConstructor<T> constructor;
		private String name;
		private ContextHolder contextHolder;

		private List<MenuItem> menuItems = new ArrayList<MenuItem>();

		/**
		 * Hidden internal constructor, use
		 * {@link #create(String, ContextHolder)} method instead.
		 * 
		 * @param constructor
		 * @param name
		 * @param contextHolder
		 */
		private Builder(GeneralConstructor<T> constructor, String name, ContextHolder contextHolder) {
			this.constructor = constructor;
			this.name = name;
			this.contextHolder = contextHolder;
		}

		/**
		 * Creates a new builder.
		 * 
		 * @param <T>
		 *            Type of menu that will be constructed; suitable is any
		 *            subclass of {@link BasicMenu}.
		 * @param constructor
		 *            Custom lambda function returning instance of any subclass
		 *            of {@link BasicMenu}, given arguments.
		 * @param name
		 * @param contextHolder
		 * @return
		 */
		public static <T extends BasicMenu> Builder<T> create(GeneralConstructor<T> constructor, String name,
				ContextHolder contextHolder) {
			return new Builder<T>(constructor, name, contextHolder);
		}

		/**
		 * Creates a new builder.
		 * <p>
		 * This is alternative method of creating the builder, omitting argument
		 * {@code constructor}.
		 * </p>
		 * 
		 * @param name
		 * @param contextHolder
		 * @return
		 * @see #create(String, ContextHolder, GeneralConstructor)
		 */
		public static Builder<BasicMenu> create(String name, ContextHolder contextHolder) {
			return create((n, ch, mi) -> new BasicMenu(n, ch, mi), name, contextHolder);
		}

		/**
		 * Adds {@link MenuItem}.
		 * 
		 * @param item
		 *            {@link MenuItem} to add.
		 * @return reference to the builder itself
		 */
		public Builder<T> item(MenuItem item) {
			menuItems.add(item);
			return this;
		}

		/**
		 * Finishes building the menu and returns it.
		 * 
		 * @return created instance the menu
		 */
		public T build() {
			return constructor.buildMenu(name, contextHolder, menuItems);
		}

	}

}
