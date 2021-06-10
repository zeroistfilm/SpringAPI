package com.youngdong.woowahan.repository;

import com.youngdong.woowahan.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaUserRepository extends JpaRepository<User,Long>, UserRepository{
//                                                                      -----data type of primary key


    // JPQL select u from user u where u.name = ?
    @Override
    Optional<User> findByName(String name);

    // JPQL select u from user u where u.email = ?
    @Override
    Optional<User> findByEmail(String email);


    //페이징기능 search keyword : spring data jpa paging
    //Page<User> users = UserRepository.findAll(new PageRequest(1, 20));

    // 이런식으로 구문에 맞는 인터페이스를 작성하면 알아서 쿼리를 만들어 준다.
    // JPQL select u from user u where u.name = ? and where u.email = ?
    //@Override
    //Optional<User> findByNameAndEmail(String name,String email);
}
