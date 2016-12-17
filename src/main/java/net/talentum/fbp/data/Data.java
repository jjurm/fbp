package net.talentum.fbp.data;

import java.sql.Timestamp;

/**
 * Class encapsulating the structure of columns in the database. 
 * Every row in the database can be expressed as an instance of {@link Data} and vice-versa.
 * An instance of this class is built up using the builder pattern to make it possible for variable body
 * and parameters.
 * @author padr31
 *
 */
public class Data {

	private final DataType dataType;
	private final Timestamp time;
	private final Object value;
	
	public static class Builder {
		//required parameters
		private final DataType dataType;
		private final Timestamp time;
		
		//optional parameters
		private Object value = null;
		
		public Builder(DataType dataType, Timestamp timestamp) {
			this.dataType = dataType;
			this.time = timestamp;
		}
		
		public Builder setValue(Object value) {
			this.value = value;
			return this;
		}
		
		public Data build() {
			return new Data(this);
		}
	}
	
	private Data(Builder builder) {
		dataType = builder.dataType;
		time = builder.time;
		value = builder.value;
	}
	
	public DataType getType(){
		return dataType;
	}
	
	public Timestamp getTime() {
		return time;
	}
	
	public Object getValue() {
		return value;
	}
}
