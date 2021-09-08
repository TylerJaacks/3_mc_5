package edu.tjaacks.springlogindemo;

import edu.tjaacks.springlogindemo.domainobjects.User;
import edu.tjaacks.springlogindemo.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        User user1 = new User("tjaacks@iastate.edu", "password");
        User user2 = new User("tylerjaacks@gmail.com", "password");

        this.userRepository.save(user1);
        this.userRepository.save(user2);
    }

    @Test
    public void testFetchData() {
        User user1 = userRepository.findByEmail("tjaacks@iastate.edu");
        User user2 = userRepository.findByEmail("tylerjaacks@gmail.com");

        Iterable<User> userIterable = userRepository.findAll();

        int count = 0;

        for (User user : userIterable) {
            System.out.println("User Email: " + user.getEmail() + " User Password: " + user.getPassword());

            count++;
        }

        Assert.assertEquals(2, count);
    }
}