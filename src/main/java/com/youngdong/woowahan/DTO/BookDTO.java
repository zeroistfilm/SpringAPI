package com.youngdong.woowahan.DTO;
import lombok.Getter;

@Getter
public class BookDTO {

    private String title;
    private String author;
    private String publisher;

    public BookDTO(String title, String author, String publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }
}
