package com.youngdong.woowahan.Controller;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.youngdong.woowahan.DTO.BookDTO;
import com.youngdong.woowahan.Entity.Book;
import com.youngdong.woowahan.Service.BookService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.transaction.Transactional;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class BookAPIControllerTest {

    @Autowired
    BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("HTTP 책등록 // 정상")
    public void Createbook() throws Exception {
        String[][] vailidTestCase = {
                {"혼자 공부하는 머신러닝 + 딥러닝 1:1 과외하듯 배우는 인공지능 자습서", "박해선", "한빛미디어 "},
                {"스프링 부트 실전 활용 마스터 스프링 부트 개발과 운영부터 웹플럭스, R소켓", " 그렉 턴키스트", " 책만"},
                {"스프링 마이크로서비스", "라제시 RV", "에이콘출판"},
                {"D3.js 실시간 데이터 시각화 (Node.js 환경에서 실시간 대시보드 만들기)    ", "파블로 나바로 카스틸로", "에이콘출판"}

        };

        for (String[] casei : vailidTestCase) {
            Book book = new Book(casei[0], casei[1], casei[2]);
//            System.out.println(book.getTitle()+book.getAuthor()+ book.getPublisher());
            //then

            JsonObject obj = new JsonObject();
            obj.addProperty("title", book.getTitle());
            obj.addProperty("author", book.getAuthor());
            obj.addProperty("publisher", book.getPublisher());

            mockMvc.perform(post("/book/new")
                    .content(obj.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }
    }

    @Test
    @DisplayName("HTTP 책등록 // 빈값 필터링")
    public void CreatebookEmptyValue() throws Exception {
        String[][] invailidTestCase = {
                {"1", "        ", "     "},
                {"          ", "2", "     "},
                {"          ", "        ", "3"},
                {"1", "1", "     "},
                {"          ", "2", "2"},
                {"3", "        ", "3"},
                {"1", "1", "     "},
                {"          ", "  ", "  "}
        };

        for (String[] casei : invailidTestCase) {
            Book book = new Book(casei[0], casei[1], casei[2]);

            StringBuilder errorMessage = new StringBuilder();
            if (book.getTitle().isEmpty()) {
                errorMessage.append("Title ");
            }
            if (book.getAuthor().isEmpty()) {
                errorMessage.append("Author ");
            }
            if (book.getPublisher().isEmpty()) {
                errorMessage.append("Publisher ");
            }
            if (errorMessage.length() > 0) {
                errorMessage.append("정보가 없습니다");

            }

            JsonObject obj = new JsonObject();
            obj.addProperty("title", book.getTitle());
            obj.addProperty("author", book.getAuthor());
            obj.addProperty("publisher", book.getPublisher());


            mockMvc.perform(post("/book/new")
                    .content(obj.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(status().reason(errorMessage.toString()));
        }
    }

    @Test
    @DisplayName("HTTP 책조회 // 정상")
    public void Readbook() throws Exception {
        //given
        String title = "title";
        String author = "author";
        String publisher = "publisher";

        BookDTO bookDTO = new BookDTO("title", "author", "publisher");

        //when
        Book savebook = bookService.create(bookDTO);

        //then
        MvcResult result = mockMvc.perform(get("/book/").param("id", String.valueOf(savebook.getBid()))
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(content);
        String gettitle = element.getAsJsonObject().get("title").getAsString();
        String getauthor = element.getAsJsonObject().get("author").getAsString();
        String getpublisher = element.getAsJsonObject().get("publisher").getAsString();

        Assertions.assertThat(title).isEqualTo(gettitle);
        Assertions.assertThat(author).isEqualTo(getauthor);
        Assertions.assertThat(publisher).isEqualTo(getpublisher);
    }

    @Test
    @DisplayName("HTTP 책조회 // 비정상")
    public void ReadbookError() throws Exception{

        //then
        MvcResult result = mockMvc.perform(get("/book/").param("id", String.valueOf("999999999999"))
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        String content = result.getResponse().getContentAsString();

    }


    @Test
    @DisplayName("HTTP 모든 책조회")
    public void Readbookall() throws Exception{

        //then
        MvcResult result = mockMvc.perform(get("/book/all")
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();

    }

    @Test
    @DisplayName("HTTP 책조회 페이징")
    public void ReadbookPageing() throws Exception {
        //1. 100개의 유저테이터를 생성
        //2. 총 요청페이지 수, 현재 요청페이지 랜덤으로 생성
        //3. 총 요청페이지 수보다 현재 요청페이지가 크다면 실패
        //4. 점검
        for (int j = 0; j < 5; j++) {
            //given
            Book[] booklist = new Book[100];
            for (int i = 0; i < 100; i++) {
                BookDTO tmp = new BookDTO(String.valueOf(i),String.valueOf(i),String.valueOf(i));
                Book savebook = bookService.create(tmp);
            }

            //when
            Random random = new Random();
            int requestpage = random.nextInt(30);
            int pagesize = random.nextInt(30)+1;

            ResultMatcher result;
            try {
                Page allpages = bookService.readPage(requestpage, pagesize);
                result = status().isOk();

            } catch (Exception e) {
                result = status().isBadRequest();
            }

            //then
            mockMvc.perform(get("/book/allPages")
                    .param("pagesize", String.valueOf(pagesize))
                    .param("requestpage", String.valueOf(requestpage)))
                    .andDo(print())
                    .andExpect(result);
        }
    }

    @Test
    @DisplayName("HTTP 책수정")
    public void Updatebook() throws Exception{


        String newTitle = "newtitle2";
        String newAuthor = "newauthor2";
        String newPublisher = "newpublisher2";

        BookDTO book = new BookDTO("title", "author", "pulbisher");
        Book savebook = bookService.create(book);

        JsonObject obj = new JsonObject();
        obj.addProperty("title", newTitle);
        obj.addProperty("author", newAuthor);
        obj.addProperty("publisher", newPublisher);


        //then
        MvcResult result = mockMvc.perform(put("/book")
                .param("id", String.valueOf(savebook.getBid()))
                .content(obj.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println(result);
        Book book2 = bookService.readOne(savebook.getBid());

        Assertions.assertThat(book2.getTitle()).isEqualTo(newTitle);
        Assertions.assertThat(book2.getAuthor()).isEqualTo(newAuthor);
        Assertions.assertThat(book2.getPublisher()).isEqualTo(newPublisher);

    }


}