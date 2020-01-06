package crud.hsqldb.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
	
	private static BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
	
	public static String hashPassword(String password) {
		return passEncoder.encode(password);
	}
	
	public static boolean validatePassword(String password, String hashPassword) {
		return passEncoder.matches(password, hashPassword);
	}
}
