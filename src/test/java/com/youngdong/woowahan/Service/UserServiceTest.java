package com.youngdong.woowahan.Service;

import com.youngdong.woowahan.DTO.UserDTO;
import com.youngdong.woowahan.Entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("JPA 올바른유저등록 및 조회")
    void UserInsert() {
        //given
        UserDTO userDTO = new UserDTO("youngdong", "zeroistfilm@naver.com");

        //when
        User saveuser = userService.create(userDTO);
        //then
        User finduser = userService.readOne(saveuser.getUid());
        Assertions.assertThat(saveuser.getUid()).isEqualTo(finduser.getUid());
    }

    @Test
    @DisplayName("JPA 빈 이름")
    void UserInsertNoName() {
        //given
        UserDTO userDTO = new UserDTO("", "zeroistfilm@naver.com");
        try {
            //when
            userService.create(userDTO);
        } catch (IllegalArgumentException e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("Name 정보가 없습니다");
        }
    }


    @Test
    @DisplayName("JPA 빈 이메일")
    void UserInsertNoEmail() {
        //given
        UserDTO userDTO = new UserDTO("youngdongkim", "");
        try {
            //when
            userService.create(userDTO);
        } catch (IllegalArgumentException e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("Email 정보가 없습니다");
        }
    }

    @Test
    @DisplayName("JPA 모든 정보 없음")
    void UserInsertNoNameEmail() {
        //given
        UserDTO userDTO = new UserDTO("", "");
        try {
            //when
            userService.create(userDTO);
        } catch (IllegalArgumentException e) {
            //then
            Assertions.assertThat(e.getMessage()).isEqualTo("Name Email 정보가 없습니다");
        }
    }

    @Test
    @DisplayName("JPA 모든 정보 없음")
    void UserInsertinvalidEmail() {
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
        };

        for (String casei : invaildTestCase) {
            UserDTO userDTO = new UserDTO("anonymous", casei);
            try {
                //when
                userService.create(userDTO);
            } catch (IllegalArgumentException e) {
                //then
                Assertions.assertThat(e.getMessage()).isEqualTo("회원 이메일 정보가 양식에 맞지 않습니다");
            }

        }
    }

    @Test
    @DisplayName("JPA 유저정보 수정")
    void JPA유저정보수정validID() throws Exception {

        //given
        UserDTO userDTO = new UserDTO("old", "old@old.old");
        String newName = "new";

        //when
        User saveduser = userService.create(userDTO);
        userService.update(saveduser.getUid(), new UserDTO(newName, userDTO.getEmail()));
        User finduser = userService.readOne(saveduser.getUid());

        //then
        Assertions.assertThat(finduser.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("JPA 유저정보 수정 | 오류id")
    void JPA유저정보수정InvalidID() throws Exception {
        //given

        Long requestID = 99999999L;
        String newName = "new";
        String newEmail = "new@new.new";

        try {
            userService.update(requestID, new UserDTO(newName, newEmail));
        }catch (NoSuchElementException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("No contents");
        }

    }



}