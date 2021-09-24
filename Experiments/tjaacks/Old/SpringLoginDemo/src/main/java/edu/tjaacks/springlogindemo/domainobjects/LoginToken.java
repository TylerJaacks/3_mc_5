package edu.tjaacks.springlogindemo.domainobjects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "logintoken")
public class LoginToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @NotBlank int id;
    private @NotBlank String user;
    private @NotBlank String token;
    private @NotBlank String expiration;

    public LoginToken() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginToken that = (LoginToken) o;
        return id == that.id && Objects.equals(user, that.user) && Objects.equals(token, that.token) && Objects.equals(expiration, that.expiration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, token, expiration);
    }
}
