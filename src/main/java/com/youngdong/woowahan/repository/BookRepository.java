package com.youngdong.woowahan.repository;


import com.youngdong.woowahan.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
    Page<Book> findAll(Pageable sortedById);
}
