package edu.iastate.goalfriend.domainobjects;

import edu.iastate.goalfriend.FriendshipType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "friendship")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @NotBlank @NotNull
    @Column(unique = true) int id;

    private @NotBlank @NotNull @OneToOne(cascade=CascadeType.ALL) User user1;
    private @NotBlank @NotNull @OneToOne(cascade=CascadeType.ALL) User user2;
    private @NotBlank @NotNull FriendshipType friendshipType;
}
