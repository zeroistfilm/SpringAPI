package com.youngdong.woowahan.Repository;

import com.youngdong.woowahan.Entity.Contents;
import com.youngdong.woowahan.RepositoryInterface.RepositoryInterface;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJPAContentsRepositoryInterface extends JpaRepository<Contents,Long>, RepositoryInterface<Contents> {
}
