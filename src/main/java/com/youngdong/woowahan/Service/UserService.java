package com.youngdong.woowahan.Service;

import com.youngdong.woowahan.ServiceInterface.ServiceInterface;
import com.youngdong.woowahan.DTO.UserDTO;
import com.youngdong.woowahan.Entity.User;
import com.youngdong.woowahan.RepositoryInterface.RepositoryInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
            throw new IllegalStateException("No content");
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
            throw new IllegalStateException("No content");
        }

    }

    @Override
    public Page readPage(int requestpage, int pagesize) {
        Pageable sortedById = PageRequest.of(requestpage, pagesize, Sort.by("uid"));
        Page<User> allpages = userRepository.findAll(sortedById);

        if (requestpage > allpages.getTotalPages()) {
            log.info("Out of page");
            throw new IllegalStateException("out of page, MaxPage : " + allpages.getTotalPages());
        } else {
            log.info("Success read user for paging");
            return allpages;
        }

    }

    @Override
    public void update(long id, UserDTO userDTO) {
        isVaild(userDTO);
        Optional<User> userbyId = userRepository.findById(id);
        userbyId.ifPresentOrElse(
                selectUser -> {
                    selectUser.setName(userDTO.getName());
                    selectUser.setEmail(userDTO.getEmail());
                    userRepository.save(selectUser);
                    log.info("Success to update with new data");
                },
                () -> {
                    log.info("Fail to update");
                    throw new IllegalStateException("No contents");
                });
    }

    @Override
    public void isVaild(UserDTO userDTO) {
        StringBuilder errorMessage = new StringBuilder();
        if (!userDTO.getEmail().isEmpty() && !isValidEmail(userDTO.getEmail())) {
            throw new IllegalStateException("회원 이메일 정보가 양식에 맞지 않습니다");
        }

        if (userDTO.getName().isEmpty()) {
            errorMessage.append("Name ");
        }

        if (userDTO.getEmail().isEmpty()) {
            errorMessage.append("Email ");
        }

        if (errorMessage.length() > 0) {
            errorMessage.append("정보가 없습니다");
            throw new IllegalStateException(String.valueOf(errorMessage).strip());
        }
    }


    public boolean isValidEmail(String email) {
        //앞 뒤 공백 문자 제거
        email = email.trim();
        //@기준 분리
        String[] splited = email.split("@");
        if (splited.length != 2) { //@ 없음.
            return false;
        }
        //.기준 분리
        String[] domainToTopDomain = splited[1].split("\\.");
        if (domainToTopDomain.length != 2) { //. 없음.
            return false;
        }
        String account = splited[0];
        String domain = domainToTopDomain[0];
        String topdomain = domainToTopDomain[1];
        // 3가지 정보가 모두 들어 있는 경우
        if (!account.isEmpty() && !domain.isEmpty() && !topdomain.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
