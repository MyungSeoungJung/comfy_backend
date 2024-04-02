package com.comfy_backend.comment;

import com.comfy_backend.study.entity.Study;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class StudyComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Study study;

    private long userId;

    private String nickName;

    private String content;

}
