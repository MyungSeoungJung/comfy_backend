package com.comfy_backend.tag.studyHashTagMapping.service;

import com.comfy_backend.study.entity.Study;
import com.comfy_backend.tag.hashTag.entity.HashTag;
import com.comfy_backend.tag.hashTag.entity.HashTagRepository;
import com.comfy_backend.tag.hashTag.service.HashTagService;
import com.comfy_backend.tag.studyHashTagMapping.entity.HashTagMapping;
import com.comfy_backend.tag.studyHashTagMapping.entity.StudyHashTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudyHashTagService {
    @Autowired
    HashTagService hashTagService;
    @Autowired
    StudyHashTagRepository studyHashTagRepository;

    public void saveHashTag(Study study, List<String> tagNames) {
        // 만약 태그명 리스트가 비어있다면 메서드를 종료합니다.
        if(tagNames == null || tagNames.isEmpty()) return;

        // 태그명 리스트를 스트림으로 변환하고, 각 태그명에 대해 다음 작업을 수행
        tagNames.stream()
                // 태그명을 가지고 해시태그를 찾거나 새로 생성하여 반환
                .map(hashTag ->
                       hashTagService.findByTagName(hashTag)
                                .orElseGet(() -> hashTagService.save(hashTag)))
                // 질문과 해시태그를 연결하는 작업을 수행합니다.
                .forEach(hashTag -> mapHashtagToQuestion(study, hashTag));
    }
    private Long mapHashtagToQuestion(Study study, HashTag hashTag) {

        return studyHashTagRepository.save(new HashTagMapping(study,hashTag)).getId();
    }

    public List<HashTagMapping> findHashtagListByStudy(Study study) {

        return studyHashTagRepository.findAllByStudy(study);
    }
}
