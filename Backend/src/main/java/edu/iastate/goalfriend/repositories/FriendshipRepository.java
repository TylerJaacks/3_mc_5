package edu.iastate.goalfriend.repositories;

import edu.iastate.goalfriend.domainobjects.Friendship;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendshipRepository extends CrudRepository<Friendship, Integer> {
    List<Friendship> getAllByUsername1(String username);
    List<Friendship> getAllByUsername2(String username);

    List<Friendship> getAllByUsername1Equals(String username);
    List<Friendship> getAllByUsername2Equals(String username);
}
