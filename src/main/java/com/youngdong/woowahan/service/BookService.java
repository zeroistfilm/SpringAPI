package com.youngdong.woowahan.service;

import com.youngdong.woowahan.Entity.Book;
import com.youngdong.woowahan.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Long join(Book book) {
        bookRepository.save(book);
        return book.getBid();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }


    public Page<Book> findAll(Pageable sortedById) {
        return bookRepository.findAll(sortedById);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
