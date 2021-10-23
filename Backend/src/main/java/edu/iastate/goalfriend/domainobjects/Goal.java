package edu.iastate.goalfriend.domainobjects;

import edu.iastate.goalfriend.GoalCategory;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @NotBlank @NotNull @Column(unique = true) int id;

    private @NotBlank @NotNull String goalName;

    @OneToOne(cascade=CascadeType.ALL)
    private @NotBlank @NotNull User goalOwner;

    private @NotBlank @NotNull GoalCategory goalCategory;

    private @NotBlank @NotNull float goalProgress;

    public Goal(){

    }

    public Goal(String goalName, User goalOwner, GoalCategory goalCategory, float goalProgress){
        this.goalName = goalName;
        this.goalOwner = goalOwner;
        this.goalCategory = goalCategory;
        this.goalProgress = goalProgress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public User getGoalOwner() {
        return goalOwner;
    }

    public void setGoalOwner(User goalOwner) {
        this.goalOwner = goalOwner;
    }

    public GoalCategory getGoalCategory() {
        return goalCategory;
    }

    public void setGoalCategory(GoalCategory goalCategory) {
        this.goalCategory = goalCategory;
    }

    public float getGoalProgress(){
        return goalProgress;
    }

    public void setGoalProgress(float goalProgress){
        this.goalProgress = goalProgress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Goal)) return false;
        Goal goal = (Goal) o;
        return getId() == goal.getId() && Float.compare(goal.getGoalProgress(), getGoalProgress()) == 0 && getGoalName().equals(goal.getGoalName()) && getGoalOwner().equals(goal.getGoalOwner()) && getGoalCategory() == goal.getGoalCategory();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGoalName(), getGoalOwner(), getGoalCategory(), getGoalProgress());
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", goalName='" + goalName + '\'' +
                ", goalOwner=" + goalOwner +
                ", goalCategory=" + goalCategory +
                ", goalProgress=" + goalProgress +
                '}';
    }
}
