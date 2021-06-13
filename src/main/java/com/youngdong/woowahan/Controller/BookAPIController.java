package com.youngdong.woowahan.Controller;

import com.youngdong.woowahan.ServiceInterface.ServiceInterface;
import com.youngdong.woowahan.DTO.BookDTO;
import com.youngdong.woowahan.Entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
public class BookAPIController {
    @Autowired
    private ServiceInterface<BookDTO, Book> api;

    @PostMapping("/book/new")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public String createBook(@RequestBody BookDTO BookDTO) {
        try {
            return api.create(BookDTO).toJson();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/book")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Book getBook(@RequestParam("id") Long id) {
        try {
            return api.readOne(id);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

    }

    @GetMapping("/book/all")
    @ResponseStatus(value = HttpStatus.OK) //200
    public List getAllBook() {
        try {
            return api.readAll();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }
    }

    @GetMapping("/book/allPages")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Page getAllBookPage(@RequestParam("pagesize") int pagesize, @RequestParam("requestpage") int requestpage) {
        try {
            return api.readPage(requestpage, pagesize);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @PutMapping("/book")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public void editBookInfo(@RequestParam("id") Long id, @RequestBody BookDTO BookDTO) {

        try {
            api.update(id, BookDTO);
        }catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

    }

}
