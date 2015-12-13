package net.talentum.fbp.hardware.drivers;

import com.jjurm.libs.charlcd.CharLCD.BLINKING;
import com.jjurm.libs.charlcd.CharLCD.CURSOR;
import com.jjurm.libs.charlcd.CharLCD.CURSORMOVEMENT;
import com.pi4j.io.gpio.GpioController;

import net.talentum.fbp.display.DisplaySection;

/**
 * A {@link DisplayDriver} that can do nothing, used only for test purposes.
 * 
 * @author JJurM
 */
public class NullDisplayDriver extends DisplayDriver {

	public NullDisplayDriver() {
		super(null);
	}

	@Override
	public void home() {
	}

	@Override
	public void moveCursor(int col, int row) {
	}

	@Override
	public void write(String text) {
	}

	@Override
	public void write(int i) {
	}

	@Override
	public void writeSpecialChar(DisplayCharacter c) {
	}

	@Override
	public void removeSpecialChars() {
	}

	@Override
	public void removeSpecialChar(DisplayCharacter c) {
	}

	@Override
	public void write(String text, int col, int row) {
	}

	@Override
	public void write(int i, int col, int row) {
	}

	@Override
	public void write(String string, DisplaySection section) {
	}

	@Override
	public void writeSpecialChar(DisplayCharacter c, int col, int row) {
	}

	@Override
	public void wline(int row, String string) {
	}

	@Override
	public void clearRow(int row) {
	}

	@Override
	public void clear() {
	}

	@Override
	public void setBacklight(boolean on) {
	}

	@Override
	public void setBlinking(BLINKING b) {
	}

	@Override
	public void setCursorMovement(CURSORMOVEMENT c) {
	}

	@Override
	public void setCursor(CURSOR c) {
	}

	@Override
	public void setup(GpioController gpio, boolean backlightOn) {
	}

	@Override
	public void setup(GpioController gpio) {
	}

	@Override
	public void close() {
	}

}
