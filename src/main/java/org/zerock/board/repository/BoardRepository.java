package org.zerock.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b, w from Board b left join b.writer w where b.bno = :bno") //: -> 파라미터로 받을 때
    Object getBoardWithWriter(@Param("bno") Long bno);
    //한 개의 Object의 배열 값으로 나옴
    //Board를 사용하고 있지만 Member를 같이 조회해야 하는 상황
    //b.writer는 Board 클래스는 Member 클래스와 연관관계를 맺고 있음
    //Board 입장에서 보면 writer와 연과 관계가 있어 on을 생략함

    @Query("SELECT b, r FROM Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);
    //Board 입장에서 보면 Reply에 연관 관계가 없어서 on을 붙인다

    @Query(value = "SELECT b, w, count(r) " +
                    "FROM Board b LEFT JOIN b.writer w LEFT JOIN Reply r ON r.board = b " +
                    "GROUP BY b ",
                    countQuery = "SELECT count(b) FROM Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable); //페이징 처리
    //Board에 모든 것 Writer에 모든 것 Reply에 개수(count)를 필드로 가져온다

    @Query("SELECT b, w, count(r) " +
            "FROM Board b LEFT JOIN b.writer w LEFT OUTER JOIN Reply r ON r.board = b " +
            "WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}
