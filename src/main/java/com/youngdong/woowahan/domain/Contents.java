package com.youngdong.woowahan.domain;


//    EID     int          not null auto_increment primary key,
//
//    User_UID     int ,
//    FOREIGN KEY (User_UID) REFERENCES User (UID),
//
//    Book_BID     int,
//    FOREIGN KEY (Book_BID) REFERENCES Book (BID),
//
//    Page    int not null,
//    Contents varchar(200) not null,
public class Contents {
    private Long eid;
    private Long uid;
    private Long bid;
    private Integer page;
    private String contents;


    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
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
