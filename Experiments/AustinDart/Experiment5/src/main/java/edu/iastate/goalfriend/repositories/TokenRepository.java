package edu.iastate.goalfriend.repositories;

import edu.iastate.goalfriend.domainobjects.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Integer> {
    Token findByUserId(int id);
    Token findByTokenString(String tokenString);
}