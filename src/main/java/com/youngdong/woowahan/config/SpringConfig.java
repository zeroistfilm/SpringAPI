package com.youngdong.woowahan.config;

import com.youngdong.woowahan.aop.TimeTraceAop;
import com.youngdong.woowahan.repository.UserRepository;
import com.youngdong.woowahan.repository.jpaUserRepository;
import com.youngdong.woowahan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Basic;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final UserRepository userRepository;

    @Autowired
    public SpringConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository);
    }

//    @Bean
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }
//    @Bean
//    public UserRepository userRepository(){
////        return new jpaUserRepository(em);
//    }



}
