package com.youngdong.woowahan.repository;

import com.youngdong.woowahan.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJPABookRepository extends JpaRepository<Book,Long>, APIRepository<Book>{

}
