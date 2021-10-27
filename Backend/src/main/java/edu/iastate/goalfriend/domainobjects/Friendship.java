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

    // TODO: These should be a relationship but I don't care to fix this for demo3.
    private @NotBlank String username1;
    private @NotBlank String username2;
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

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Friendship)) return false;
        Friendship that = (Friendship) o;
        return getId() == that.getId() && getUsername1().equals(that.getUsername1()) && getUsername2().equals(that.getUsername2()) && getFriendshipType() == that.getFriendshipType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername1(), getUsername2(), getFriendshipType());
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", username1=" + username1 +
                ", username2=" + username2 +
                ", friendshipType=" + friendshipType +
                '}';
    }
}
