package edu.iastate.goalfriend.domainobjects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="token")
public class Token implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @NotBlank @NotNull @Column(unique=true) int id;

    @NotBlank
    @NotNull
    @Column(unique=true)
    @Size(max = 32)
    private String token;

    @NotBlank
    @NotNull
    private Long creationDate;

    @NotBlank
    @NotNull
    private Long expirationTime;

    public Token() {

    }

    public Token(String token, Long creationDate, Long expirationTime) {
        this.token = token;
        this.creationDate = creationDate;
        this.expirationTime = expirationTime;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return getId() == token.getId() && getToken().equals(token.getToken()) && getCreationDate().equals(token.getCreationDate()) && getExpirationTime().equals(token.getExpirationTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getToken(), getCreationDate(), getExpirationTime());
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token=" + token +
                ", creationDate=" + creationDate +
                ", expirationDate=" + expirationTime +
                '}';
    }
}