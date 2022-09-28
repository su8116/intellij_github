package org.zerock.board.service;

import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto); //등록 처리

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO); //목록 처리

    BoardDTO get(Long bno); //게시물 조회로 게시물 번호를 파라미터로 받아서 처리

    void removeWithReplies(Long bno); //bno 로 삭제처리를 진행한다
    
    void modify(BoardDTO boardDTO); //수정 처리를 위한 boardDTO 를 사용한다

    default Board dtoToEntity(BoardDTO dto){

        Member member = Member.builder().email(dto.getWriterEmail()).build();
        //Board 에 있는 자료를 기준으로 Member 에 있는 이메일을 가져올 수 있도록 builder 사용

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member) //13행에서 작성한 빌더의 자료를 사용하여 작성자를 알아낸다
                .build();
        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) //long으로 나오므로 int로 처리하도록
                .build();
        return boardDTO;
    }
}
