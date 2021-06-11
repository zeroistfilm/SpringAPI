package com.youngdong.woowahan.DTO;



public class ContentsDTO {

    private Long uid;

    private Long bid;

    private Integer page;

    private String contents;

    public ContentsDTO(Long uid, Long bid, Integer page, String contents) {
        this.uid = uid;
        this.bid = bid;
        this.page = page;
        this.contents = contents;
    }

    public Long getUid() {
        return uid;
    }

    public Long getBid() {
        return bid;
    }

    public Integer getPage() {
        return page;
    }

    public String getContents() {
        return contents;
    }
}
