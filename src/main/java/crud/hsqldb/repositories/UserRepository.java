package crud.hsqldb.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import crud.hsqldb.models.User;

public interface UserRepository extends CrudRepository<User,Integer>{
	Optional<User> findByEmail(String email);
	
	
}
