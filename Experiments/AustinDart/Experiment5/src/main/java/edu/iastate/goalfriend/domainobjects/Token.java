package edu.iastate.goalfriend.domainobjects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

// T0D0: Make sure constraints and types are correct.
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @NotBlank @Column(unique=true) int id;
    private @NotBlank @Column(unique=true) int userId;

    private @NotBlank @Column(unique=true) String tokenString;

    public Token() {

    }

    public Token(@NotBlank String tokenString, @NotBlank int userId) {
        this.tokenString = tokenString;
        this.userId = userId;
    }

    public int getUserId(){
        return userId;
    }

    public String getTokenString(){
        return tokenString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return id == token.id && getUserId() == token.getUserId() && getTokenString().equals(token.getTokenString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getUserId(), getTokenString());
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", userId=" + userId +
                ", tokenString='" + tokenString + '\'' +
                '}';
    }
}
