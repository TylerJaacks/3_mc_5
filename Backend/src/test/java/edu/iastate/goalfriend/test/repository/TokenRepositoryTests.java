package edu.iastate.goalfriend.test.repository;

import edu.iastate.goalfriend.domainobjects.Token;
import edu.iastate.goalfriend.domainobjects.User;
import edu.iastate.goalfriend.repositories.TokenRepository;
import edu.iastate.goalfriend.repositories.UserRepository;
import edu.iastate.goalfriend.utils.TokenUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokenRepositoryTests {

    @Test
    public void getUserTest(){

        TokenRepository tokenRepository = mock(TokenRepository.class);

        String tokenString = TokenUtils.tokenGenerator();

        Date now = new Date();
        long time = now.getTime();

        when(tokenRepository.getByToken(tokenString)).thenReturn(new Token(tokenString, time, TokenUtils.EXPIRATION_TIME));

        Token foundToken = tokenRepository.getByToken(tokenString);

        assertEquals(tokenString, foundToken.getToken());
        assertEquals(Long.valueOf(time), Long.valueOf(foundToken.getCreationDate()));
    }
}
