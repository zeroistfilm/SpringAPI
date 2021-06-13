package com.youngdong.woowahan.Repository;

import com.youngdong.woowahan.Entity.Book;
import com.youngdong.woowahan.RepositoryInterface.RepositoryInterface;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJPABookRepositoryInterface extends JpaRepository<Book,Long>, RepositoryInterface<Book> {

}
