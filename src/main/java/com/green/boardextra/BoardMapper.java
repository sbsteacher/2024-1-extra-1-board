package com.green.boardextra;

import com.green.boardextra.model.PostBoardReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    int insBoard(PostBoardReq p);
}
