package com.green.boardextra.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GetBoardRes {
    private long boardId;
    private String title;
    private String writer;
    private String createdAt;
}
