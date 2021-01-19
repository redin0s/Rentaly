package com.folders.rentaly;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean emailCheck(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
	    return matcher.find();
    }
	
}
