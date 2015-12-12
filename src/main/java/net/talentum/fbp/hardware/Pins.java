package net.talentum.fbp.hardware;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Includes hardware pin definitions for FBP.
 * 
 * @author padr
 */
public class Pins {

	public static Pin PIN_LED_ON = RaspiPin.GPIO_15;
	public static Pin PIN_LED_ACT = RaspiPin.GPIO_00;
	public static Pin PIN_LED_ERR = RaspiPin.GPIO_07;

	public static Pin PIN_BTN_L = RaspiPin.GPIO_02;
	public static Pin PIN_BTN_OK = RaspiPin.GPIO_03;
	public static Pin PIN_BTN_R = RaspiPin.GPIO_12;

	public static Pin PIN_HALL = RaspiPin.GPIO_25;

	public static Pin PIN_PIEZO = RaspiPin.GPIO_29;

	public static Pin PIN_LCD_BCKLGHT = RaspiPin.GPIO_28;
	public static Pin PIN_LCD_RS = RaspiPin.GPIO_13;
	public static Pin PIN_LCD_E = RaspiPin.GPIO_14;
	public static Pin[] PINS_LCD_DB = {
			RaspiPin.GPIO_21,
			RaspiPin.GPIO_22,
			RaspiPin.GPIO_13,
			RaspiPin.GPIO_24
	};

}
