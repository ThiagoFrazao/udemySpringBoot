package crud.hsqldb.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {
	
	private BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
	
	public String hashPassword(String password) {
		return passEncoder.encode(password);
	}
	
	public boolean validatePassword(String password, String hashPassword) {
		return passEncoder.matches(password, hashPassword);
	}
}