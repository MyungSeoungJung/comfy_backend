package com.comfy_backend.tag.studyHashTagMapping.entity;

import com.comfy_backend.study.entity.Study;
import com.comfy_backend.tag.hashTag.entity.BaseEntity;
import com.comfy_backend.tag.hashTag.entity.HashTag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class HashTagMapping extends BaseEntity {
    @ManyToOne
    private Study study;

    @ManyToOne
    private HashTag hashTag;
}
