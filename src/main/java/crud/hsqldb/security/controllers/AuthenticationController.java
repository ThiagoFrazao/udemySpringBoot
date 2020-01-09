package crud.hsqldb.security.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crud.hsqldb.models.User;
import crud.hsqldb.security.utils.JwtTokensUtils;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokensUtils jwtTokensUtils;
	
	@PostMapping
	public ResponseEntity<String> generateJwsToken(@Valid @RequestBody User user, BindingResult result){
		
		if(result.hasErrors()) {
			StringBuilder strBuilder = new StringBuilder("The User is invalid:\n");
			result.getAllErrors().forEach(error -> strBuilder.append(error.getDefaultMessage()+"\n"));
			return ResponseEntity.badRequest().body(strBuilder.toString());
		} else {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());	
			String token = jwtTokensUtils.createToken(userDetails);
			return ResponseEntity.ok("jwtToken: "+token);
		}
	}
}
