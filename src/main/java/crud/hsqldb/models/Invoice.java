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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "INVOICE" )
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "CUSTOMERID")
	private Customer customer;
	
	private Float total;
	
	@Column(name = "LAUNCHDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date launchDate;
	
	@PrePersist
	public void prePersist() {
		if(this.launchDate == null) {
			final Date invoiceDate = new Date();
			this.launchDate = invoiceDate;
		}		
	}
}
