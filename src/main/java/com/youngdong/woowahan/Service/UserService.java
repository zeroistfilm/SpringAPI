package com.youngdong.woowahan.Service;

import com.youngdong.woowahan.DTO.UserDTO;
import com.youngdong.woowahan.Entity.User;
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
public class UserService implements ServiceInterface<UserDTO, User> {

    @Autowired
    private RepositoryInterface<User> userRepository;

    @Override
    public User create(UserDTO userDTO) {

            isVaild(userDTO);
            User user = new User(userDTO.getName(), userDTO.getEmail());
            return userRepository.save(user);

    }

    @Override
    public User readOne(long id) {
        Optional<User> userbyId = userRepository.findById(id);
        if (userbyId.isPresent()) {
            log.info("Success Read User");
            return userbyId.orElse(null);
        } else {
            log.info("Fail Read User");
            throw new NoSuchElementException("No content");
        }

    }

    @Override
    public List<User> readAll() {
        List<User> userAll = userRepository.findAll();
        if (!userAll.isEmpty()) {
            log.info("Success Read All User");
            return userAll;
        } else {
            log.info("Fail Read All User");
            throw new NoSuchElementException("No content");
        }

    }

    @Override
    public Page readPage(int requestpage, int pagesize) {
        Pageable sortedById = PageRequest.of(requestpage, pagesize, Sort.by("uid"));
        Page<User> allpages = userRepository.findAll(sortedById);

        if (requestpage > allpages.getTotalPages()) {
            log.info("Out of page");
            throw new IllegalArgumentException("out of page, MaxPage : " + allpages.getTotalPages());
        } else {
            log.info("Success read user for paging");
            return allpages;
        }

    }

    @Override
    public void update(long id, UserDTO userDTO) {
        Optional<User> userbyId = userRepository.findById(id);
        String newname = (userDTO.getName()!=null) ? userDTO.getName() : userbyId.get().getName();
        userbyId.ifPresentOrElse(
                selectUser -> {
                    selectUser.setName(newname);
                    userRepository.save(selectUser);
                    log.info("Success to update with new data");
                },
                () -> {
                    log.info("Fail to update");
                    throw new NoSuchElementException("No contents");
                });
    }

    @Override
    public void isVaild(UserDTO userDTO) {
        StringBuilder errorMessage = new StringBuilder();
        if (!userDTO.getEmail().isEmpty() && !isValidEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("?????? ????????? ????????? ????????? ?????? ????????????");
        }

        if (userDTO.getName().isEmpty()) {
            errorMessage.append("Name ");
        }

        if (userDTO.getEmail().isEmpty()) {
            errorMessage.append("Email ");
        }

        if (errorMessage.length() > 0) {
            errorMessage.append("????????? ????????????");
            throw new IllegalArgumentException(String.valueOf(errorMessage).strip());
        }
    }


    public boolean isValidEmail(String email) {
        //??? ??? ?????? ?????? ??????
        email = email.trim();
        //@?????? ??????
        String[] splited = email.split("@");
        if (splited.length != 2) { //@ ??????.
            return false;
        }
        //.?????? ??????
        String[] domainToTopDomain = splited[1].split("\\.");
        if (domainToTopDomain.length != 2) { //. ??????.
            return false;
        }
        String account = splited[0];
        String domain = domainToTopDomain[0];
        String topdomain = domainToTopDomain[1];
        // 3?????? ????????? ?????? ?????? ?????? ??????
        if (!account.isEmpty() && !domain.isEmpty() && !topdomain.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
