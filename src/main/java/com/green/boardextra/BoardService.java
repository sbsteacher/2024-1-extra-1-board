package com.green.boardextra;

import com.green.boardextra.model.PostBoardReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper mapper;

    public int postBoard(PostBoardReq p) {
        return mapper.insBoard(p);
    }
}
