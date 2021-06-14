package com.youngdong.woowahan.Controller;

import com.youngdong.woowahan.DTO.BookDTO;
import com.youngdong.woowahan.Entity.Book;
import com.youngdong.woowahan.ServiceInterface.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class BookAPIController {
    @Autowired
    private ServiceInterface<BookDTO, Book> api;

    @PostMapping("/book/new")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public Book createBook(@RequestBody BookDTO BookDTO) {
        return api.create(BookDTO);
    }

    @GetMapping("/book")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Book getBook(@RequestParam("id") Long id) {
        return api.readOne(id);
    }

    @GetMapping("/book/all")
    @ResponseStatus(value = HttpStatus.OK) //200
    public List getAllBook() {
        return api.readAll();
    }

    @GetMapping("/book/allPages")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Page getAllBookPage(@RequestParam("pagesize") int pagesize, @RequestParam("requestpage") int requestpage) {
        return api.readPage(requestpage, pagesize);
    }

    @PutMapping("/book")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public void editBookInfo(@RequestParam("id") Long id, @RequestBody BookDTO BookDTO) {
        api.update(id, BookDTO);
    }

}
