package com.codejava.login;

import com.codejava.login.entity.User;
import com.codejava.login.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void testCreateUser(){
        User user=new User();
        user.setEmail("b@123");
        user.setPassword("b2020");
        user.setFirstName("ball");
        user.setLastName("kumar");

        User saveUser=userRepository.save(user);

        User existUser=entityManager.find(User.class,saveUser.getId());

        assertThat(existUser.getEmail().equals(user.getEmail()));
    }

}
