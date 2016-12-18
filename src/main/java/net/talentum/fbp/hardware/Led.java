package net.talentum.fbp.hardware;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

/**
 * Class representing one LED light.
 * 
 * @author padr
 * @deprecated Use {@link OutputDevice} instead
 */
public class Led {

  public GpioPinDigitalOutput out;

  public Led(Pin pin, GpioController gpio) {
    out = gpio.provisionDigitalOutputPin(pin, PinState.LOW);
  }

  public void on() {
    out.high();
  }

  public void off() {
    out.low();
  }

}
