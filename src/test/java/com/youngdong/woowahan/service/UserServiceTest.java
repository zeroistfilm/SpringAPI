package com.youngdong.woowahan.service;

import com.youngdong.woowahan.domain.User;
import com.youngdong.woowahan.repository.UserRepository;
import org.assertj.core.api.Assertions;
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
    void UserInsert(){
        //given
        User user = new User();
        user.setName("youngdong");
        user.setEmail("zeroistfilm@naver.com");

        user.isVailid();
        //when
        Long saveID = userService.join(user);

        //then
        User foundUser = userService.findOne(saveID).get();
        Assertions.assertThat(user.getUid()).isEqualTo(foundUser.getUid());
    }

}