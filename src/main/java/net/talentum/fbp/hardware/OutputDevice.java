package net.talentum.fbp.hardware;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

/**
 * This class represents an output device running on a pin. Used instead of defining Led, Piezo,
 * etc. separately.
 * 
 * @author padr31
 *
 */
public class OutputDevice extends Device {

  protected GpioPinDigitalOutput out;

  public OutputDevice(Pin pin, GpioController gpio) {
    super(pin, gpio);
    out = gpio.provisionDigitalOutputPin(pin, PinState.LOW);
  }

  public GpioPinDigitalOutput getOutput() {
    return this.out;
  }

}
