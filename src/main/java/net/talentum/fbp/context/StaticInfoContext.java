package net.talentum.fbp.context;

import net.talentum.fbp.hardware.drivers.DisplayDriver;
import net.talentum.fbp.system.Config;
import net.talentum.fbp.ui.AbstractStringContent;
import net.talentum.fbp.ui.StringContent;

/**
 * Basic instantiable implementation of abstract class {@link InfoContext}. Can
 * only render rows of text retrieved from {@link StringContent}. Although this
 * would not necessarily be static content (it may be generated dynamically),
 * this class behaves as if the content is static and does not provide any
 * method to receive notices about updated content.
 * 
 * <p>
 * This context does <em>not</em> respond to buttons {@code LEFT} and
 * {@code RIGHT}.
 * </p>
 * 
 * @author JJurM
 */
public class StaticInfoContext extends InfoContext {

	protected StringContent content;

	/**
	 * Default constructor
	 * 
	 * @param name
	 *            name that is used as headline and menu item label
	 * @param content
	 *            object providing content
	 * @param contextHolder
	 */
	public StaticInfoContext(String name, AbstractStringContent content, ContextHolder contextHolder) {
		super(name, contextHolder);
		this.content = content;
	}

	@Override
	protected String getLabel() {
		return getName();
	}

	@Override
	protected void enter() {
		// do nothing
	}

	@Override
	public void renderContext(DisplayDriver display) {
		// render title bar
		display.wline(0, getName());
		int offset = 1;

		// render remaining rows
		String row;
		for (int i = 0; i < Config.getDisplayRows() - 1; i++) {
			row = content.getRow(i);
			if (row == null) {
				display.clearRow(offset + i);
			} else {
				display.write(row, 0, offset + i);
			}
		}
	}

}
