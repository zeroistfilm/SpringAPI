package com.youngdong.woowahan.repository;


import com.youngdong.woowahan.domain.Contents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ContentsRepository {
    Contents save(Contents contents);
    Optional<Contents> findById(Long id);
    List<Contents> findAll();
    Page<Contents> findAll(Pageable sortedById);
}
