package com.green.boardextra.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetBoardReq {
    private int size;
    private int page;
    private String search;
    private int startIdx;

    public void setSize(int size) {
        this.size = size;
        this.startIdx = (page - 1) * size;
    }
}
