package com.comfy_backend.tag.hashTag.service;

import com.comfy_backend.tag.hashTag.entity.HashTag;
import com.comfy_backend.tag.hashTag.entity.HashTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class HashTagService {

    private final HashTagRepository hashtagRepository;

    public Optional<HashTag> findByTagName(String tagName) {

        return hashtagRepository.findByTagName(tagName);
    }
    public HashTag save(String tagName) {

        return hashtagRepository.save(
                HashTag.builder()
                        .tagName(tagName)
                        .build());
    }
}