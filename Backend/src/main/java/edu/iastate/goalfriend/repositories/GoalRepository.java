package edu.iastate.goalfriend.repositories;

import edu.iastate.goalfriend.domainobjects.Goal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoalRepository extends CrudRepository<Goal, Integer> {

}
