package com.youngdong.woowahan.service;

import com.google.gson.JsonObject;
import com.youngdong.woowahan.domain.User;
import com.youngdong.woowahan.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.transaction.Transactional;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @DisplayName("JPA 올바른유저등록")
    void UserInsert() {
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
    @DisplayName("HTTP 이름빈유저등록")
    void http유저등록빈이름() throws Exception {
        //given
        User user = new User();
        user.setName("");
        user.setEmail("zeroistfilm@naver.com");


        JsonObject obj = new JsonObject();
        obj.addProperty("name", user.getName());
        obj.addProperty("email", user.getEmail());


        mockMvc.perform(post("/user/new")
                .content(obj.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("회원 이름 정보가 없습니다"));
    }

    @Test
    @DisplayName("HTTP 올바른 유저 등록")
    void http유저등록() throws Exception {
        //given
        User user = new User();
        user.setName("youngdong");
        user.setEmail("zeroistfilm@naver.com");


        JsonObject obj = new JsonObject();
        obj.addProperty("name", user.getName());
        obj.addProperty("email", user.getEmail());


        mockMvc.perform(post("/user/new")
                .content(obj.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    @DisplayName("HTTP 잘못된이메일등록")
    void http잘못된유저등록() throws Exception {
        //given
        String[] invaildTestCase = {
                "  @naver.com ",            // ('계정')없음
                "zeroistfilmnaver.com ",    // ('@')없음
                "zeroistfilm@.com ",        // ('도메인')없음
                "zeroistfilm@navercom ",    // ('.')없음
                "zeroistfilm@naver. ",      // ('최상위 도메인')없음
                "  naver.com ",             // ('계정', '@')없음
                "@.com ",                   // ('계정', '도메인')없음
                "@navercom ",               // ('계정', '.')없음
                " @naver. ",                // ('계정', '최상위 도메인')없음
                "zeroistfilm.com ",         // ('@', '도메인')없음
                " zeroistfilmnavercom ",    // ('@', '.')없음
                "zeroistfilmnaver. ",       // ('@', '최상위 도메인')없음
                " zeroistfilm@com ",        // ('도메인', '.')없음
                "    zeroistfilm@. ",       // ('도메인', '최상위 도메인')없음
                "    zeroistfilm@naver ",   // ('.', '최상위 도메인')없음
                ".com ",                    // ('계정', '@', '도메인')없음
                "   navercom ",             // ('계정', '@', '.')없음
                "naver. ",                  // ('계정', '@', '최상위 도메인')없음
                "@com ",                    // ('계정', '도메인', '.')없음
                "@. ",                      // ('계정', '도메인', '최상위 도메인')없음
                "  @naver ",                // ('계정', '.', '최상위 도메인')없음
                "zeroistfilmcom ",          // ('@', '도메인', '.')없음
                "   zeroistfilm. ",         // ('@', '도메인', '최상위 도메인')없음
                "zeroistfilmnaver ",        // ('@', '.', '최상위 도메인')없음
                "   zeroistfilm@ ",         // ('도메인', '.', '최상위 도메인')없음
                "com ",                     // ('계정', '@', '도메인', '.')없음
                ". ",                       // ('계정', '@', '도메인', '최상위 도메인')없음
                "naver ",                   // ('계정', '@', '.', '최상위 도메인')없음
                "@ ",                       // ('계정', '도메인', '.', '최상위 도메인')없음
                "zeroistfilm ",             // ('@', '도메인', '.', '최상위 도메인')없음
                "",                         // (계정', '@', '도메인', '.', '최상위 도메인')없음
        };

        for (String casei : invaildTestCase) {
            User user = new User();
            user.setName("anonymous");
            user.setEmail(casei);
            JsonObject obj = new JsonObject();
            obj.addProperty("name", user.getName());
            obj.addProperty("email", user.getEmail());

            mockMvc.perform(post("/user/new")
                    .content(obj.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(status().reason("회원 이메일 정보가 양식에 맞지 않습니다"));
        }
    }


    @Test
    @DisplayName("HTTP 유저정보 수정")
    void HTTP유저정보수정() throws Exception {
        //given
        User user = new User();
        user.setName("youngdong");
        user.setEmail("zeroistfilm@naver.com");
        user.isVailid();
        Long saveID = userService.join(user);

        //when
        String newName = "newName";
        String newEmail = "new@new.new";

        JsonObject obj = new JsonObject();
        obj.addProperty("name", newName);
        obj.addProperty("email", newEmail);

        //then
        mockMvc.perform(put("/user").param("id", String.valueOf(saveID))
                .content(obj.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("HTTP 유저정보 수정 // UID 오류")
    void HTTP유저정보수정InvaildID() throws Exception {

        //when
        String newName = "newName";
        String newEmail = "new@new.new";

        JsonObject obj = new JsonObject();
        obj.addProperty("name", newName);
        obj.addProperty("email", newEmail);

        //then
        mockMvc.perform(put("/user").param("id", "9999999999999")
                .content(obj.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(status().reason("요청한 User ID가 데이터 베이스에 존재하지 않습니다"));

    }

    @Test
    @DisplayName("HTTP 유저데이터 페이징")
    void HTTP유저데이터페이징() throws Exception {
        //1. 100개의 유저테이터를 생성
        //2. 총 요청페이지 수, 현재 요청페이지 랜덤으로 생성
        //3. 총 요청페이지 수보다 현재 요청페이지가 크다면 실패
        //4. 점검
        for (int j = 0; j < 5; j++) {
            //given
            User[] userlist = new User[100];
            for (int i = 0; i < 100; i++) {
                User tmp = new User();
                tmp.setName(String.valueOf(i));
                tmp.setEmail("zeroistfilm@naver.com");
                tmp.isVailid();
                userlist[i] = tmp;
                Long saveID = userService.join(tmp);
            }

            //when
            Random random = new Random();
            int requestpage = random.nextInt(30);
            int pagesize = random.nextInt(30)+1;

            Pageable sortedById = PageRequest.of(requestpage, pagesize, Sort.by("uid"));
            Page<User> allpages = this.userService.findAll(sortedById);

            ResultMatcher result;
            if (requestpage > allpages.getTotalPages()) {
                result = status().isBadRequest();
            } else {
                result = status().isOk();
            }

            //then
            mockMvc.perform(get("/user/allPages")
                    .param("pagesize", String.valueOf(pagesize))
                    .param("requestpage", String.valueOf(requestpage)))
                    .andDo(print())
                    .andExpect(result);
        }
    }


}