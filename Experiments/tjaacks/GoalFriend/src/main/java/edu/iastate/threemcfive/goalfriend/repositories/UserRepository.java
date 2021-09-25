package edu.iastate.threemcfive.goalfriend.repositories;

import edu.iastate.threemcfive.goalfriend.domainobjects.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
    User findById(int id);
}
