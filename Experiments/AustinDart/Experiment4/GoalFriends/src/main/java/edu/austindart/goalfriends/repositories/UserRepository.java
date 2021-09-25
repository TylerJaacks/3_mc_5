package edu.austindart.goalfriends.repositories;

import edu.austindart.goalfriends.domainobjects.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);
    User findById(int id);

}
