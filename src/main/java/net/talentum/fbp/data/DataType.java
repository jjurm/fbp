package net.talentum.fbp.data;

/**
 * Enum determining the type of the data in the database.</br>
 * {@code HALL} - Data from the HallSensor</br>
 * {@code ERROR} - Data about errors and caught exceptions</br>
 * {@code DEBUG} - Data used to make debugging easier</br>
 * {@code INFO} - Data about some actions performed by the program</br>
 * @author padr31
 *
 */
public enum DataType {

	HALL("hall"),
	ERROR("error"),
	DEBUG("debug"),
	INFO("info");
	
	private String name;
	
	DataType(String name) {
		this.name = name;
	}
	public String toString() {
		return this.name;
	}
	
}
