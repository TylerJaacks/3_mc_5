package edu.iastate.goalfriend.domainobjects;

import edu.iastate.goalfriend.GoalCategory;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
}
