package crud.hsqldb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import crud.hsqldb.models.Customer;
import crud.hsqldb.models.User;
import crud.hsqldb.repositories.CustomerRepository;
import crud.hsqldb.repositories.UserRepository;
import crud.hsqldb.security.roles.SecurityRoles;
import crud.hsqldb.utils.PasswordUtils;

@SpringBootApplication
public class CrudHsqldbApplication {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CustomerRepository custRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CrudHsqldbApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			PasswordUtils password = new PasswordUtils();
			User user = new User();
			user.setEmail("admin@fake.com");
			user.setPassword(password.hashPassword("admin"));
			user.setRole(SecurityRoles.ROLE_ADMIN);
			userRepo.save(user);
			Customer cust = new Customer();
			cust.setFirstName("Heroku");
			cust.setLastName("AdminTest");
			cust.setStreet("HerokuCloud");
			cust.setCity("Internet Cloud");
			cust.setUserInfo(user);
			custRepo.save(cust);
		};
	}
}