package com.youngdong.woowahan.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.youngdong.woowahan.domain.Book;
import com.youngdong.woowahan.domain.Contents;
import com.youngdong.woowahan.domain.User;
import com.youngdong.woowahan.repository.ContentsRepository;
import com.youngdong.woowahan.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ContentsServiceTest {

    @Autowired
    ContentsService contentsService;

    @Autowired
    ContentsRepository contentsRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("func 콘텐츠 정보 없음")
    public void iscontentsvaild() {
        Contents contents1 = new Contents( null, null,null,"");
        try {
            contents1.isVaild();
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Uid Bid Page Contents 정보가 없습니다");
        }

        Contents contents2 = new Contents(1L, null,null,"");
        try {
            contents2.isVaild();
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Bid Page Contents 정보가 없습니다");
        }

        Contents contents3 = new Contents( null, 1L,null,"");
        try {
            contents3.isVaild();
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Uid Page Contents 정보가 없습니다");
        }

        Contents contents4 = new Contents( null, null,1,"");
        try {
            contents4.isVaild();
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Uid Bid Contents 정보가 없습니다");
        }
        Contents contents5 = new Contents( null, null,null,"aaaaaaa");
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
        MvcResult userresult =mockMvc.perform(post("/user/new")
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
        MvcResult result =mockMvc.perform(post("/book/new")
                .content(objbook.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String bookInfo = result.getResponse().getContentAsString();



        JsonParser parser1 = new JsonParser();
        JsonElement element1 = parser1.parse(userInfo);
        String uid  = element1.getAsJsonObject().get("uid").getAsString();

        JsonParser parser2 = new JsonParser();
        JsonElement element2 = parser2.parse(bookInfo);
        String bid  = element2.getAsJsonObject().get("bid").getAsString();



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
    @DisplayName("콘텐츠 조회")
    public void readcontents(){
    }

    @Test
    @DisplayName("모든 콘텐츠 조회")
    public void readAllcontents(){
    }

    @Test
    @DisplayName("콘텐츠 페이징조회")
    public void readcontentsPaging(){
    }

    //update

    @Test
    @DisplayName("콘텐츠 수정")
    public void updateContents(){
    }





}
