package com.comfy_backend.tag.hashTag.entity;

import com.comfy_backend.tag.studyHashTagMapping.entity.HashTagMapping;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//https://s7won.tistory.com/3

@Getter
@Setter
@NoArgsConstructor
@Entity
public class HashTag extends BaseEntity {

    private String tagName;

    @OneToMany(mappedBy = "hashTag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HashTagMapping> hashTagMapping;

    @Builder
    public HashTag(String tagName) {
        this.tagName = tagName;
    }
}