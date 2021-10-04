package edu.austindart.goalfriends.domainobjects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @NotBlank int id;
    private @NotBlank String email;
    private @NotBlank String password;
    private @NotBlank String username;
    private @NotBlank int isLoggedIn;


    public User(){

    }

    public User(@NotBlank String email, @NotBlank String password, @NotBlank String username){
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn == 1;
    }

    public void setIsLoggedIn(int loggedIn) {
        this.isLoggedIn = loggedIn;
    }

    public void setLoggedIn(boolean loggedIn){
        if(loggedIn){
            setIsLoggedIn(1);
        }else{
            setIsLoggedIn(0);
        }
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && isIsLoggedIn() == user.isIsLoggedIn() && getEmail().equals(user.getEmail()) && getPassword().equals(user.getPassword()) && getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getEmail(), getPassword(), getUsername(), isIsLoggedIn());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + username + '\'' +
                ", loggedIn=" + isLoggedIn +
                '}';
    }
}
