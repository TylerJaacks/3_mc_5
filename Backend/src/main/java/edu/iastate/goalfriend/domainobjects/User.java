package edu.iastate.goalfriend.domainobjects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @NotBlank @NotNull @Column(unique=true) int id;
    private @NotBlank @NotNull @Column(unique=true) String email;
    private @NotBlank @NotNull String password;
    private @NotBlank @NotNull @Column(unique=true) String username;
    private @NotBlank @NotNull int isLoggedIn = 0;
    private @NotBlank @NotNull @Column(unique=true) String phoneNumber;

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
    private Token token;

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
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

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && isLoggedIn == user.isLoggedIn && getEmail().equals(user.getEmail()) && getPassword().equals(user.getPassword()) && getUsername().equals(user.getUsername()) && token.equals(getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword(), getUsername(), getIsLoggedIn(), getToken());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", isLoggedIn=" + isLoggedIn +
                ", token=" + token +
                '}';
    }
}
