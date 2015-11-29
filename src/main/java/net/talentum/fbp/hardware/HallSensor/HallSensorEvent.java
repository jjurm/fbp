package net.talentum.fbp.hardware.HallSensor;

public class HallSensorEvent {

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
