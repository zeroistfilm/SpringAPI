package com.youngdong.woowahan.domain;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

//    BID       int          not null auto_increment primary key,
//    Title     varchar(100) not null,
//    Author    varchar(40)  not null,
//    Publisher varchar(40)  not null,
@Entity
@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BID")
    private Long bid;

    @Column(name = "Title")
    private String title;

    @Column(name = "Author")
    private String author;

    @Column(name = "Publisher")
    private String publisher;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Book(String title, String author, String publisher) {
        this.title = title.strip();
        this.author = author.strip();
        this.publisher = publisher.strip();
    }

    public void isVailid() {

        if (this.title.isEmpty() && this.author.isEmpty() && this.publisher.isEmpty()){
            throw new IllegalStateException("책 정보가 없습니다");
        }
        else if (this.title.isEmpty() && this.author.isEmpty()) {
            throw new IllegalStateException("책 제목과 저자 정보가 없습니다");
        }
        else if (this.author.isEmpty() && this.publisher.isEmpty()){
            throw new IllegalStateException("책 저자와 출판사 정보가 없습니다");
        }
        else if (this.title.isEmpty() && this.publisher.isEmpty()){
            throw new IllegalStateException("책 제목과 출판사 정보가 없습니다");
        }
        else if (this.title.isEmpty()){
            throw new IllegalStateException("책 제목 정보가 없습니다");
        }
        else if (this.author.isEmpty()){
            throw new IllegalStateException("책 저자 정보가 없습니다");
        }
        else if (this.publisher.isEmpty()){
            throw new IllegalStateException("책 출판사 정보가 없습니다");
        }
    }
    public String getTitle() {
        return title;
    }


    public String getAuthor() {
        return author;
    }


    public String getPublisher() {
        return publisher;
    }


    public String toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("title", this.title);
        obj.addProperty("author", this.author);
        obj.addProperty("publisher", this.publisher);
        obj.addProperty("createdAt", String.valueOf(this.createdDate));
        obj.addProperty("updateAt", String.valueOf(this.updatedDate));
        return String.valueOf(obj);
    }
}
