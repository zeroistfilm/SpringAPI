package com.youngdong.woowahan.cntroller;

import com.youngdong.woowahan.domain.User;
import com.youngdong.woowahan.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class APIController {

    private final UserService userService;

    public APIController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getallusers")
    @ResponseBody
    public List getAllUsers(UserService userService){
        List<User> allUsers = userService.findAll();
        return allUsers;
    }

    @PostMapping("/saveuser")
    @ResponseBody
    public Object saveuser(@RequestBody User user){
        try{
            user.isVailid();
            Long resultId =  this.userService.join(user);
            return resultId;
        }catch (IllegalStateException e){
            return e.getMessage();
        }

    }



}
