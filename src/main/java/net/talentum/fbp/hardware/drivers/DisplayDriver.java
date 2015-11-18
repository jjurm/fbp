package net.talentum.fbp.hardware.drivers;

import java.util.Arrays;
import java.util.List;

import net.talentum.fbp.hardware.Pins;

import com.jjurm.libs.charlcd.CharLCD;
import com.jjurm.libs.charlcd.CharLCD.BLINKING;
import com.jjurm.libs.charlcd.CharLCD.CURSOR;
import com.jjurm.libs.charlcd.CharLCD.CURSORMOVEMENT;
import com.jjurm.libs.charlcd.CharLCD.DISPLAY;
import com.jjurm.libs.charlcd.CharLCD.DOTSIZE;
import com.pi4j.io.gpio.GpioController;

public class DisplayDriver implements Driver {

	private CharLCD lcd;
	private DisplayCharacter[] customChars = new DisplayCharacter[8];
	private List<Byte> emptyChars = Arrays.asList(new Byte[] { 0, 1, 2, 3, 4, 5, 6, 7 });

	public DisplayDriver(GpioController gpio, boolean backlightOn) {
		setup(gpio, backlightOn);

	}

	public DisplayDriver(GpioController gpio) {
		this(gpio, false);
	}

	public void home() {
		lcd.home();
	}

	public void moveCursor(int col, int row) {
		lcd.moveCursor(col, row);
	}

	public void writeString(String text) {
		lcd.write(text);
	}

	public void writeInt(int i) {
		lcd.write(Integer.toString(i));
	}

	public void writeSpecialChar(DisplayCharacter c) {
		for (int i = 0; i < customChars.length; i++) {
			if (customChars[i].equals(c)) {
				lcd.data((byte) i);
				return;
			}
		}

		if (emptyChars.size() != 0) {
			byte b = emptyChars.remove(0);
			lcd.createChar(b, c.getBytes());
			lcd.data(b);
		} else {
			throw new RuntimeException("Not enough special characters!");
		}
	}

	public void writeString(String text, int col, int row) {
		lcd.moveCursor(col, row);
		this.writeString(text);
	}

	public void writeInt(int i, int col, int row) {
		lcd.moveCursor(col, row);
		this.writeInt(i);
	}

	public void writeSpecialChar(DisplayCharacter c, int col, int row) {
		lcd.moveCursor(col, row);
		this.writeSpecialChar(c);
	}

	public void clearRow(int row) {
		lcd.wline(row, "");
	}

	public void clear() {
		lcd.clear();
	}

	public void setBacklight(boolean on) {
		lcd.setBacklight(on);
	}

	public void setBlinking(BLINKING b) {
		lcd.setBlinking(b);
	}

	public void setCursorMovement(CURSORMOVEMENT c) {
		lcd.setCursorMovement(c);
	}

	public void setCursor(CURSOR c) {
		lcd.setCursor(c);
	}

	public void setup(GpioController gpio, boolean backlightOn) {
		lcd = new CharLCD(Pins.PIN_LCD_E, Pins.PIN_LCD_E, Pins.PINS_LCD_DB,
				Pins.PIN_LCD_BCKLGHT, 16, 4, DOTSIZE.DOTS_5x7);
		lcd.setDisplay(DISPLAY.ON);
		lcd.setBacklight(backlightOn);
	}

	@Override
	public void setup(GpioController gpio) {
		setup(gpio, false);
	}

	@Override
	public void close() {
		lcd.clear();
		lcd.setDisplay(DISPLAY.OFF);
	}
}
