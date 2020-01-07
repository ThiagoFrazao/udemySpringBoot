package crud.hsqldb.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import crud.hsqldb.models.Customer;
import crud.hsqldb.models.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {
	Optional<Invoice> findById(Integer id);
	
	Optional<List<Invoice>> findByCustomer(Customer customer);
}
