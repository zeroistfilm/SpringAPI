package com.youngdong.woowahan.repository;

import com.youngdong.woowahan.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJPAContentsRepository extends JpaRepository<Contents,Long>, ContentsRepository {
}
