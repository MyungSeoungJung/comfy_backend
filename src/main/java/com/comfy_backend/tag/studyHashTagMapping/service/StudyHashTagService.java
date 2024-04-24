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
        // 만약 태그명 리스트가 비어있다면 메서드를 종료
        if(tagNames == null || tagNames.isEmpty()) return;

        // 태그명 리스트를 스트림으로 변환하고, 각 태그명에 대해 다음 작업을 수행
        tagNames.stream()
                // 태그명을 가지고 해시태그를 찾거나 새로 생성하여 반환
                .map(hashTag ->
                       hashTagService.findByTagName(hashTag)
                                .orElseGet(() -> hashTagService.save(hashTag)))
                // 질문과 해시태그를 연결하는 작업
                .forEach(hashTag -> mapHashtagToQuestion(study, hashTag));
    }
    private Long mapHashtagToQuestion(Study study, HashTag hashTag) {

        return studyHashTagRepository.save(new HashTagMapping(study,hashTag)).getId();
    }
//    해시태그 업데이트
    public void updateHashTagsForStudy(Study study, List<String> newTagNames) {
        // 새로운 태그 목록이 비어있으면 해당 스터디의 모든 기존 매핑을 삭제하고 리턴
        if (newTagNames == null || newTagNames.isEmpty()) {
            removeAllHashTagsForStudy(study);
            return;
        }

        // 해당 스터디의 모든 기존 매핑을 삭제.
        removeAllHashTagsForStudy(study);

        // 새로운 태그 목록을 이용하여 새로운 해시태그 매핑 새로 만들기
        saveHashTag(study, newTagNames);
    }
//    해시태그 지우기
    private void removeAllHashTagsForStudy(Study study) {
//        맵핑 레파지토리에서 스터디 찾고 해당 스터디 맵핑 지우기
        List<HashTagMapping> existingMappings = studyHashTagRepository.findAllByStudy(study);
        studyHashTagRepository.deleteAll(existingMappings);
    }



    public List<HashTagMapping> findHashtagListByStudy(Study study) {
        return studyHashTagRepository.findAllByStudy(study);
    }
}
