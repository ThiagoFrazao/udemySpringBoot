package crud.hsqldb.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import crud.hsqldb.models.User;
import crud.hsqldb.security.roles.SecurityRoles;

public class JwtUserFactory {
	public static JwtUser create(User user) {
		return new JwtUser(user.getId(),user.getEmail(),user.getPassword(),mapToGrantedAuthorities(user.getRole()));
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(SecurityRoles role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return authorities;
	}
}
