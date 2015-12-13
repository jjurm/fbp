package net.talentum.fbp.hardware;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Includes hardware pin definitions for FBP.
 * 
 * @author padr
 */
public class Pins {

	// @formatter:off
	public static Pin PIN_LED_ON		= RaspiPin.GPIO_15;  // GPIO 14
	public static Pin PIN_LED_ACT		= RaspiPin.GPIO_00;  // GPIO 17
	public static Pin PIN_LED_ERR		= RaspiPin.GPIO_07;  // GPIO 04

	public static Pin PIN_BTN_L			= RaspiPin.GPIO_02;  // GPIO 27
	public static Pin PIN_BTN_OK		= RaspiPin.GPIO_03;  // GPIO 22
	public static Pin PIN_BTN_R			= RaspiPin.GPIO_12;  // GPIO 10

	public static Pin PIN_HALL			= RaspiPin.GPIO_25;  // GPIO 26

	public static Pin PIN_PIEZO			= RaspiPin.GPIO_29;  // GPIO 21

	public static Pin PIN_LCD_BCKLGHT	= RaspiPin.GPIO_28;  // GPIO 20
	public static Pin PIN_LCD_RS		= RaspiPin.GPIO_13;  // GPIO 09
	public static Pin PIN_LCD_E			= RaspiPin.GPIO_14;  // GPIO 11
	public static Pin[] PINS_LCD_DB = {
										  RaspiPin.GPIO_21,  // GPIO 05
										  RaspiPin.GPIO_22,  // GPIO 06
										  RaspiPin.GPIO_23,  // GPIO 13
										  RaspiPin.GPIO_24   // GPIO 19
	};
	// @formatter:on

}
