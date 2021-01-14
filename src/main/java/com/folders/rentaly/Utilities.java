package com.folders.rentaly;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilities {
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		int v;
		
		for (int j = 0; j < bytes.length; j++) {
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		
		return new String(hexChars);
	}

	// TODO Change this to something else, maybe stored in the database
	private static String SALT = "123456";
	
	
	public static String encrypt(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(SALT.getBytes());        // <-- Prepend SALT.
			md.update(password.getBytes());
			// md.update(SALT.getBytes());     // <-- Or, append SALT.

			byte[] out = md.digest();
			return bytesToHex(out);            // <-- Return the Hex Hash.
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	
}
