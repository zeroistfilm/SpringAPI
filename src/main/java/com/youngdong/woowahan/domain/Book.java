package com.youngdong.woowahan.domain;

//    BID       int          not null auto_increment primary key,
//    Title     varchar(100) not null,
//    Author    varchar(40)  not null,
//    Publisher varchar(40)  not null,
public class Book {
    private Long bid;
    private String title;
    private String author;
    private String publisher;

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
