package net.talentum.fbp.hardware;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Includes hardware pin definitions for FBP.
 * 
 * @author padr
 */
public class Pins {

	public static Pin PIN_LED_ON = RaspiPin.GPIO_14;
	public static Pin PIN_LED_ACT = RaspiPin.GPIO_17;
	public static Pin PIN_LED_ERR = RaspiPin.GPIO_04;

	public static Pin PIN_BTN_L = RaspiPin.GPIO_27;
	public static Pin PIN_BTN_OK = RaspiPin.GPIO_22;
	public static Pin PIN_BTN_R = RaspiPin.GPIO_10;

	public static Pin PIN_HALL = RaspiPin.GPIO_26;

	public static Pin PIN_PIEZO = RaspiPin.GPIO_21;

	public static Pin PIN_LCD_BCKLGHT = RaspiPin.GPIO_20;
	public static Pin PIN_LCD_RS = RaspiPin.GPIO_09;
	public static Pin PIN_LCD_E = RaspiPin.GPIO_11;
	public static Pin[] PINS_LCD_DB = {
			RaspiPin.GPIO_05, 
			RaspiPin.GPIO_06, 
			RaspiPin.GPIO_13, 
			RaspiPin.GPIO_19
	};

}
