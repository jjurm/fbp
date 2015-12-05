package net.talentum.fbp.context;

import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.hardware.drivers.DisplayDriver;

/**
 * Basic instantiable implementation of abstract class {@link InfoContext}. Can
 * only render static lines of text.
 * 
 * @author JJurM
 */
public class StaticInfoContext extends InfoContext {

	protected String[] lines;

	public StaticInfoContext(String name, String[] lines, ContextHolder contextHolder) {
		super(name, contextHolder);
	}

	@Override
	public void render(DisplaySection section, DisplayDriver display) {
		// only print name
		display.write(getName(), section);
	}

	@Override
	public void renderContext(DisplayDriver display) {
		// render title bar
		display.wline(0, getName());

		// TODO renderContext method of StaticInfoContext
	}

}
