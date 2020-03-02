package crud.hsqldb.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import crud.hsqldb.models.Customer;
import crud.hsqldb.models.User;
import crud.hsqldb.repositories.CustomerRepository;
import crud.hsqldb.repositories.UserRepository;
import crud.hsqldb.utils.PasswordUtils;

@RestController
@RequestMapping("/crud/customer")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private UserRepository userRespository;
	
	@Autowired
	private PasswordUtils passwordUtils;
	
	@GetMapping
	public ResponseEntity<Iterator<Customer>> getAllCustomers(){
		return ResponseEntity.ok(customerRepository.findAll().iterator());
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Integer id){
		return customerRepository.findById(id).map(customer -> {return ResponseEntity.ok(customer);})
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/find")
	public ResponseEntity<Customer> getCustomerByFullName(@RequestParam("firstName")String firstName,@RequestParam("lastName")String lastName){
		return customerRepository.findByFullName(firstName,lastName).map(customer -> {return ResponseEntity.ok(customer);})
			       .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addCustomer(@Valid @RequestBody Customer customer,BindingResult bindResult){
		if(bindResult.hasErrors()) {
			StringBuilder strBuilder = new StringBuilder("The received Customer is not valid:\n");
			bindResult.getAllErrors().forEach(error -> strBuilder.append(error.getDefaultMessage()+"\n"));
			return ResponseEntity.badRequest().body(strBuilder.toString());
		} else {
			if(customerRepository.alreadyExists(customer)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer already registered.");
			} else {
				User userInfo = customer.getUserInfo();
				if(userRespository.existsByEmail(userInfo.getEmail())){
					return ResponseEntity.status(HttpStatus.CONFLICT)
							.body(String.format("The email %s is already being used.", userInfo.getEmail()));
				} else {
					//### criptografando senha ###
					userInfo.setPassword(passwordUtils.hashPassword(userInfo.getPassword()));
					userRespository.save(userInfo); 
					customer.setUserInfo(userInfo);
					Customer createdCustomer = customerRepository.save(customer);
					URI customerUri = null;
					try {
						String uriTemplate = ServletUriComponentsBuilder.fromCurrentServletMapping().toUriString() + 
											 "/crud/customer/find/%d";
						customerUri = new URI(String.format(uriTemplate, createdCustomer.getId()));
					} catch (URISyntaxException e) {
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A server error ocurred. Try again later.");
					}
					return ResponseEntity.created(customerUri).body("Customer created successfully.");
				}
			}
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