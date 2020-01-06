package crud.hsqldb.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
}
