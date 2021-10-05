package edu.iastate.goalfriend.domainobjects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

// T0D0: Make sure constraints and types are correct.
@Entity
@Table(name="token")
public class Token implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @NotBlank @Column(unique=true) int id;

    @OneToOne
    @JoinTable(name = "users", joinColumns = @JoinColumn(name = "id"))
    private User user;

    @NotBlank
    @Column(unique=true)
    private String token;

    @NotBlank
    private Long creationDate;

    @NotBlank
    private Long expirationTime;

    public Token(User user, String token, Long creationDate, Long expirationTime) {
        this.user = user;
        this.token = token;
        this.creationDate = creationDate;
        this.expirationTime = expirationTime;
    }

    public Token() {

    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
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
        return getId() == token.getId() && getUser().equals(token.getUser()) && getToken().equals(token.getToken()) && getCreationDate().equals(token.getCreationDate()) && getExpirationTime().equals(token.getExpirationTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getToken(), getCreationDate(), getExpirationTime());
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", user=" + user +
                ", token=" + token +
                ", creationDate=" + creationDate +
                ", expirationDate=" + expirationTime +
                '}';
    }
}