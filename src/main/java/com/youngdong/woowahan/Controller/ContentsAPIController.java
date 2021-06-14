package com.youngdong.woowahan.Controller;

import com.youngdong.woowahan.DTO.ContentsDTO;
import com.youngdong.woowahan.Entity.Contents;
import com.youngdong.woowahan.ServiceInterface.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ContentsAPIController {
    @Autowired
    private ServiceInterface<ContentsDTO, Contents> api;

    @PostMapping("/contents/new")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public Contents createcontents(@RequestBody ContentsDTO contentsDTO) {
        return api.create(contentsDTO);
    }

    @GetMapping("/contents")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Contents getcontents(@RequestParam("id") Long id) {
        return api.readOne(id);
    }

    @GetMapping("/contents/all")
    @ResponseStatus(value = HttpStatus.OK) //200
    public List getAllcontentss() {
        return api.readAll();
    }

    @GetMapping("/contents/allPages")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Page getAllcontentssPage(@RequestParam("pagesize") int pagesize, @RequestParam("requestpage") int requestpage) {
        return api.readPage(requestpage, pagesize);
    }

    @PutMapping("/contents")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public void editcontentsInfo(@RequestParam("id") Long id, @RequestBody ContentsDTO contentsDTO) {
        api.update(id, contentsDTO);
    }

}
