package edu.iastate.goalfriend.repositories;

import edu.iastate.goalfriend.domainobjects.Friendship;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendshipRepository extends CrudRepository<Friendship, Integer> {
    List<Friendship> getAllByUser1UsernameEquals(String username);
}
