package crud.hsqldb.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import crud.hsqldb.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
	Optional<Customer> findById(Integer id);
	
	@Query(value="SELECT * FROM CUSTOMER WHERE FIRSTNAME = :firstName and lastname=:lastName",nativeQuery = true)
	Optional<Customer> findByFullName(@Param("firstName")String firstName,@Param("lastName") String lastName);
	
	default boolean alreadyExists(Customer customer) {
		return findByFullName(customer.getFirstName(),customer.getLastName()).isPresent();
	}
}
