package crud.hsqldb.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import crud.hsqldb.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
	
	Optional<Customer> findById(Integer id);
	
	@Query(value="DELETE FROM CUSTOMER WHERE id=?1",nativeQuery=true)
	void deleteById(Integer id);
	
	@Query(value="DELETE FROM CUSTOMER WHERE firstname = ?1 and lastname=?2",nativeQuery = true)
	void deleteByFullName(String firstName,String lastName);
	
	@Query(value="SELECT * FROM CUSTOMER WHERE firstname = ?1 and lastname=?2",nativeQuery = true)
	Optional<Customer> findByFullName(String firstName, String lastName);
	
	default boolean alreadyExists(String firstname, String lastName) {
		return findByFullName(firstname,lastName).isPresent();
	}
	
	default boolean alreadyExists(Customer customer) {
		return alreadyExists(customer.getFirstName(),customer.getLastName());
	}
}
