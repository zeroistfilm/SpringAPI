package com.youngdong.woowahan.Entity;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UID")
    private Long uid;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    private String email;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedDate;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public String toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("uid", this.uid);
        obj.addProperty("name", this.name);
        obj.addProperty("page", this.email);
        obj.addProperty("createdAt", String.valueOf(this.createdDate));
        obj.addProperty("updateAt", String.valueOf(this.updatedDate));
        return String.valueOf(obj);
    }

}
