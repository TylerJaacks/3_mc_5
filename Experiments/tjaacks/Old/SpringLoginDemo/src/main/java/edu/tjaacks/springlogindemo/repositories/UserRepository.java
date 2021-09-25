package edu.tjaacks.springlogindemo.repositories;

import edu.tjaacks.springlogindemo.domainobjects.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String name);
    User findByPassword(String password);
}
