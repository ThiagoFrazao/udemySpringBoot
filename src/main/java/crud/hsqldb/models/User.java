package crud.hsqldb.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import crud.hsqldb.security.roles.SecurityRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false,unique = true)
	@NotEmpty(message = "Email can't be empty.")
	@Email(message = "Invalid email.")
	private String email;
	
	@Column(nullable = false)
	@NotEmpty(message = "Password can't be empty.")
	private String password;
	
	@Enumerated(EnumType.STRING)
	private SecurityRoles role;
	
	public SecurityRoles getRole() {
		if(this.role == null) {
			return SecurityRoles.ROLE_USER;
		} else {
			return this.role;
		}
	}
	
	@PrePersist
	public void prePersist() {
		if(this.role == null) {
			this.role = SecurityRoles.ROLE_USER;
		}
	}
}
