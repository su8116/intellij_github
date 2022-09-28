package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard(){
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    @Transactional
    @Test
    public void testRead1(){

        Optional<Board> result = boardRepository.findById(100L);
        //보드 테이블에 100번 값을 갖는 자료를 찾는다.

        Board board = result.get(); //Board 객체에 있는 모든 자료를 찾는다

        System.out.println(board);
        System.out.println(board.getWriter()); //작성자를 찾는다
    }

    @Test
    public void testReadWithWriter(){

        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[]) result;

        System.out.println("-------------------------------");
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testGetBoardWithReply(){

        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for (Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {

            Object[] arr = (Object[]) row;

            System.out.println(Arrays.toString(arr));
        });
    } //리스트 화면의 처리 결과 확인
    //[Board(bno=100, title=Title...100, content=Content...100), Member(email=user100@aaa.com, password=1111, name=USER100), 1]
    //[Board(bno=99, title=Title...99, content=Content...99), Member(email=user99@aaa.com, password=1111, name=USER99), 1]
    //[Board(bno=98, title=Title...98, content=Content...98), Member(email=user98@aaa.com, password=1111, name=USER98), 4]
    //[Board(bno=97, title=Title...97, content=Content...97), Member(email=user97@aaa.com, password=1111, name=USER97), 3]
    //[Board(bno=96, title=Title...96, content=Content...96), Member(email=user96@aaa.com, password=1111, name=USER96), 1]
    //[Board(bno=95, title=Title...95, content=Content...95), Member(email=user95@aaa.com, password=1111, name=USER95), 5]
    //[Board(bno=94, title=Title...94, content=Content...94), Member(email=user94@aaa.com, password=1111, name=USER94), 3]
    //[Board(bno=93, title=Title...93, content=Content...93), Member(email=user93@aaa.com, password=1111, name=USER93), 2]
    //[Board(bno=92, title=Title...92, content=Content...92), Member(email=user92@aaa.com, password=1111, name=USER92), 2]
    //[Board(bno=91, title=Title...91, content=Content...91), Member(email=user91@aaa.com, password=1111, name=USER91), 1]

    @Test
    public void testRead3(){
        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }
    //[Board(bno=100, title=Title...100, content=Content...100), Board 테이블 정보
    // Member(email=user100@aaa.com, password=1111, name=USER100), Member 테이블 정보
    // 1] 댓글 수 카운트 정보

}
