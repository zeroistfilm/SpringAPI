package com.youngdong.woowahan.Entity;


import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Contents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CID")
    private Long cid;

    @Column(name = "User_UID")
    private Long uid;

    @Column(name = "Book_BID")
    private Long bid;

    @Column(name = "Page")
    private Integer page;

    @Column(name = "Contents")
    private String contents;

    @CreationTimestamp
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public Contents(Long uid, Long bid, Integer page, String contents) {
        this.uid = uid;
        this.bid = bid;
        this.page = page;
        this.contents = contents;
    }


}
