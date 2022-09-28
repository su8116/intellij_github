package org.zerock.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") //@ToString은 항상 exclude
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동번호 생성
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY) //기본값은 Eager 로딩이고 Lazy 로딩은 지연 로딩
    private Member writer; //연관관계 지정

    public void changeTitle(String title){
        this.title = title;
    } //수정할 때 활용되는 제목

    public void changeContent(String content){
        this.content = content;
    } //수정할 때 활용되는 내용
}
