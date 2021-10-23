package edu.iastate.goalfriend.repositories;

import edu.iastate.goalfriend.domainobjects.Goal;
import edu.iastate.goalfriend.domainobjects.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoalRepository extends CrudRepository<Goal, Integer> {
    List<Goal> getAllByGoalOwnerEquals(User goalOwner);
    List<Goal> getAll();
}
