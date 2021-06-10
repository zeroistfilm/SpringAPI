package com.youngdong.woowahan.controller;

import com.google.gson.JsonObject;
import com.youngdong.woowahan.Entity.Contents;
import com.youngdong.woowahan.service.ContentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class ContentsAPIController {

    private ContentsService contentsService;

    public ContentsAPIController(ContentsService contentsService) {
        this.contentsService = contentsService;
    }

    @PostMapping("contents/new")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String saveContents(@RequestBody Contents contents) {
        try {
            contents.isVaild();
            Long resultId = this.contentsService.join(contents);
            JsonObject obj = new JsonObject();
            obj.addProperty("bid", resultId);
            obj.addProperty("uid", contents.getUid());
            obj.addProperty("bid", contents.getBid());
            obj.addProperty("page", contents.getPage());
            obj.addProperty("contents", contents.getContents());
            log.info("Success create contents");
            return obj.toString();

        } catch (Exception e) {
            log.info("Fail create contents");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()); //400
        }
    }


    //-----------Read--------
    @GetMapping("/contents")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Contents readcontents(@RequestParam("id") Long id) {
        Optional<Contents> contents = this.contentsService.findById(id);
        if (contents.isEmpty()) {
            log.info("Fail read contents");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "정보없음");
        }
        log.info("Success read contents");
        return contents.orElse(null);
    }


    @GetMapping("/contents/all")
    public List getAllUsers() {
        List<Contents> allcontents = this.contentsService.findAll();
        if (allcontents.isEmpty()) {
            log.info("Fail Read contents");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "정보없음");
        }
        log.info("Success Read contents");
        return allcontents;
    }

    @GetMapping("/contents/allPages")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Page readBookAllpages(@RequestParam("pagesize") int pagesize, @RequestParam("requestpage") int requestpage) {
        Pageable sortedById = PageRequest.of(requestpage, pagesize, Sort.by("cid"));
        Page<Contents> allpages = this.contentsService.findAll(sortedById);

        if (requestpage > allpages.getTotalPages()) {
            log.info("Out of page");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "페이지 범위를 초과하는 요청입니다 MaxPage : " + allpages.getTotalPages(), new IndexOutOfBoundsException());
        }
        if (allpages.isEmpty()) {
            log.info("data is empty");
        } else {
            log.info("Success Read All contents");
        }

        return allpages;
    }


    @PutMapping("/contents")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public void editUserInfo(@RequestParam("id") Long id, @RequestBody Contents contents) {
        Optional<Contents> findcontents = this.contentsService.findById(id);
        findcontents.ifPresentOrElse(
                selectContents -> {
                    selectContents.setUid(contents.getUid());
                    selectContents.setBid(contents.getBid());
                    selectContents.setPage(contents.getPage());
                    selectContents.setContents(contents.getContents());
                    contentsService.join(selectContents);
                    log.info("Success to update with new data");
                },
                () -> {
                    log.info("Fail to update");
                    throw new ResponseStatusException(HttpStatus.NO_CONTENT, "요청한 Contents ID가 데이터 베이스에 존재하지 않습니다",
                            new IllegalAccessError());
//                    IllegalStateException("can't find user in database");
                });
    }

}
