package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){

        BoardDTO dto = BoardDTO.builder()
                .title("Test.")
                .content("Test...")
                .writerEmail("user55@aaa.com") //현재 DB에 이메일이 있는지 확인
                .build();

        Long bno = boardService.register(dto); //정상 처리시 bno 가 넘어오도록 처리
    }//결과
    //Hibernate:
    //    select
    //        member_.email,
    //        member_.moddate as moddate2_1_,
    //        member_.name as name4_1_,
    //        member_.password as password5_1_
    //    from
    //        member member_
    //    where
    //        member_.email=?
    //Hibernate:
    //    insert
    //    into
    //        board
    //        (moddate, regdate, content, title, writer_email)
    //    values
    //        (?, ?, ?, ?, ?)

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        //PageRequestDTO 객체를 생성하여 pageRequestDTO 변수로 선언한다

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
        //PageResultDTO 객체를 BoardDTO, Object[] 통로도 사용하여 result 변수로 선언
        //boardService.getList 메서드를 사용하여 pageRequestDTO 를 처리한다

        for (BoardDTO boardDTO : result.getDtoList()){
            System.out.println(boardDTO);
        } //BoardDTO 객체를 향상된 for 문을 이용하여 boardDTO를 출력한다
    } //결과
    //Hibernate:
    //    select
    //        board0_.bno as col_0_0_,
    //        member1_.email as col_1_0_,
    //        count(reply2_.rno) as col_2_0_,
    //        board0_.bno as bno1_0_0_,
    //        member1_.email as email1_1_1_,
    //        board0_.moddate as moddate2_0_0_,
    //        board0_.regdate as regdate3_0_0_,
    //        board0_.content as content4_0_0_,
    //        board0_.title as title5_0_0_,
    //        board0_.writer_email as writer_e6_0_0_,
    //        member1_.moddate as moddate2_1_1_,
    //        member1_.regdate as regdate3_1_1_,
    //        member1_.name as name4_1_1_,
    //        member1_.password as password5_1_1_
    //    from
    //        board board0_
    //    left outer join
    //        member member1_
    //            on board0_.writer_email=member1_.email
    //    left outer join
    //        reply reply2_
    //            on (
    //                reply2_.board_bno=board0_.bno
    //            )
    //    group by
    //        board0_.bno
    //    order by
    //        board0_.bno desc limit ?
    //Hibernate:
    //    select
    //        count(board0_.bno) as col_0_0_
    //    from
    //        board board0_
    //BoardDTO(bno=100, title=Title...100, content=Content...100, regDate=2022-09-26T12:10:05.555300, modDate=2022-09-26T12:10:05.555300, writerEmail=user100@aaa.com, writerName=USER100, replyCount=1)
    //BoardDTO(bno=99, title=Title...99, content=Content...99, regDate=2022-09-26T12:10:05.550270, modDate=2022-09-26T12:10:05.550270, writerEmail=user99@aaa.com, writerName=USER99, replyCount=1)
    //BoardDTO(bno=98, title=Title...98, content=Content...98, regDate=2022-09-26T12:10:05.535318, modDate=2022-09-26T12:10:05.535318, writerEmail=user98@aaa.com, writerName=USER98, replyCount=4)
    //BoardDTO(bno=97, title=Title...97, content=Content...97, regDate=2022-09-26T12:10:05.532535, modDate=2022-09-26T12:10:05.532535, writerEmail=user97@aaa.com, writerName=USER97, replyCount=3)
    //BoardDTO(bno=96, title=Title...96, content=Content...96, regDate=2022-09-26T12:10:05.529503, modDate=2022-09-26T12:10:05.529503, writerEmail=user96@aaa.com, writerName=USER96, replyCount=1)
    //BoardDTO(bno=95, title=Title...95, content=Content...95, regDate=2022-09-26T12:10:05.526272, modDate=2022-09-26T12:10:05.526272, writerEmail=user95@aaa.com, writerName=USER95, replyCount=5)
    //BoardDTO(bno=94, title=Title...94, content=Content...94, regDate=2022-09-26T12:10:05.523405, modDate=2022-09-26T12:10:05.523405, writerEmail=user94@aaa.com, writerName=USER94, replyCount=3)
    //BoardDTO(bno=93, title=Title...93, content=Content...93, regDate=2022-09-26T12:10:05.520242, modDate=2022-09-26T12:10:05.520242, writerEmail=user93@aaa.com, writerName=USER93, replyCount=2)
    //BoardDTO(bno=92, title=Title...92, content=Content...92, regDate=2022-09-26T12:10:05.516273, modDate=2022-09-26T12:10:05.516273, writerEmail=user92@aaa.com, writerName=USER92, replyCount=2)
    //BoardDTO(bno=91, title=Title...91, content=Content...91, regDate=2022-09-26T12:10:05.510264, modDate=2022-09-26T12:10:05.510264, writerEmail=user91@aaa.com, writerName=USER91, replyCount=1)

    @Test
    public void testGet(){
        Long bno = 100L; //조회할 개심ㄹ 100을 선정한다

        BoardDTO boardDTO = boardService.get(bno); //boardService.get() 메서드를 사용해본다

        System.out.println(boardDTO); //결과를 출력한다
    }//결과
    //Hibernate:
    //    select
    //        board0_.bno as col_0_0_,
    //        member1_.email as col_1_0_,
    //        count(reply2_.rno) as col_2_0_,
    //        board0_.bno as bno1_0_0_,
    //        member1_.email as email1_1_1_,
    //        board0_.moddate as moddate2_0_0_,
    //        board0_.regdate as regdate3_0_0_,
    //        board0_.content as content4_0_0_,
    //        board0_.title as title5_0_0_,
    //        board0_.writer_email as writer_e6_0_0_,
    //        member1_.moddate as moddate2_1_1_,
    //        member1_.regdate as regdate3_1_1_,
    //        member1_.name as name4_1_1_,
    //        member1_.password as password5_1_1_
    //    from
    //        board board0_
    //    left outer join
    //        member member1_
    //            on board0_.writer_email=member1_.email
    //    left outer join
    //        reply reply2_
    //            on (
    //                reply2_.board_bno=board0_.bno
    //            )
    //    where
    //        board0_.bno=?
    //BoardDTO(bno=100, title=Title...100, content=Content...100,
    //regDate=2022-09-26T12:10:05.555300, modDate=2022-09-26T12:10:05.555300,
    //writerEmail=user100@aaa.com, writerName=USER100, replyCount=1)

    @Test
    public void testRemove(){

        Long bno = 1L;

        boardService.removeWithReplies(bno);
    } //결과 : 게시글의 댓글을 먼저 삭제한 후에 게시글을 삭제한다
    //Hibernate:
    //    delete
    //    from
    //        reply
    //    where
    //        board_bno=?
    //Hibernate:
    //    select
    //        board0_.bno as bno1_0_0_,
    //        board0_.moddate as moddate2_0_0_,
    //        board0_.regdate as regdate3_0_0_,
    //        board0_.content as content4_0_0_,
    //        board0_.title as title5_0_0_,
    //        board0_.writer_email as writer_e6_0_0_
    //    from
    //        board board0_
    //    where
    //        board0_.bno=?
    //Hibernate:
    //    delete
    //    from
    //        board
    //    where
    //        bno=?

    @Test
    public void testModify(){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("제목 변경합니다.")
                .content("내용 변경합니다.")
                .build();
        boardService.modify(boardDTO);
    } //결과 : @Transactional 처리를 해야 nosession 에 대한 오류를 처리할 수 있다
    //Hibernate:
    //    select
    //        board0_.bno as bno1_0_0_,
    //        board0_.moddate as moddate2_0_0_,
    //        board0_.regdate as regdate3_0_0_,
    //        board0_.content as content4_0_0_,
    //        board0_.title as title5_0_0_,
    //        board0_.writer_email as writer_e6_0_0_
    //    from
    //        board board0_
    //    where
    //        board0_.bno=?
    //Hibernate:
    //    update
    //        board
    //    set
    //        moddate=?,
    //        content=?,
    //        title=?,
    //        writer_email=?
    //    where
    //        bno=?

}
