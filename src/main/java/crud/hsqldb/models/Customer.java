package crud.hsqldb.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
	@Column(name="FIRSTNAME",nullable = false)
	private String firstName;
	
	@Column(name = "LASTNAME",nullable = false)
	private String lastName;
	
	private String street;
	
	private String city;
	
	@Column(nullable = false)
	private String password;
}
