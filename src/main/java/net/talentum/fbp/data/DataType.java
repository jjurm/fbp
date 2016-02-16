package net.talentum.fbp.data;

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
