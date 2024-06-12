package com.green.boardextra;

import com.green.boardextra.model.GetBoardReq;
import com.green.boardextra.model.GetBoardRes;
import com.green.boardextra.model.PostBoardReq;
import com.green.boardextra.model.PutBoardReq;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import static org.junit.jupiter.api.Assertions.*;


@MybatisTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardMapperTest {

    @Autowired private BoardMapper mapper;
    private final int INS_RECORD_SIZE = 10;

    @BeforeAll
    void beforeAll() {
        System.out.println("bbbbbbbbbbbbbbbbbbbbbb");
        mapper.delBoardAllForTest();

        PostBoardReq p = new PostBoardReq();
        for(int i=1; i<=INS_RECORD_SIZE; i++) {
            p.setTitle(String.format("테스트%d", i));
            p.setContents(String.format("테스트내용%d", i));
            p.setWriter(String.format("테스트작성자%d", i));
            mapper.insBoard(p);
        }

        GetBoardReq req = new GetBoardReq();
        req.setPage(1);
        req.setSize(20);
        List<GetBoardRes> list = mapper.selBoardList(req);
        System.out.printf("list: %d\n", list.size());
        for(GetBoardRes item : list) {
            System.out.println(item);
        }
    }

    @Test
    void insBoard() {
        PostBoardReq p = new PostBoardReq();
        p.setTitle("그린컴퓨터");
        p.setContents("자바 백엔드");
        p.setWriter("홍길동");

        int affectedRows = mapper.insBoard(p);
        assertEquals(1, affectedRows, "영향받은 행이 1이 아니다.");

        GetBoardReq req = new GetBoardReq();
        req.setPage(1);
        req.setSize(20);
        List<GetBoardRes> allList = mapper.selBoardList(req);
        assertEquals(INS_RECORD_SIZE + 1, allList.size());
        GetBoardRes newRecord = allList.get(0);
        //assertEquals(p, newRecord, "insert가 제대로 되지 않음");
        assertEquals(p.getTitle(), newRecord.getTitle(), "제목이 다름");
        //ssertEquals(p.getContents(), newRecord.getCon(), "제목이 다름");
        assertEquals(p.getWriter(), newRecord.getWriter(), "작성자가 다름");
        assertNotNull(newRecord.getCreatedAt(), "날짜가 입력되지 않음");
    }

    @Test
    void selBoardList() {
        GetBoardReq req = new GetBoardReq();
        req.setPage(1);
        req.setSize(20);
        List<GetBoardRes> allList = mapper.selBoardList(req);
        assertEquals(INS_RECORD_SIZE, allList.size(), "레코드 수가 다름");

        int checkVal = 10;
        for(int i=0; i<INS_RECORD_SIZE; i++) {
            GetBoardRes res = allList.get(i);
            assertThat(res.getBoardId(), greaterThan(0L));
            assertEquals(String.format("테스트%d", checkVal), res.getTitle());
            assertEquals(String.format("테스트작성자%d", checkVal), res.getWriter());
            assertNotNull(res.getCreatedAt());
            checkVal--;
        }
    }

    @Test
    void updBoard() {
        GetBoardReq req = new GetBoardReq();
        req.setPage(1);
        req.setSize(20);
        List<GetBoardRes> beforeList = mapper.selBoardList(req);
        GetBoardRes originData = beforeList.get(0);

        PutBoardReq p = new PutBoardReq();
        p.setBoardId(originData.getBoardId());
        p.setTitle("완전히 다른 제목");
        p.setContents("완전히 다른 내용");

        int affectedRows = mapper.updBoard(p);
        assertEquals(1, affectedRows, "수정된 레코드 수가 다름");

        //똑바로 바뀌었는지 체크
        List<GetBoardRes> afterList = mapper.selBoardList(req);
        assertEquals(beforeList.size(), afterList.size(), "레코드 수가 변화가 되었다.");

        GetBoardRes afterData = afterList.get(0);
        assertEquals(p.getTitle(), afterData.getTitle());
    }

}