package com.youngdong.woowahan.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.source.tree.AssertTree;
import com.youngdong.woowahan.domain.Book;
import com.youngdong.woowahan.domain.User;
import com.youngdong.woowahan.repository.BookRepository;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
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
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.transaction.Transactional;

import java.util.Optional;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class BookServiceTest {

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;


    //Create

    @Test
    @DisplayName("책 정보 공백 제거")
    public void stripbook() {
        String[] invailidTestCase = {"  title", "  author", "publisher   "};

        Book book = new Book(invailidTestCase[0], invailidTestCase[1], invailidTestCase[2]);

        Assertions.assertThat(book.getTitle()).isEqualTo("title");
        Assertions.assertThat(book.getAuthor()).isEqualTo("author");
        Assertions.assertThat(book.getPublisher()).isEqualTo("publisher");
    }


    @Test
    @DisplayName("책 정보")
    public void isbookvaild() {
        String[] invailidTestCase = {"    ", "     ", "      "};

        Book book = new Book(invailidTestCase[0], invailidTestCase[1], invailidTestCase[2]);

        try {
            book.isvailid();
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("책 정보가 없습니다");
        }
    }


    @Test
    @DisplayName("HTTP 책등록 // 정상")
    @Commit
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
            mockMvc.perform(post("/book/new")
                    .content(book.toJson())
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

            String reason = "";
            if (book.getTitle().isEmpty() && book.getAuthor().isEmpty() && book.getPublisher().isEmpty()) {
                reason = "책 정보가 없습니다";
            }
            else if (book.getTitle().isEmpty() && book.getAuthor().isEmpty()) {
                reason = "책 제목과 저자 정보가 없습니다";
            }
            else if (book.getAuthor().isEmpty() && book.getPublisher().isEmpty()){
                reason = "책 저자와 출판사 정보가 없습니다";
            }
            else if (book.getTitle().isEmpty() && book.getPublisher().isEmpty()){
                reason = "책 제목과 출판사 정보가 없습니다";
            }
            else if (book.getTitle().isEmpty()) {
                reason = "책 제목 정보가 없습니다";
            }
            else if (book.getAuthor().isEmpty()) {
                reason = "책 저자 정보가 없습니다";
            }
            else if (book.getPublisher().isEmpty()) {
                reason = "책 출판사 정보가 없습니다";
            }

            System.out.println(reason);
            //then

            mockMvc.perform(post("/book/new")
                    .content(book.toJson())
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(status().reason(reason));
        }

    }

    //Read
    @Test
    @DisplayName("HTTP 책조회 // 정상")
    public void Readbook() throws Exception{
        //given
        String title = "title";
        String author = "author";
        String publisher = "publisher";

        Book book = new Book("title", "author", "publisher");

        //when
        Long savebid = bookService.join(book);

        //then
        MvcResult result = mockMvc.perform(get("/book").param("id", String.valueOf(savebid))
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(content);
        String gettitle  = element.getAsJsonObject().get("title").getAsString();
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
        MvcResult result = mockMvc.perform(get("/book").param("id", String.valueOf("999999999999"))
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isNoContent())
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
                Book tmp = new Book(String.valueOf(i),String.valueOf(i),String.valueOf(i));
                tmp.isvailid();
                booklist[i] = tmp;
                Long saveID = bookService.join(tmp);
            }

            //when
            Random random = new Random();
            int requestpage = random.nextInt(30);
            int pagesize = random.nextInt(30)+1;

            Pageable sortedById = PageRequest.of(requestpage, pagesize, Sort.by("bid"));
            Page<Book> allpages = this.bookService.findAll(sortedById);

            ResultMatcher result;
            if (requestpage > allpages.getTotalPages()) {
                result = status().isBadRequest();
            } else {
                result = status().isOk();
            }

            //then
            mockMvc.perform(get("/users/allPages")
                    .param("pagesize", String.valueOf(pagesize))
                    .param("requestpage", String.valueOf(requestpage)))
                    .andDo(print())
                    .andExpect(result);
        }
    }


    //Update
    @Test
    @DisplayName("HTTP 책수정")
    public void Updatebook() throws Exception{

        long requestId = 1;
        String newTitle = "newtitle2";
        String newAuthor = "newauthor2";
        String newPublisher = "newpublisher2";

        JsonObject obj = new JsonObject();
        obj.addProperty("title", newTitle);
        obj.addProperty("author", newAuthor);
        obj.addProperty("publisher", newPublisher);


        //then
        MvcResult result = mockMvc.perform(put("/book")
                .param("id", String.valueOf(requestId))
                .content(obj.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println(result);
        Book book = this.bookService.findById(requestId).get();

        Assertions.assertThat(book.getTitle()).isEqualTo(newTitle);
        Assertions.assertThat(book.getAuthor()).isEqualTo(newAuthor);
        Assertions.assertThat(book.getPublisher()).isEqualTo(newPublisher);

    }

}