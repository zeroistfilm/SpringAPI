package com.youngdong.woowahan.service;

import com.youngdong.woowahan.CRUDInterface.APIInterface;
import com.youngdong.woowahan.DTO.BookDTO;
import com.youngdong.woowahan.Entity.Book;
import com.youngdong.woowahan.repository.APIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookService implements APIInterface<BookDTO, Book> {

    @Autowired
    private APIRepository<Book> bookRepository;


    @Override
    public Book create(BookDTO bookDTO) {
        isVaild(bookDTO);
        Book book = new Book(bookDTO.getTitle(),bookDTO.getAuthor(), bookDTO.getPublisher());
        return bookRepository.save(book);
    }

    @Override
    public Book readOne(long id) {
        Optional<Book> bookbyId = bookRepository.findById(id);
        if (bookbyId.isPresent()) {
            log.info("Success Read book");
            return bookbyId.orElse(null);
        } else {
            log.info("Fail Read book");
            throw new IllegalStateException("No content");
        }

    }

    @Override
    public List<Book> readAll() {
        List<Book> bookAll = bookRepository.findAll();
        if (!bookAll.isEmpty()) {
            log.info("Success Read All books");
            return bookAll;
        } else {
            log.info("Fail Read All books");
            throw new IllegalStateException("No content");
        }

    }

    @Override
    public Page readPage(int requestpage, int pagesize) {
        Pageable sortedById = PageRequest.of(requestpage, pagesize, Sort.by("bid"));
        Page<Book> allpages = bookRepository.findAll(sortedById);

        if (requestpage > allpages.getTotalPages()) {
            log.info("Out of page");
            throw new IllegalStateException("out of page, MaxPage : " + allpages.getTotalPages());
        } else {
            log.info("Success read all books");
            return allpages;
        }

    }


    @Override
    public void update(long id, BookDTO bookDTO) {
        Optional<Book> bookById = bookRepository.findById(id);
        bookById.ifPresentOrElse(
                selectBook -> {
                    selectBook.setTitle(bookDTO.getTitle());
                    selectBook.setAuthor(bookDTO.getAuthor());
                    selectBook.setPublisher(bookDTO.getPublisher());
                    bookRepository.save(selectBook);
                    log.info("Success to update with new data");
                },
                () -> {
                    log.info("Fail to update");
                    throw new IllegalStateException("No contents");
                });
    }

    @Override
    public void isVaild(BookDTO bookDTO) {
        StringBuilder errorMessage = new StringBuilder();

        if (bookDTO.getTitle().isEmpty()) {
            errorMessage.append("Title ");
        }

        if (bookDTO.getAuthor().isEmpty()) {
            errorMessage.append("Author ");
        }

        if (bookDTO.getPublisher().isEmpty()) {
            errorMessage.append("Publisher ");
        }

        if (errorMessage.length() > 0) {
            errorMessage.append("정보가 없습니다");
            throw new IllegalStateException(String.valueOf(errorMessage).strip());
        }
    }


}
