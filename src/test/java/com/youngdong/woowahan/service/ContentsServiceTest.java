package com.youngdong.woowahan.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.youngdong.woowahan.Entity.Book;
import com.youngdong.woowahan.Entity.Contents;
import com.youngdong.woowahan.Entity.User;
import com.youngdong.woowahan.repository.BookRepository;
import com.youngdong.woowahan.repository.ContentsRepository;
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
import org.springframework.test.annotation.Commit;
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
@Commit
public class ContentsServiceTest {


    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    ContentsService contentsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ContentsRepository contentsRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("func 콘텐츠 정보 없음")
    public void iscontentsvaild() {
        Contents contents1 = new Contents(null, null, null, "");
        try {
            contents1.isVaild();
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Uid Bid Page Contents 정보가 없습니다");
        }

        Contents contents2 = new Contents(1L, null, null, "");
        try {
            contents2.isVaild();
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Bid Page Contents 정보가 없습니다");
        }

        Contents contents3 = new Contents(null, 1L, null, "");
        try {
            contents3.isVaild();
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Uid Page Contents 정보가 없습니다");
        }

        Contents contents4 = new Contents(null, null, 1, "");
        try {
            contents4.isVaild();
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Uid Bid Contents 정보가 없습니다");
        }
        Contents contents5 = new Contents(null, null, null, "aaaaaaa");
        try {
            contents5.isVaild();
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Uid Bid Page 정보가 없습니다");
        }

    }


    //Create
    @Test
    @DisplayName("HTTP 콘텐츠 생성 / 유저, 책정보 생성")
    @Commit
    public void createcontents() throws Exception {
        User user = new User("name", "email@naver.com");
        Book book = new Book("title", "author", "publisher");

        //http user create
        JsonObject objuser = new JsonObject();
        objuser.addProperty("name", user.getName());
        objuser.addProperty("email", user.getEmail());
        MvcResult userresult = mockMvc.perform(post("/user/new")
                .content(objuser.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String userInfo = userresult.getResponse().getContentAsString();

        //http book create
        JsonObject objbook = new JsonObject();
        objbook.addProperty("title", book.getTitle());
        objbook.addProperty("author", book.getAuthor());
        objbook.addProperty("publisher", book.getPublisher());
        MvcResult result = mockMvc.perform(post("/book/new")
                .content(objbook.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String bookInfo = result.getResponse().getContentAsString();


        JsonParser parser1 = new JsonParser();
        JsonElement element1 = parser1.parse(userInfo);
        String uid = element1.getAsJsonObject().get("uid").getAsString();

        JsonParser parser2 = new JsonParser();
        JsonElement element2 = parser2.parse(bookInfo);
        String bid = element2.getAsJsonObject().get("bid").getAsString();


        Contents contents = new Contents(Long.parseLong(uid), Long.parseLong(bid), 1, "contents");
        mockMvc.perform(post("/contents/new")
                .content(contents.toJson())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    //read
    @Test
    @DisplayName("HTTP 콘텐츠 조회")
    public void readcontents() throws Exception {


        User user = new User("name", "email@naver.com");
        Book book = new Book("title", "author", "publisher");


        int page = 1;
        String contents = "contents";

        Long saveuid = userService.join(user);
        Long savebid = bookService.join(book);

        Contents contents1 = new Contents(saveuid, savebid, page, contents);

        //when
        Long savecid = contentsService.join(contents1);

        //then
        MvcResult result = mockMvc.perform(get("/contents").param("id", String.valueOf(savecid))
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(content);
        String getuid = element.getAsJsonObject().get("uid").getAsString();
        String getbid = element.getAsJsonObject().get("bid").getAsString();
        String getpage = element.getAsJsonObject().get("page").getAsString();
        String getcontents = element.getAsJsonObject().get("contents").getAsString();

        Assertions.assertThat(String.valueOf(saveuid)).isEqualTo(getuid);
        Assertions.assertThat(String.valueOf(savebid)).isEqualTo(getbid);
        Assertions.assertThat(String.valueOf(page)).isEqualTo(getpage);
        Assertions.assertThat(contents).isEqualTo(getcontents);

    }

    @Test
    @DisplayName("HTTP 모든 콘텐츠 조회")
    public void readAllcontents() throws Exception {
        MvcResult result = mockMvc.perform(get("/contents/all")
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("HTTP 콘텐츠 페이징조회")
    public void readcontentsPaging() throws Exception {
        for (int j = 0; j < 5; j++) {
            //given
            for (int i = 0; i < 100; i++) {
                User user = new User("name", "email@naver.com");
                Book book = new Book("title", "author", "publisher");
                int page = 1;
                String contents = "contents";
                Long saveuid = userService.join(user);
                Long savebid = bookService.join(book);
                Contents contents1 = new Contents(saveuid, savebid, page, contents);
                Long savecid = contentsService.join(contents1);
            }

            //when
            Random random = new Random();
            int requestpage = random.nextInt(30);
            int pagesize = random.nextInt(30) + 1;

            Pageable sortedById = PageRequest.of(requestpage, pagesize, Sort.by("cid"));
            Page<Contents> allpages = this.contentsService.findAll(sortedById);

            ResultMatcher result;
            if (requestpage > allpages.getTotalPages()) {
                result = status().isBadRequest();
            } else {
                result = status().isOk();
            }

            //then
            mockMvc.perform(get("/contents/allPages")
                    .param("pagesize", String.valueOf(pagesize))
                    .param("requestpage", String.valueOf(requestpage)))
                    .andDo(print())
                    .andExpect(result);
        }


    }

    //update

    @Test
    @DisplayName("HTTP 콘텐츠 수정")
    @Commit
    public void updateContents() throws Exception {

        //데이터 입력
        String name = "name";
        String email = "email@naver.com";
        String title = "title";
        String author = "author";
        String publisher = "publisher";
        User user = new User(name, email);
        Book book = new Book(title, author, publisher);
        int page = 1;
        String contents = "contents";
        Long saveuid = userService.join(user);
        Long savebid = bookService.join(book);
        Contents contents1 = new Contents(saveuid, savebid, page, contents);
        Long savecid = contentsService.join(contents1);

        //변경할 데이터
        String newname = "newname";
        String newemail = "newemail@naver.com";
        String newtitle = "newtitle";
        String newauthor = "newauthor";
        String newpublisher = "newpublisher";
        User newuser = new User(newname, newemail);
        Book newbook = new Book(newtitle, newauthor, newpublisher);
        Long newsaveuid = userService.join(newuser);
        Long newsavebid = bookService.join(newbook);
        int newpage = 10;
        String newcontents = "contents";

        JsonObject obj = new JsonObject();
        obj.addProperty("uid", newsaveuid);
        obj.addProperty("bid", newsavebid);
        obj.addProperty("page", newpage);
        obj.addProperty("contents", newcontents);

        Thread.sleep(2000); //1초 대기

        //HTTP요청
        MvcResult result = mockMvc.perform(put("/contents")
                .param("id", String.valueOf(savecid))
                .content(obj.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        // 저장된 데이터가 요청한 데이터와 같은지 확인
        Contents retContents = this.contentsService.findById(savecid).get();
        Assertions.assertThat(Long.toString(retContents.getUid())).isEqualTo(Long.toString(newsaveuid));
        Assertions.assertThat(Long.toString(retContents.getBid())).isEqualTo(Long.toString(newsavebid));
        Assertions.assertThat(String.valueOf(retContents.getPage())).isEqualTo(String.valueOf(newpage));
        Assertions.assertThat(retContents.getContents()).isEqualTo(newcontents);

    }


}
