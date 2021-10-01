package edu.iastate.threemcfive.goalfriend.domainobjects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @NotBlank @Column(unique=true) int id;
    private @NotBlank @Column(unique=true) String email;
    private @NotBlank String password;
    private @NotBlank @Column(unique=true) String username;
    private @NotBlank int isLoggedIn;

    public User() {

    }

    public User(@NotBlank String email, @NotBlank String password, @NotBlank String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public int getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(int isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn ? 1 : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && isLoggedIn == user.isLoggedIn && getEmail().equals(user.getEmail()) && getPassword().equals(user.getPassword()) && getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword(), getUsername(), isLoggedIn);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", isLoggedIn=" + isLoggedIn +
                '}';
    }
}
