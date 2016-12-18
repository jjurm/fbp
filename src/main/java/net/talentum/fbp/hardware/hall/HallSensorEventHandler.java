package net.talentum.fbp.hardware.hall;

/**
 * Handler interface responsible for taking care of {@link HallSensorEvent}. Every class which needs
 * to work with these events will implement this interface.
 * 
 * @author padr31
 *
 */
public interface HallSensorEventHandler {

  public void hallSensorStateChanged(HallSensorEvent event);
}
