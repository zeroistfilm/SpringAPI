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

    public String toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("cid", this.cid);
        obj.addProperty("uid", this.uid);
        obj.addProperty("bid", this.bid);
        obj.addProperty("page", this.page);
        obj.addProperty("contents", this.contents);
        obj.addProperty("createdAt", String.valueOf(this.createdDate));
        obj.addProperty("updateAt", String.valueOf(this.updatedDate));
        return String.valueOf(obj);
    }

}
