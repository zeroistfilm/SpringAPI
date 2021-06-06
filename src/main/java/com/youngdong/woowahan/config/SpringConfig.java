package com.youngdong.woowahan.config;

import com.youngdong.woowahan.repository.BookRepository;
import com.youngdong.woowahan.repository.UserRepository;
import com.youngdong.woowahan.service.BookService;
import com.youngdong.woowahan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public SpringConfig(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Autowired

    @Bean
    public BookService bookService(){
        return new BookService(bookRepository);
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository);
    }

}
