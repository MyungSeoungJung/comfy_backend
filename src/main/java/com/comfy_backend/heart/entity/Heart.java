package com.comfy_backend.heart.entity;

import com.comfy_backend.study.entity.Study;
import com.comfy_backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
//https://velog.io/@korea3611/Spring-Boot%EA%B2%8C%EC%8B%9C%EA%B8%80-%EC%A2%8B%EC%95%84%EC%9A%94-%EA%B8%B0%EB%8A%A5-%EB%A7%8C%EB%93%A4%EA%B8%B0
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Builder
    public Heart(User user, Study study) {
        this.user = user;
        this.study = study;
    }
}
