package com.youngdong.woowahan.controller;

import com.youngdong.woowahan.CRUDInterface.APIInterface;
import com.youngdong.woowahan.DTO.UserDTO;
import com.youngdong.woowahan.Entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
public class UserAPIController {
    @Autowired
    private APIInterface<UserDTO, User> api;

    @PostMapping("/user/new")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public String createuser(@RequestBody UserDTO userDTO) {
        try {
            return api.create(userDTO).toJson();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/user/")
    public User getUser(@RequestParam("id") Long id) {
        try {
            return api.readOne(id);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

    }

    @GetMapping("/user/all")
    public List getAllUsers() {
        try {
            return api.readAll();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }
    }

    @GetMapping("/user/allPages")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Page getAllUsersPage(@RequestParam("pagesize") int pagesize, @RequestParam("requestpage") int requestpage) {
        try {
            return api.readPage(requestpage, pagesize);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @PutMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public void editUserInfo(@RequestParam("id") Long id, @RequestBody UserDTO userDTO) {

        try {
            api.update(id, userDTO);
        }catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }

    }

}
