package com.youngdong.woowahan.service;

import com.youngdong.woowahan.CRUDInterface.APIInterface;
import com.youngdong.woowahan.DTO.ContentsDTO;
import com.youngdong.woowahan.Entity.Contents;
import com.youngdong.woowahan.Entity.User;
import com.youngdong.woowahan.repository.APIRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContentsService implements APIInterface<ContentsDTO, Contents> {

    @Autowired
    private APIRepository<Contents> contentsRepository;

    @Override
    public Contents create(ContentsDTO contentsDTO) {
        isVaild(contentsDTO);
        Contents contents = new Contents(contentsDTO.getUid(), contentsDTO.getBid(), contentsDTO.getPage(), contentsDTO.getContents());
        return contentsRepository.save(contents);
    }

    @Override
    public Contents readOne(long id) {
        Optional<Contents> contentsbyId = contentsRepository.findById(id);
        if (contentsbyId.isPresent()) {
            log.info("Success Read contents");
            return contentsbyId.orElse(null);
        } else {
            log.info("Fail Read User");
            throw new IllegalStateException("No content");
        }

    }

    @Override
    public List<Contents> readAll() {
        List<Contents> contentsAll = contentsRepository.findAll();
        if (!contentsAll.isEmpty()) {
            log.info("Success Read All contents");
            return contentsAll;
        } else {
            log.info("Fail Read All contents");
            throw new IllegalStateException("No content");
        }
    }

    @Override
    public Page readPage(int requestpage, int pagesize) {
        Pageable sortedById = PageRequest.of(requestpage, pagesize, Sort.by("cid"));
        Page<Contents> allpages = contentsRepository.findAll(sortedById);

        if (requestpage > allpages.getTotalPages()) {
            log.info("Out of page");
            throw new IllegalStateException("out of page, MaxPage : " + allpages.getTotalPages());
        } else {
            log.info("Success read all contents");
            return allpages;
        }

    }

    @Override
    public void update(long id, ContentsDTO contentsDTO) {
        isVaild(contentsDTO);
        Optional<Contents> userbyId = contentsRepository.findById(id);
        userbyId.ifPresentOrElse(
                selectContents -> {
                    selectContents.setUid(contentsDTO.getUid());
                    selectContents.setBid(contentsDTO.getBid());
                    selectContents.setPage(contentsDTO.getPage());
                    selectContents.setContents(contentsDTO.getContents());

                    contentsRepository.save(selectContents);
                    log.info("Success to update with new data");
                },
                () -> {
                    log.info("Fail to update");
                    throw new IllegalStateException("No contents");
                });

    }

    @Override
    public void isVaild(ContentsDTO contentsDTO) {

        StringBuilder errorMessage = new StringBuilder();

        if (contentsDTO.getUid()==null) {
            errorMessage.append("Uid ");
        }

        if (contentsDTO.getBid()==null) {
            errorMessage.append("Bid ");
        }
        if (contentsDTO.getPage()==null) {
            errorMessage.append("Page ");
        }
        if (contentsDTO.getContents().isEmpty()) {
            errorMessage.append("Contents ");
        }


        if (errorMessage.length() > 0) {
            errorMessage.append("정보가 없습니다");
            throw new IllegalStateException(String.valueOf(errorMessage).strip());
        }
    }


}
