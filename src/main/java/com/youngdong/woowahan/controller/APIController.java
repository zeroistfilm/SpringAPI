package com.youngdong.woowahan.controller;

import com.google.gson.JsonObject;
import com.youngdong.woowahan.domain.User;
import com.youngdong.woowahan.service.UserService;
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
public class APIController {

    private final UserService userService;

    public APIController(UserService userService) {
        this.userService = userService;
    }

    //-----------Create--------
    @PostMapping("/users/new")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public String saveuser(@RequestBody User user) {
        try {
            user.isVailid();
            Long resultId = this.userService.join(user);
            JsonObject obj = new JsonObject();
            obj.addProperty("uid", resultId);
            obj.addProperty("name", user.getName());
            obj.addProperty("email", user.getEmail());
            log.info("Success Create User");
            return obj.toString();

        } catch (IllegalStateException e) {
            log.info("Fail Create User");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e); //400
        }
    }
    //-----------Read--------
    @GetMapping("/users/all")
    @ResponseBody
    public List getAllUsers() {
        List<User> allUsers = this.userService.findAll();
        if (allUsers.isEmpty()){
            log.info("Fail Read All Users");
        }
        log.info("Success Read All Users");
        return allUsers;
    }

    @GetMapping("/users/allPages")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK) //200
    public Page getAllUsersPage(@RequestParam("pagesize") int pagesize,@RequestParam("requestpage") int requestpage) {
        Pageable sortedById = PageRequest.of(requestpage, pagesize, Sort.by("uid"));
        Page<User> allpages = this.userService.findAll(sortedById);

        if (requestpage>allpages.getTotalPages()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"페이지 범위를 초과하는 요청입니다 MaxPage : "+allpages.getTotalPages(), new IndexOutOfBoundsException());
        }
        if (allpages.isEmpty()){
            log.info("Fail Read All Users");
        }
        log.info("Success Read All Users");
        return allpages;
    }

    @GetMapping("/users/")
    @ResponseBody
    public User getUser(@RequestParam("id") Long id) {
        Optional<User> user = this.userService.findOne(id);
        if (user.isEmpty()) {
            log.info("Fail Read User");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "정보없음");//204
        }
        log.info("Success Read User");
        return user.orElse(null);
    }


    //-----------Update--------
    @PostMapping("/users")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public User editUserInfo(@RequestParam("id") Long id, @RequestBody User user) {
        user.isVailid();
        Optional<User> finduser = this.userService.findOne(id);
        finduser.ifPresent(selectUser -> {
            selectUser.setName(user.getName());
            selectUser.setEmail(user.getEmail());
            userService.join(selectUser);
        });
        Optional<User> updatedUser = this.userService.findOne(id);
        log.info("Success Update User");
        return updatedUser.orElse(null);
    }

}
