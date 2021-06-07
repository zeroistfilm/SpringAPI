package com.youngdong.woowahan.repositoryTest;

import com.youngdong.woowahan.domain.User;
import com.youngdong.woowahan.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class UserRepositoryTest {

    @Test
    @DisplayName("func 유저객체생성")
    public void createVailidUser(){
        //given
        String email = "zeroistfilm@naver.com";
        String name = "youngdongkim";

        //when
        User user = new User(name, email);

        //then
        Assertions.assertThat(user.getEmail()).isEqualTo(email);
        Assertions.assertThat(user.getName()).isEqualTo(name);
    }


    @Test
    @DisplayName("func 빈 이름 assert")
    public void createInvailidUserNameEmpty(){
        //given
        String email = "zeroistfilm@naver.com";
        String name = "";

        //when
        User user = new User(name, email);
        //then
        try{
            user.isVailid();
            fail();
        }catch (IllegalStateException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("회원 이름 정보가 없습니다");
        }
    }

    @Test
    @DisplayName("func 빈 이름,빈 이메일 assert")
    public void createInvailidUserNameAndEmailEmpty(){
        //given
        String email = "";
        String name = "";

        //when
        User user = new User(name, email);
        //then
        try{
            user.isVailid();
            fail();
        }catch (IllegalStateException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("회원 이름과 이메일 정보가 없습니다");
        }
    }

    @Test
    @DisplayName("func 잘못된이메일 T/F")
    public void checkWorngEmail1(){

        //+ 랜덤으로 앞 뒤 공백 추가
        String[] invaildTestCase = {
                "  @naver.com ",            // ('계정',)없음
                "zeroistfilmnaver.com ",    // ('@',)없음
                "zeroistfilm@.com ",        // ('도메인',)없음
                "zeroistfilm@navercom ",    // ('.',)없음
                "zeroistfilm@naver. ",      // ('최상위 도메인',)없음
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
        for (String casei : invaildTestCase){
            //given
            String email = casei;
            String name = "youngdongkim";

            //when
            User user = new User(name, email);

            //then
            boolean result = user.isVailidEmail(user.getEmail());
            org.junit.jupiter.api.Assertions.assertFalse(result);

        }
    }

    @Test
    @DisplayName("func 잘못된이메일 assert")
    public void invaildemailassert(){
        //given
        //+ 랜덤으로 앞 뒤 공백 추가
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
            //given
            String email = casei;
            String name = "youngdongkim";

            //when
            User user = new User(name, email);

            //then
            try {
                user.isVailid();
                fail();
            } catch (IllegalStateException e) {
                Assertions.assertThat(e.getMessage()).isEqualTo("회원 이메일 정보가 양식에 맞지 않습니다");
            }
        }
    }

    @Test
    @DisplayName("func 올바른이메일체크")
    public void checkCorrectEmail1(){
        //http://www.moakt.com/ko/mail 에서 자동 생성함
        String[] vaildTestCase = {
                "i9ubnomnl@tmail.ws",
                "0batx5iv0@moakt.co",
                "pexvlbqsc@moakt.cc",
                "admwcjy8v@tmails.net",
                "8vwyalicw@disbox.org",
                "bduccnexb@tmpmail.net",
                "uao9b2gzd@moakt.ws",
                "m3chj8o4b@moakt.co",
                "yszghsv5e@tmpmail.net",
                "1g01gvdl9@moakt.cc",
                "fjihbqlef@tmail.ws",
                "lnqsesbrl@tmail.ws"
        };
        for (String casei : vaildTestCase){
            //given
            String email = casei;
            String name = "youngdongkim";

            //when
            User user = new User(name, email);

            //then
            boolean result = user.isVailidEmail(user.getEmail());
            org.junit.jupiter.api.Assertions.assertTrue(result);
        }
    }



}

