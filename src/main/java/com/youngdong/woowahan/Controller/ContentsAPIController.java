package com.youngdong.woowahan.Controller;

import com.youngdong.woowahan.ServiceInterface.ServiceInterface;
import com.youngdong.woowahan.DTO.ContentsDTO;
import com.youngdong.woowahan.Entity.Contents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
public class ContentsAPIController {
    @Autowired
    private ServiceInterface<ContentsDTO, Contents> api;

    @PostMapping("/contents/new")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public Contents createcontents(@RequestBody ContentsDTO contentsDTO) {
        try {
            return api.create(contentsDTO);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/contents")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Contents getcontents(@RequestParam("id") Long id) {
        try {
            return api.readOne(id);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

    }

    @GetMapping("/contents/all")
    @ResponseStatus(value = HttpStatus.OK) //200
    public List getAllcontentss() {
        try {
            return api.readAll();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }
    }

    @GetMapping("/contents/allPages")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Page getAllcontentssPage(@RequestParam("pagesize") int pagesize, @RequestParam("requestpage") int requestpage) {
        try {
            return api.readPage(requestpage, pagesize);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @PutMapping("/contents")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public void editcontentsInfo(@RequestParam("id") Long id, @RequestBody ContentsDTO contentsDTO) {
        try {
            api.update(id, contentsDTO);
        }catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }
    }

}
