package com.comfy_backend.tag.hashTag.entity;

import com.comfy_backend.tag.hashTag.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    Optional<HashTag> findByTagName(String tagName);
}
