package com.folders.rentaly;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;

import com.folders.rentaly.model.User;
import com.folders.rentaly.persistence.UserRepository;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;

public class Utilities {

	private static UserRepository userRepository;

	public static void setUserRepository(UserRepository userRepository) {
		Utilities.userRepository = userRepository;
	}

	public static User getUser(HttpSession session) {
		SecurityContext s = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		UserDetails ud = (UserDetails) s.getAuthentication().getPrincipal();
		User user = userRepository.findByEmail(ud.getUsername());
		return user;
	}


	public static String encrypt(String password, String salt) {
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec spec = new SecretKeySpec(salt.getBytes(), "HmacSHA256");
			mac.init(spec);
			return Base64.getEncoder().encodeToString(mac.doFinal());
		}
		catch (InvalidKeyException | NoSuchAlgorithmException e) {
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
