package com.youngdong.woowahan.repository;


import com.youngdong.woowahan.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
}
