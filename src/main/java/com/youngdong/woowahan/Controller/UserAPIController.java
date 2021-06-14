package com.youngdong.woowahan.Controller;

import com.youngdong.woowahan.DTO.UserDTO;
import com.youngdong.woowahan.Entity.User;
import com.youngdong.woowahan.ServiceInterface.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UserAPIController {
    @Autowired
    private ServiceInterface<UserDTO, User> api;

    @PostMapping("/user/new")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public User createuser(@RequestBody UserDTO userDTO) {
        return api.create(userDTO);
    }

    @GetMapping("/user")
    @ResponseStatus(value = HttpStatus.OK) //200
    public User getUser(@RequestParam("id") Long id) {
        return api.readOne(id);
    }

    @GetMapping("/user/all")
    @ResponseStatus(value = HttpStatus.OK) //200
    public List getAllUsers() {
        return api.readAll();
    }

    @GetMapping("/user/allPages")
    @ResponseStatus(value = HttpStatus.OK) //200
    public Page getAllUsersPage(@RequestParam("pagesize") int pagesize, @RequestParam("requestpage") int requestpage) {
        return api.readPage(requestpage, pagesize);
    }

    @PutMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED) //201
    public void editUserInfo(@RequestParam("id") Long id, @RequestBody UserDTO userDTO) {
        api.update(id, userDTO);
    }

}
