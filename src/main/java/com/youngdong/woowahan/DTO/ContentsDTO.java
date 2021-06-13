package com.youngdong.woowahan.DTO;


import lombok.Getter;

@Getter
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

}
