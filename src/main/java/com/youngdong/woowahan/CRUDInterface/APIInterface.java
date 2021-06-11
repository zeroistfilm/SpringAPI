package com.youngdong.woowahan.CRUDInterface;

import org.springframework.data.domain.Page;

import java.util.List;

public interface APIInterface<DTO,Entity> {
    Entity create(DTO dto);
    Entity readOne(long id);
    List<Entity> readAll();
    Page readPage(int requestpage, int pagesize);
    void update(long id,DTO dto);
    void isVaild(DTO dto);
}
