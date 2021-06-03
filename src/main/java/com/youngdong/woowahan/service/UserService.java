package com.youngdong.woowahan.service;


import com.youngdong.woowahan.domain.User;
import com.youngdong.woowahan.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class UserService {
  private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){

        try{
            user.isVailid();
        }catch (IllegalStateException e){
            //api throw 구현해야함
            System.out.println(e.getMessage());
        }

        userRepository.save(user);
        return user;
    }

    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userID){
        return userRepository.findById(userID);
    }

}
