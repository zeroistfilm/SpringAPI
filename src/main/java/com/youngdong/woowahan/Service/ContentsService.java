package com.youngdong.woowahan.Service;

import com.youngdong.woowahan.DTO.ContentsDTO;
import com.youngdong.woowahan.Entity.Contents;
import com.youngdong.woowahan.RepositoryInterface.RepositoryInterface;
import com.youngdong.woowahan.ServiceInterface.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class ContentsService implements ServiceInterface<ContentsDTO, Contents> {

    @Autowired
    private RepositoryInterface<Contents> contentsRepository;

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
            throw new NoSuchElementException("No content");
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
            throw new NoSuchElementException("No content");
        }
    }

    @Override
    public Page readPage(int requestpage, int pagesize) {
        Pageable sortedById = PageRequest.of(requestpage, pagesize, Sort.by("cid"));
        Page<Contents> allpages = contentsRepository.findAll(sortedById);

        if (requestpage > allpages.getTotalPages()) {
            log.info("Out of page");
            throw new IllegalArgumentException("out of page, MaxPage : " + allpages.getTotalPages());
        } else {
            log.info("Success read contents for paging");
            return allpages;
        }

    }

    @Override
    public void update(long id, ContentsDTO contentsDTO) {

        Optional<Contents> contentsbyId = contentsRepository.findById(id);

        int newpage = (contentsDTO.getPage() != null) ? contentsDTO.getPage() : contentsbyId.get().getPage();
        String newcontents = (contentsDTO.getContents() != null) ? contentsDTO.getContents() : contentsbyId.get().getContents();

        contentsbyId.ifPresentOrElse(
                selectContents -> {
                    selectContents.setPage(newpage);
                    selectContents.setContents(newcontents);

                    contentsRepository.save(selectContents);
                    log.info("Success to update with new data");
                },
                () -> {
                    log.info("Fail to update");
                    throw new NoSuchElementException("No contents");
                });

    }

    @Override
    public void isVaild(ContentsDTO contentsDTO) {

        StringBuilder errorMessage = new StringBuilder();

        if (contentsDTO.getUid() == null) {
            errorMessage.append("Uid ");
        }

        if (contentsDTO.getBid() == null) {
            errorMessage.append("Bid ");
        }
        if (contentsDTO.getPage() == null) {
            errorMessage.append("Page ");
        }
        if (contentsDTO.getContents().isEmpty()) {
            errorMessage.append("Contents ");
        }


        if (errorMessage.length() > 0) {
            errorMessage.append("????????? ????????????");
            throw new IllegalArgumentException(String.valueOf(errorMessage).strip());
        }
    }


}
