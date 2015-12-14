package net.talentum.fbp.context.menu;

import net.talentum.fbp.display.DisplaySection;
import net.talentum.fbp.hardware.drivers.DisplayDriver;
import net.talentum.fbp.system.Main;

public class ShutdownMenuItem implements MenuItem {

	@Override
	public void render(DisplaySection section, DisplayDriver display) {
		display.write("Shutdown", section);
	}

	@Override
	public void call(AbstractMenu menu) {
		Main.shutdown();
	}

}
