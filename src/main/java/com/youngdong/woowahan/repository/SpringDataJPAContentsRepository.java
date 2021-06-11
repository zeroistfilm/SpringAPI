package com.youngdong.woowahan.repository;

import com.youngdong.woowahan.Entity.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJPAContentsRepository extends JpaRepository<Contents,Long>, APIRepository<Contents> {
}
