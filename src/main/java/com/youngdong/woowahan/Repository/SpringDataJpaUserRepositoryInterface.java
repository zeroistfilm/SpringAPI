package com.youngdong.woowahan.Repository;

import com.youngdong.woowahan.Entity.User;
import com.youngdong.woowahan.RepositoryInterface.RepositoryInterface;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaUserRepositoryInterface extends JpaRepository<User,Long>, RepositoryInterface<User> {

}
