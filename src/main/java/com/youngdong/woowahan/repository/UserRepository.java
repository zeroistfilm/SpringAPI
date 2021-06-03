package com.youngdong.woowahan.repository;

import com.youngdong.woowahan.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
}
