package com.youngdong.woowahan.Entity;


import com.google.gson.JsonObject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

//    EID     int          not null auto_increment primary key,
//
//    User_UID     int ,
//    FOREIGN KEY (User_UID) REFERENCES User (UID),
//
//    Book_BID     int
//    FOREIGN KEY (Book_BID) REFERENCES Book (BID),
//
//    Page    int not null,
//    Contents varchar(200) not null,
@Entity
@Slf4j
@NoArgsConstructor
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

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Contents(Long uid, Long bid, Integer page, String contents) {
        this.uid = uid;
        this.bid = bid;
        this.page = page;
        this.contents = contents;
    }

    public Contents(int i, int i1, int page) {
    }

    public void isVaild(){

        StringBuilder errorMessage = new StringBuilder();

        if (this.uid==null){
            errorMessage.append("Uid ");
        }
        if (this.bid==null){
            errorMessage.append("Bid ");
        }
        if (this.page==null){
            errorMessage.append("Page ");
        }
        if (this.contents.isEmpty()){
            errorMessage.append("Contents ");
        }

        if (errorMessage.length()>0) {

            errorMessage.append("정보가 없습니다");
            throw new IllegalStateException(String.valueOf(errorMessage).strip());
        }
    }

    public String toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("uid", this.uid);
        obj.addProperty("bid", this.bid);
        obj.addProperty("page", this.page);
        obj.addProperty("contents", this.contents);
        return String.valueOf(obj);
    }
    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
