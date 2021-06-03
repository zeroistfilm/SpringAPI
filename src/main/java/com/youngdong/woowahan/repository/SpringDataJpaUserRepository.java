package com.youngdong.woowahan.repository;

import com.youngdong.woowahan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaUserRepository extends JpaRepository<User,Long>, UserRepository{
//                                                                      -----data type of primary key

    @Override
    Optional<User> findById(Long id);

    @Override
    User save(User user);

    @Override
    List<User> findAll();
}
