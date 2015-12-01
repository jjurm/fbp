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

/**
 * {@link DisplayDriver} is used to controll the lcd display. It implements methods to create and use
 * special characters, write to specific coordinates(rows and columns). 
 * 
 * DisplayDriver implements the setup() and the close() methods and works as a {@link Driver}.
 * @author padr31
 * @see Driver
 */
public class DisplayDriver implements Driver {
	
	private DOTSIZE dotsize;
	
	private int cols, rows;
	
	private CharLCD lcd;
	
	/**
	 * Array of {@link DisplayCharacter} used for holding already created special characters.
	 */
	private DisplayCharacter[] specialChars = new DisplayCharacter[8];
	
	/**
	 * List that holds the indexes of the displays registries that are available to use for special 
	 * characters. 
	 */
	private List<Byte> availableChars = Arrays.asList(new Byte[] { 0, 1, 2, 3, 4, 5, 6, 7 });
	
	public DisplayDriver(GpioController gpio, boolean backlightOn) {
		this.dotsize = DOTSIZE.DOTS_5x7;
		this.cols = 16;
		this.rows = 4;

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

	/**
	 * This method writes the desired special character {@link DisplayCharacter} on the display at the
	 * cursor's location. It first checks whether the special character was already created. If it wasn't, it
	 * creates a new special character the display's registry has place for a new special character.  
	 * @param c {@link DisplayCharacter}
	 * @throws RuntimeException In case of inavailability of registry space for a new character.
	 * @see DisplayCharacter
	 */
	public void writeSpecialChar(DisplayCharacter c) {
		for (int i = 0; i < specialChars.length; i++) {
			if (specialChars[i].equals(c)) {
				lcd.data((byte) i);
				return;
			}
		}

		if (availableChars.size() != 0) {
			byte b = availableChars.remove(0);
			lcd.createChar(b, c.getBytes());
			specialChars[b] = c;
			lcd.data(b);
		} else {
			throw new RuntimeException("Not enough special characters!");
		}
	}
	
	public void removeSpecialChars(){
		availableChars = Arrays.asList(new Byte[] { 0, 1, 2, 3, 4, 5, 6, 7 });
	}
	
	public void removeSpecialChar(DisplayCharacter c) {
		for(int i = 0; i < specialChars.length; i++) {
			if(specialChars[i].equals(c)){
				availableChars.add((byte)i);
			}
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
		lcd = new CharLCD(Pins.PIN_LCD_RS, Pins.PIN_LCD_E, Pins.PINS_LCD_DB,
				Pins.PIN_LCD_BCKLGHT, cols, rows, dotsize);
		
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
