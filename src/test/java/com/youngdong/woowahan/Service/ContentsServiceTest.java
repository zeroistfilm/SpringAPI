package com.youngdong.woowahan.Service;

import com.youngdong.woowahan.DTO.BookDTO;
import com.youngdong.woowahan.DTO.ContentsDTO;
import com.youngdong.woowahan.DTO.UserDTO;
import com.youngdong.woowahan.Entity.Book;
import com.youngdong.woowahan.Entity.Contents;
import com.youngdong.woowahan.Entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@SpringBootTest
class ContentsServiceTest {

    @Autowired
    ContentsService contentsService;
    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

    @Test
    @DisplayName("JPA 콘텐츠 등록 및 조회")
    void contentsInsert() {
        //given
        UserDTO userDTO = new UserDTO("youngdong", "zeroistfilm@naver.com");
        BookDTO bookDTO = new BookDTO("title", "author", "publusher");

        User saveuser = userService.create(userDTO);
        Book savebook = bookService.create(bookDTO);

        ContentsDTO contentsDTO = new ContentsDTO(saveuser.getUid(), savebook.getBid(), 1, "contents");

        //when
        Contents savecontents = contentsService.create(contentsDTO);
        //then
        Contents findcontents = contentsService.readOne(savecontents.getCid());
        Assertions.assertThat(savecontents.getCid()).isEqualTo(findcontents.getCid());
    }

    @Test
    @DisplayName("JPA 항목 없음")
    void contentsInsertNotitle() {
        //given
        Object[][] cases = {
                {null, 1L, 1, "contents"},
                {1L, null, 1, "contents"},
                {1L, 1L, null, "contents"},
                {1L, 1L, 1, ""},
                {null, null, 1, "contents"},
                {1L, null, null, "contents"},
                {1L, 1L, null, ""},
                {null, 1L, null, "contents"},
                {null, 1L, 1, ""},
                {1L, null, 1, ""},
                {null, null, null, "contents"},
                {1L, null, null, ""},
                {null, 1L, null, ""},
                {null, null, null, ""},
        };
        for (Object[] aCase : cases) {
            ContentsDTO contentsDTO = new ContentsDTO((Long) aCase[0],(Long) aCase[1],(Integer) aCase[2],(String) aCase[3]);

            StringBuilder errorMessage = new StringBuilder();

            if (contentsDTO.getUid()==null) {
                errorMessage.append("Uid ");
            }

            if (contentsDTO.getBid()==null) {
                errorMessage.append("Bid ");
            }
            if (contentsDTO.getPage()==null) {
                errorMessage.append("Page ");
            }

            if (contentsDTO.getContents().isEmpty()) {
                errorMessage.append("Contents ");
            }


            try {
                //when
                contentsService.create(contentsDTO);
            } catch (IllegalArgumentException e) {
                //then
                Assertions.assertThat(e.getMessage()).isEqualTo(errorMessage+"정보가 없습니다");
            }
        }
    }

    @Test
    @DisplayName("JPA 콘텐츠 수정")
    void JPAcontentsinfoeditvalidID() throws Exception {

        //given
        UserDTO userDTO = new UserDTO("youngdong", "zeroistfilm@naver.com");
        BookDTO bookDTO = new BookDTO("title", "author", "publusher");

        User saveuser = userService.create(userDTO);
        Book savebook = bookService.create(bookDTO);

        ContentsDTO contentsDTO = new ContentsDTO(saveuser.getUid(), savebook.getBid(), 1, "contents");

        int newpage = 22;
        String newcomtents = "newcontents";

        //when
        Contents savedcontents = contentsService.create(contentsDTO);
        Thread.sleep(1000);

        contentsService.update(savedcontents.getCid(), new ContentsDTO(null , null, newpage, newcomtents));

        Contents findcontents = contentsService.readOne(savedcontents.getCid());
        //then
        Assertions.assertThat(findcontents.getPage()).isEqualTo(newpage);
        Assertions.assertThat(findcontents.getContents()).isEqualTo(newcomtents);
    }


    @Test
    @DisplayName("JPA 콘텐츠정보 수정 | 오류id")
    void JPA콘텐츠정보수정InvalidID() throws Exception {
        //given

        Long requestID = 99999999L;

        UserDTO userDTO = new UserDTO("youngdong", "zeroistfilm@naver.com");
        BookDTO bookDTO = new BookDTO("title", "author", "publusher");

        User saveuser = userService.create(userDTO);
        Book savebook = bookService.create(bookDTO);

        int newpage = 22;
        String newcomtents = "newcontents";

        try {
            contentsService.update(requestID, new ContentsDTO(saveuser.getUid() , savebook.getBid(), newpage, newcomtents));
        }catch (NoSuchElementException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("No contents");
        }

    }

}