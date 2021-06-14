package com.youngdong.woowahan.Service;

import com.youngdong.woowahan.DTO.BookDTO;
import com.youngdong.woowahan.Entity.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

@SpringBootTest
class BookServiceTest {

    @Autowired
    BookService bookService;

    @Test
    @DisplayName("JPA 책 데이터 등록 및 조회")
    void BookInsert() {
        //given
        BookDTO bookDTO = new BookDTO("title", "author", "publusher");

        //when
        Book savebook = bookService.create(bookDTO);

        //then
        Book findBook = bookService.readOne(savebook.getBid());
        Assertions.assertThat(savebook.getBid()).isEqualTo(findBook.getBid());
    }

    @Test
    @DisplayName("JPA 항목 없음")
    void bookInsertNotitle() {
        //given
        String[][] cases = {
                {"", "author", "publusher"},
                {"title", "", "publusher"},
                {"title", "author", ""},
                {"", "", "publusher"},
                {"title", "", ""},
                {"", "author", ""},
                {"", "", ""}
        };
        for (String[] aCase : cases) {
            BookDTO bookDTO = new BookDTO(aCase[0], aCase[1], aCase[2]);

            StringBuilder errorMessage = new StringBuilder();

            if (bookDTO.getTitle().isEmpty()) {
                errorMessage.append("Title ");
            }

            if (bookDTO.getAuthor().isEmpty()) {
                errorMessage.append("Author ");
            }

            if (bookDTO.getPublisher().isEmpty()) {
                errorMessage.append("Publisher ");
            }


            try {
                //when
                bookService.create(bookDTO);
            } catch (IllegalArgumentException e) {
                //then
                Assertions.assertThat(e.getMessage()).isEqualTo(errorMessage+"정보가 없습니다");
            }
        }
    }
    @Test
    @DisplayName("JPA 책정보 수정")
    void JPA유저정보수정validID() throws Exception {

        //given
        BookDTO bookDTO = new BookDTO("title", "author", "publusher");
        String newTitle = "newtitle";
        String newAuthor = "newauthor";
        String newpublusher = "newpublusher";


        //when
        Book saveduser = bookService.create(bookDTO);
        Thread.sleep(1000);
        bookService.update(saveduser.getBid(), new BookDTO(newTitle, newAuthor, newpublusher));

        Book findbook = bookService.readOne(saveduser.getBid());
        //then
        Assertions.assertThat(findbook.getTitle()).isEqualTo(newTitle);
        Assertions.assertThat(findbook.getAuthor()).isEqualTo(newAuthor);
        Assertions.assertThat(findbook.getPublisher()).isEqualTo(newpublusher);
    }


    @Test
    @DisplayName("JPA 유저정보 수정 | 오류id")
    void JPA유저정보수정InvalidID() throws Exception {
        //given

        Long requestID = 99999999L;
        String newTitle = "newtitle";
        String newAuthor = "newauthor";
        String newpublusher = "newpublusher";

        try {
            bookService.update(requestID, new BookDTO(newTitle, newAuthor, newpublusher));
        }catch (NoSuchElementException e){
            Assertions.assertThat(e.getMessage()).isEqualTo("No contents");
        }

    }

}
