package crud.hsqldb.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ITEM")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item {
	@OneToOne
	@JoinColumn(name = "INVOICEID")
	private Invoice invoice;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer item;
	
	@OneToOne
	@JoinColumn(name = "PRODUCTID")
	private Product product;
	
	private Integer quantity;
	
	private Float cost;
}
