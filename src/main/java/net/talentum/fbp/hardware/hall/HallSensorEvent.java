package net.talentum.fbp.hardware.hall;

/**
 * One event that can be triggered by the {@link HallSensor}. Events are unique, specified by the
 * time of the event and the state of the {@link HallSensor} after the event has happened.
 * 
 * @author padr31
 *
 */
public class HallSensorEvent {

  /**
   * The state of the sensor after the event.
   */
  private HallSensorState state;
  private long time;

  public HallSensorEvent(HallSensorState state, long time) {
    this.setState(state);
    this.setTime(time);
  }

  public HallSensorState getState() {
    return state;
  }

  public void setState(HallSensorState state) {
    this.state = state;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }
}
