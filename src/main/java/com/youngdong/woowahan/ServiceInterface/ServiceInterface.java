package com.youngdong.woowahan.ServiceInterface;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ServiceInterface<DTO,Entity> {
    Entity create(DTO dto);
    Entity readOne(long id);
    List<Entity> readAll();
    Page readPage(int requestpage, int pagesize);
    void update(long id,DTO dto);
    void isVaild(DTO dto);
}
