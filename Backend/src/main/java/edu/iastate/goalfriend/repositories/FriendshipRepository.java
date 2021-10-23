package edu.iastate.goalfriend.repositories;

import edu.iastate.goalfriend.domainobjects.Friendship;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// TODO: I think these will work but not sure.
public interface FriendshipRepository extends CrudRepository<Friendship, Integer> {
    List<Friendship> getAllByUser1UsernameEquals(String username);
    List<Friendship> getAllByUser2UsernameEquals(String username);
}
