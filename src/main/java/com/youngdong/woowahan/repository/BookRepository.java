package com.youngdong.woowahan.repository;


import com.youngdong.woowahan.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);
    Optional<Book> findById(Long id);
    Optional<Book> findForPages(Integer page);
    Book updateTitle(Book book, String newtitle);
    Book updateAuthor(Book book, String newauthor);
    Book updatePublisher(Book book, String newpublisher);

    List<Book> findAll();
}
