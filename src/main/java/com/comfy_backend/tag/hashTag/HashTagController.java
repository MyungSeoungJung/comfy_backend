package com.comfy_backend.tag.hashTag;

import com.comfy_backend.tag.hashTag.entity.HashTag;
import com.comfy_backend.tag.hashTag.entity.HashTagRepository;
import com.comfy_backend.tag.studyHashTagMapping.entity.HashTagMapping;
import com.comfy_backend.tag.studyHashTagMapping.entity.StudyHashTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("hashTag")
public class HashTagController {

    @Autowired
    HashTagRepository hashTagRepository;
    @Autowired
    StudyHashTagRepository studyHashTagRepository;
    @GetMapping("PopularHashTag")
    public List<String> getTopFrequentHashTags() {
        // 해시태그 등장 횟수를 추적하는 맵 생성
        Map<String, Integer> hashTagFrequencyMap = new HashMap<>();

        // 모든 StudyHashTag 엔티티를 가져와서 해시태그 등장 횟수를 세어 맵에 저장합니다.
        List<HashTagMapping> allStudyHashTags = studyHashTagRepository.findAll();
        for (HashTagMapping studyHashTag : allStudyHashTags) {
            HashTag hashTag = studyHashTag.getHashTag();
            String tagName = hashTag.getTagName();
            hashTagFrequencyMap.put(tagName, hashTagFrequencyMap.getOrDefault(tagName, 0) + 1);
        }

        // 해시태그 등장 횟수가 높은 순으로 정렬해서 상위 7개 추출하기
        List<String> topFrequentHashTags = hashTagFrequencyMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(7)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return topFrequentHashTags;
    }
}
