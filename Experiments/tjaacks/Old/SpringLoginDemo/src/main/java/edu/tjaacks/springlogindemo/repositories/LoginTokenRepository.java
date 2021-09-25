package edu.tjaacks.springlogindemo.repositories;

import edu.tjaacks.springlogindemo.domainobjects.LoginToken;
import edu.tjaacks.springlogindemo.domainobjects.User;
import org.springframework.data.repository.CrudRepository;

public interface LoginTokenRepository extends CrudRepository<LoginToken, Integer> {
    LoginToken findLoginTokenByUser(User user);
}
