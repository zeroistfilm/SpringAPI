package com.youngdong.woowahan.repository;

import com.youngdong.woowahan.domain.Book;
import com.youngdong.woowahan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJPABookRepository extends JpaRepository<Book,Long>, BookRepository{

}
