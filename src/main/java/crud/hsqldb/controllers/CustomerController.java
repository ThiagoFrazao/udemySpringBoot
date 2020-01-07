package crud.hsqldb.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import crud.hsqldb.models.Customer;
import crud.hsqldb.repositories.CustomerRepository;

@RestController
@RequestMapping("/crud/customer")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping
	public List<Customer> getAllCustomers(){
		return (List<Customer>) customerRepository.findAll();
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathParam(value = "id") Integer id){
		return customerRepository.findById(id).map(customer -> {return ResponseEntity.ok(customer);})
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/find")
	public ResponseEntity<Customer> getCustomerByFullName(@RequestParam("firstName")String firstName,@RequestParam("lastName")String lastName){
		return customerRepository.findByFullName(firstName,lastName).map(customer -> {return ResponseEntity.ok(customer);})
			       .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addCustomer(@RequestBody Customer customer){
		if(customerRepository.alreadyExists(customer)) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer already registered.");
		} else {
			Customer createdCustomer = customerRepository.save(customer);
			URI customerUri = null;
			try {
				customerUri = new URI(String.format("http://localhost:8080/crud/customer/find/%d", createdCustomer.getId()));
			} catch (URISyntaxException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A server error ocurred. Try again later.");
			}
			return ResponseEntity.created(customerUri).body("Customer created successfully.");
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateCustomer(@RequestBody Customer customer){
		if(customerRepository.alreadyExists(customer)) {
			customerRepository.save(customer);
			return ResponseEntity.ok("Customer updated sucessfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(String.format("Customer %s %s don't exists.", customer.getFirstName(),customer.getLastName()));
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCustomerById(@PathParam("id") Integer id){
		if(customerRepository.existsById(id)) {
			customerRepository.deleteById(id);
			return ResponseEntity.ok("Customer removed sucessfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(String.format("Customer with identifier %d don't exists.", id));
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteCustomerByFullName(@RequestParam("firstName")String firstName,@RequestParam("lastName")String lastName){
		if(customerRepository.alreadyExists(firstName,lastName)) {
			customerRepository.deleteByFullName(firstName, lastName);
			return ResponseEntity.ok("Customer deleted sucessfully.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(String.format("Customer %s %s don't exists.", firstName,lastName));
		}
	}
}
