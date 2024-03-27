package com.comfy_backend.study.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Study {
    //    제목, 내용, 모집 상태, 작성자 닉네임, 작성일, 좋아요, 댓글, 댓글 총 수,
    // 태그는 엔티티를 분리해서 사용하는 것이 좋음
    // 관련 사이트 https://velog.io/@korea3611/Spring-Boot-%ED%95%B4%EC%8B%9C%ED%83%9C%EA%B7%B8-%EA%B8%B0%EB%8A%A5%EC%9D%84-%EA%B0%80%EC%A7%80%EA%B3%A0-%EC%9E%88%EB%8A%94-%EA%B2%8C%EC%8B%9C%ED%8C%90-%EB%A7%8C%EB%93%A4%EC%96%B4%EB%B3%B4%EA%B8%B0
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;
    private String title;
    private String content;
    private String recruitStatus;
    private String creatorNickName;

//    private int LikeCount; 좋아요 기능 따로 빼기
    private long creatorId;
    private LocalDateTime createdTime;
    private long totalComment;

}
