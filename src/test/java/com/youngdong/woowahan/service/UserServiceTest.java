package com.youngdong.woowahan.service;

import com.google.gson.JsonObject;
import com.youngdong.woowahan.domain.User;
import com.youngdong.woowahan.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
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

    @Test
    void  http유저등록() throws Exception {
        //given
        User user = new User();
        user.setName("youngdong");
        user.setEmail("zeroistfilm@naver.com");


        JsonObject obj =new JsonObject();
        obj.addProperty("name", user.getName());
        obj.addProperty("email", user.getEmail());


//        mockMvc.perform(post("/saveuser")
//                .content(obj.toString())
//                .contentType(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8"))
//
//                .andDo(print())
//                .andExpect(content().encoding("UTF-8"))
//                .andExpect(content().json());


    }


}