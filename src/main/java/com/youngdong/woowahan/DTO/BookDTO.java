package com.youngdong.woowahan.DTO;



public class BookDTO {

    private String title;

    private String author;

    private String publisher;

    public BookDTO(String title, String author, String publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
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
}
