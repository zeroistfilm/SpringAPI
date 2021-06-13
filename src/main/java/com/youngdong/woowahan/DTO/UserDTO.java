package com.youngdong.woowahan.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
public class UserDTO {
    private String name;

    private String email;

    public UserDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
