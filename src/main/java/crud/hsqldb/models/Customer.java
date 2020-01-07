package crud.hsqldb.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CUSTOMER")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty(message = "FirstName can't be empty.")
	@Column(name="FIRSTNAME",nullable = false)
	private String firstName;
	
	@NotEmpty(message = "LastName can't be empty.")
	@Column(name = "LASTNAME",nullable = false)
	private String lastName;
	
	private String street;
	
	private String city;
	
	@OneToOne
	@JoinColumn(name = "USERINFO")
	private User userInfo;
}
