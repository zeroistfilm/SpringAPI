package com.youngdong.woowahan.service;

import com.youngdong.woowahan.domain.User;
import com.youngdong.woowahan.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @Commit
    void UserInsert(){
        //given
        User user = new User();
        user.setName("youngdong");
        user.setEmail("zeroistfilm@naver.com");

        //when
        User saveUser = userService.createUser(user);

        //then


    }

}