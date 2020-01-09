package crud.hsqldb.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
public class JwtUser implements UserDetails {

	private static final long serialVersionUID = -1145264061120493124L;
	
	private Integer id;

	private String username;
	
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// Nao foi implementado
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// Nao foi implementado
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// Nao foi implementado
		return true;
	}

	@Override
	public boolean isEnabled() {
		// Nao foi implementado
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
