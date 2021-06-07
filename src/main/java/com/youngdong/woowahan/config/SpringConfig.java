package com.youngdong.woowahan.config;

import com.youngdong.woowahan.repository.BookRepository;
import com.youngdong.woowahan.repository.ContentsRepository;
import com.youngdong.woowahan.repository.UserRepository;
import com.youngdong.woowahan.service.BookService;
import com.youngdong.woowahan.service.ContentsService;
import com.youngdong.woowahan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ContentsRepository contentsRepository;

    @Autowired
    public SpringConfig(UserRepository userRepository, BookRepository bookRepository, ContentsRepository contentsRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.contentsRepository = contentsRepository;
    }


    @Bean
    public BookService bookService() {
        return new BookService(bookRepository);
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    public ContentsService contentsService() {
        return new ContentsService(contentsRepository);
    }

}
