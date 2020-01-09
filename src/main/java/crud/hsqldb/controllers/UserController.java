package crud.hsqldb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crud.hsqldb.models.User;
import crud.hsqldb.repositories.UserRepository;

@RestController
@RequestMapping("/crud/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public ResponseEntity<Iterable<User>> updateAll(){
		return ResponseEntity.ok(userRepository.findAll());
	}
	
}
