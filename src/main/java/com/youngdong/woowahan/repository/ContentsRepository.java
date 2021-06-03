package com.youngdong.woowahan.repository;


import com.youngdong.woowahan.domain.Contents;

import java.util.List;
import java.util.Optional;

public interface ContentsRepository {
    Contents save(Contents contents);
    Optional<Contents> findById(Long id);
    Optional<Contents> findForPages(Integer page);
    Contents updatePage(Contents contents, Integer newpage);
    Contents updateContents(Contents contents, String newcontents);
    List<Contents> findAll();
}
