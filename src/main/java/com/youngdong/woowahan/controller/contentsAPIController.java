package com.youngdong.woowahan.controller;

import com.google.gson.JsonObject;
import com.youngdong.woowahan.domain.Contents;
import com.youngdong.woowahan.service.ContentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
public class contentsAPIController {

    private ContentsService contentsService;

    public contentsAPIController(ContentsService contentsService) {
        this.contentsService = contentsService;
    }

    @PostMapping("contents/new")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String saveContents(@RequestBody Contents contents){
            try {
                contents.isVaild();
                Long resultId = this.contentsService.join(contents);
                JsonObject obj = new JsonObject();
                obj.addProperty("bid", resultId);
                obj.addProperty("uid", contents.getUid());
                obj.addProperty("bid", contents.getBid());
                obj.addProperty("page", contents.getPage());
                obj.addProperty("contents", contents.getContents());
                log.info("Success Create Contents");
                return obj.toString();

            } catch (Exception e) {
                log.info("Fail Create Contents");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage()); //400
            }
    }
}
