package com.youngdong.woowahan.controller;


import com.google.gson.JsonObject;
import com.youngdong.woowahan.domain.Book;
import com.youngdong.woowahan.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@Slf4j
public class BookAPIController {

    private BookService bookService;

    public BookAPIController(BookService bookService) {
        this.bookService = bookService;
    }

    //-----------Create--------
    @PostMapping("/book/new")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String saveBook(@RequestBody Book book) {
        try {
            book.isvailid();
            Long resultId = this.bookService.join(book);
            JsonObject obj = new JsonObject();
            obj.addProperty("bid", resultId);
            obj.addProperty("title", book.getTitle());
            obj.addProperty("author", book.getAuthor());
            obj.addProperty("publisher", book.getPublisher());
            log.info("Success Create Book");
            return obj.toString();

        } catch (Exception e) {
            log.info("Fail Create Book");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()); //400
        }
    }

    //-----------Read----------
    @GetMapping("/book")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Book readBook(@RequestParam("id") Long id) {
        Optional<Book> book = this.bookService.findById(id);
        if (book.isEmpty()) {
            log.info("Fail Read User");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "정보없음");
        }
        log.info("Success Read Book");
        return book.orElse(null);
    }

    @GetMapping("/book/all")
    @ResponseStatus(value = HttpStatus.OK) //200
    public List<Book> readBookAll() {
        List<Book> books = this.bookService.findAll();
        if (books.isEmpty()) {
            log.info("Fail Read User");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "정보없음");
        }
        log.info("Success Read Book");
        return books;
    }

    @GetMapping("/book/allPages")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Page readBookAllpages(@RequestParam("pagesize") int pagesize, @RequestParam("requestpage") int requestpage) {
        Pageable sortedById = PageRequest.of(requestpage, pagesize, Sort.by("uid"));
        Page<Book> allpages = this.bookService.findAll(sortedById);

        if (requestpage > allpages.getTotalPages()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "페이지 범위를 초과하는 요청입니다 MaxPage : " + allpages.getTotalPages(), new IndexOutOfBoundsException());
        }
        if (allpages.isEmpty()) {
            log.info("Fail Read All Users");
        }
        log.info("Success Read All Users");
        return allpages;
    }


    //-----------Update--------
    @PutMapping("/book")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public void editUserInfo(@RequestParam("id") Long id, @RequestBody Book book) {
        book.isvailid();
        Optional<Book> findbook = this.bookService.findById(id);
        findbook.ifPresentOrElse(
                selectbook -> {
                    selectbook.setTitle(book.getTitle());
                    selectbook.setAuthor(book.getAuthor());
                    selectbook.setPublisher(book.getPublisher());
                    bookService.join(selectbook);
                },
                () -> {
                    throw new ResponseStatusException(HttpStatus.NO_CONTENT, "요청한 Book ID가 데이터 베이스에 존재하지 않습니다",
                            new IllegalAccessError());
                });
    }


}
