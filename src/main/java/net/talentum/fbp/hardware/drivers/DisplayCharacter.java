package net.talentum.fbp.hardware.drivers;

public enum DisplayCharacter {
	
	NOTHING(new byte[]{0,0,0,0,0,0,0,0}),
	SMILE(new byte[]{0x0, 0xA, 0x0, 0x0, 0x11, 0xE, 0x0, 0x0});
	
	private final byte[] bytes;
	
	DisplayCharacter(byte[] bytes){
		this.bytes = bytes;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
}
