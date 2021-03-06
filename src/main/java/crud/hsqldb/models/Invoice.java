package crud.hsqldb.models;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INVOICE" )
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "CUSTOMERID")
	private Customer customer;
	
	private Float total;
	
	@Column(name = "LAUNCHDATE")
	@Temporal(TemporalType.DATE)
	private Date launchDate;
	
	@PrePersist
	public void prePersist() {
		if(this.launchDate == null) {
			this.launchDate = new Date();
		}		
	}
}