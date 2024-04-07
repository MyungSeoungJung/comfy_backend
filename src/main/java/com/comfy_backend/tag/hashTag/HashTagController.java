package com.comfy_backend.tag.hashTag;

import com.comfy_backend.tag.hashTag.entity.HashTag;
import com.comfy_backend.tag.hashTag.entity.HashTagRepository;
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
    @GetMapping("PopularHashTag")
    public List<String> getTopFrequentWords() {
        // 모든 해시태그를 가져오기
        List<HashTag> allHashTags = hashTagRepository.findAll();

        // 각 단어의 등장 횟수를 추적하는 맵 생성
        Map<String, Integer> wordFrequencyMap = new HashMap<>();

        // 각 단어의 등장 횟수를 세어 맵에 저장합니다.
        for (HashTag hashTag : allHashTags) {
            String tagName = hashTag.getTagName(); // 해시태그 이름
            wordFrequencyMap.put(tagName, wordFrequencyMap.getOrDefault(tagName, 0) + 1);
        }

        // 등장 횟수가 높은 순으로 정렬해서 상위 7개 추출하기
        List<String> topFrequentWords = wordFrequencyMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(7)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return topFrequentWords;
    }
}
