package com.youngdong.woowahan.service;


import com.youngdong.woowahan.domain.User;
import com.youngdong.woowahan.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long join(User user) {
        user.isVailid();
        userRepository.save(user);
        return user.getUid();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(Long userID) {
        return userRepository.findById(userID);
    }

    public Page<User> findAll(Pageable sortedById) {
        return userRepository.findAll(sortedById);
    }
}
