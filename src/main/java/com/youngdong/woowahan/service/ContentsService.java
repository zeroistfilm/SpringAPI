package com.youngdong.woowahan.service;

import com.youngdong.woowahan.Entity.Contents;
import com.youngdong.woowahan.repository.ContentsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class ContentsService {
    private ContentsRepository contentsRepository;

    public ContentsService(ContentsRepository contentsRepository) {
        this.contentsRepository = contentsRepository;
    }

    public Long join(Contents contents) {
        contentsRepository.save(contents);
        return contents.getCid();
    }

    public Optional<Contents> findById(Long id) {
        return contentsRepository.findById(id);
    }

    public List<Contents> findAll() {
        return contentsRepository.findAll();
    }

    public Page<Contents> findAll(Pageable sortedById) {
        return contentsRepository.findAll(sortedById);
    }
}
