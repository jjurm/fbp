package net.talentum.fbp.system;

public class Utils {

	public static void sleep(int ms) {
		try{
			Thread.sleep(ms);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
