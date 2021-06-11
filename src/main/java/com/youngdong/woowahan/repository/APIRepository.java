package com.youngdong.woowahan.repository;

import com.youngdong.woowahan.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface APIRepository<Entity> {
    Entity save(Entity entity);
    Optional<Entity> findById(Long id);
    List<Entity> findAll();
    Page<Entity> findAll(Pageable sortedById);

}
