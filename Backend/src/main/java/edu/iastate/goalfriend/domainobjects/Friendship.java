package edu.iastate.goalfriend.domainobjects;

import edu.iastate.goalfriend.FriendshipType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FriendshipType getFriendshipType(){
        return friendshipType;
    }

    public void setFriendshipType(FriendshipType friendshipType){
        this.friendshipType = friendshipType;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friendship)) return false;
        Friendship that = (Friendship) o;
        return getId() == that.getId() && getUser1().equals(that.getUser1()) && getUser2().equals(that.getUser2()) && getFriendshipType() == that.getFriendshipType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser1(), getUser2(), getFriendshipType());
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", user1=" + user1 +
                ", user2=" + user2 +
                ", friendshipType=" + friendshipType +
                '}';
    }
}
