package com.youngdong.woowahan.Controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.youngdong.woowahan.DTO.BookDTO;
import com.youngdong.woowahan.DTO.ContentsDTO;
import com.youngdong.woowahan.DTO.UserDTO;
import com.youngdong.woowahan.Entity.Book;
import com.youngdong.woowahan.Entity.Contents;
import com.youngdong.woowahan.Entity.User;
import com.youngdong.woowahan.Service.BookService;
import com.youngdong.woowahan.Service.ContentsService;
import com.youngdong.woowahan.Service.UserService;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ContentsAPIControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    ContentsService contentsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("HTTP 콘텐츠 생성 / 유저, 책정보 생성")

    public void createcontents() throws Exception {
        UserDTO user = new UserDTO("name", "email@naver.com");
        BookDTO book = new BookDTO("title", "author", "publisher");

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

        JsonObject obj = new JsonObject();
        obj.addProperty("uid", contents.getUid());
        obj.addProperty("bid", contents.getBid());
        obj.addProperty("page", contents.getPage());
        obj.addProperty("contents", contents.getContents());

        mockMvc.perform(post("/contents/new")
                .content(String.valueOf(obj))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    @DisplayName("HTTP 콘텐츠 조회")
    public void readcontents() throws Exception {


        UserDTO user = new UserDTO("name", "email@naver.com");
        BookDTO book = new BookDTO("title", "author", "publisher");


        int page = 1;
        String contents = "contents";

        User saveuser = userService.create(user);
        Book savebook = bookService.create(book);

        ContentsDTO contents1 = new ContentsDTO(saveuser.getUid(), savebook.getBid(), page, contents);

        //when
        Contents savecontents = contentsService.create(contents1);

        //then
        MvcResult result = mockMvc.perform(get("/contents/").param("id", String.valueOf(savecontents.getCid()))
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

        Assertions.assertThat(String.valueOf(saveuser.getUid())).isEqualTo(getuid);
        Assertions.assertThat(String.valueOf(savebook.getBid())).isEqualTo(getbid);
        Assertions.assertThat(String.valueOf(page)).isEqualTo(getpage);
        Assertions.assertThat(contents).isEqualTo(getcontents);

    }

    @Test
    @DisplayName("HTTP 모든 콘텐츠 조회")
    public void readAllcontents() throws Exception {
        UserDTO user = new UserDTO("name", "email@naver.com");
        BookDTO book = new BookDTO("title", "author", "publisher");

        int page = 1;
        String contents = "contents";

        User saveuser = userService.create(user);
        Book savebook = bookService.create(book);

        ContentsDTO contents1 = new ContentsDTO(saveuser.getUid(), savebook.getBid(), page, contents);
        contentsService.create(contents1);
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
                UserDTO user = new UserDTO("name", "email@naver.com");
                BookDTO book = new BookDTO("title", "author", "publisher");
                int page = 1;
                String contents = "contents";
                User saveuser = userService.create(user);
                Book savebook = bookService.create(book);
                ContentsDTO contents1 = new ContentsDTO(saveuser.getUid(), savebook.getBid(), page, contents);
                Contents savecontents = contentsService.create(contents1);
            }

            //when
            Random random = new Random();
            int requestpage = random.nextInt(30);
            int pagesize = random.nextInt(30) + 1;

            System.out.println("yd test " + String.valueOf(pagesize) + " " + String.valueOf(requestpage));

            ResultMatcher result;
            try {
                Page allpages = contentsService.readPage(requestpage, pagesize);
                result = status().isOk();

            } catch (Exception e) {
                result = status().isBadRequest();
            }


            //then
            mockMvc.perform(get("/contents/allPages")
                    .param("pagesize", String.valueOf(pagesize))
                    .param("requestpage", String.valueOf(requestpage)))
                    .andDo(print())
                    .andExpect(result);
        }


    }

    @Test
    @DisplayName("HTTP 콘텐츠 ,페이지 수정")
    public void updateContents() throws Exception {

        //데이터 입력
        String name = "name";
        String email = "email@naver.com";
        String title = "title";
        String author = "author";
        String publisher = "publisher";
        UserDTO user = new UserDTO(name, email);
        BookDTO book = new BookDTO(title, author, publisher);
        int page = 1;
        String contents = "contents";
        User saveuser = userService.create(user);
        Book savebook = bookService.create(book);
        ContentsDTO contents1 = new ContentsDTO(saveuser.getUid(), savebook.getBid(), page, contents);
        Contents savecontents = contentsService.create(contents1);

        //변경할 데이터
        String newname = "newname";
        String newemail = "newemail@naver.com";
        String newtitle = "newtitle";
        String newauthor = "newauthor";
        String newpublisher = "newpublisher";
        UserDTO newuser = new UserDTO(newname, newemail);
        BookDTO newbook = new BookDTO(newtitle, newauthor, newpublisher);
        User newsaveuser = userService.create(newuser);
        Book newsavebook = bookService.create(newbook);
        int newpage = 10;
        String newcontents = "contents";

        JsonObject obj = new JsonObject();
        obj.addProperty("page", newpage);
        obj.addProperty("contents", newcontents);

        Thread.sleep(2000); //1초 대기

        //HTTP요청
        MvcResult result = mockMvc.perform(put("/contents")
                .param("id", String.valueOf(savecontents.getCid()))
                .content(obj.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        // 저장된 데이터가 요청한 데이터와 같은지 확인
        Contents retContents = contentsService.readOne(savecontents.getCid());
        Assertions.assertThat(String.valueOf(retContents.getPage())).isEqualTo(String.valueOf(newpage));
        Assertions.assertThat(retContents.getContents()).isEqualTo(newcontents);

    }


    @Test
    @DisplayName("HTTP 콘텐츠만 수정")
    public void updateContentsOnlycontents() throws Exception {

        //데이터 입력
        String name = "name";
        String email = "email@naver.com";
        String title = "title";
        String author = "author";
        String publisher = "publisher";
        UserDTO user = new UserDTO(name, email);
        BookDTO book = new BookDTO(title, author, publisher);
        int page = 1;
        String contents = "contents";
        User saveuser = userService.create(user);
        Book savebook = bookService.create(book);
        ContentsDTO contents1 = new ContentsDTO(saveuser.getUid(), savebook.getBid(), page, contents);
        Contents savecontents = contentsService.create(contents1);

        //변경할 데이터
        String newname = "newname";
        String newemail = "newemail@naver.com";
        String newtitle = "newtitle";
        String newauthor = "newauthor";
        String newpublisher = "newpublisher";
        UserDTO newuser = new UserDTO(newname, newemail);
        BookDTO newbook = new BookDTO(newtitle, newauthor, newpublisher);
        User newsaveuser = userService.create(newuser);
        Book newsavebook = bookService.create(newbook);
        int newpage = 10;
        String newcontents = "contents";

        JsonObject obj = new JsonObject();
//        obj.addProperty("page", newpage);
        obj.addProperty("contents", newcontents);

        Thread.sleep(2000); //1초 대기

        //HTTP요청
        MvcResult result = mockMvc.perform(put("/contents")
                .param("id", String.valueOf(savecontents.getCid()))
                .content(obj.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        // 저장된 데이터가 요청한 데이터와 같은지 확인
        Contents retContents = contentsService.readOne(savecontents.getCid());

        Assertions.assertThat(String.valueOf(retContents.getPage())).isEqualTo(String.valueOf(page));
        Assertions.assertThat(retContents.getContents()).isEqualTo(newcontents);

    }


}