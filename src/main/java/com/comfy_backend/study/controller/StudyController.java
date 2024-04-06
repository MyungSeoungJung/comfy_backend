package com.comfy_backend.study.controller;

import com.comfy_backend.auth.Auth;
import com.comfy_backend.auth.AuthProfile;
import com.comfy_backend.heart.entity.HeartRepository;
import com.comfy_backend.study.entity.Study;
import com.comfy_backend.study.entity.StudyRepository;
import com.comfy_backend.study.entity.studyWithTagDto.StudyWithTagDto;
import com.comfy_backend.study.service.StudyService;
import com.comfy_backend.study.studyDto.StudySaveRequestDto;
import com.comfy_backend.tag.hashTag.entity.HashTag;
import com.comfy_backend.tag.studyHashTagMapping.entity.HashTagMapping;
import com.comfy_backend.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/study")
public class StudyController {
    @Autowired
    private StudyService studyService;
    @Autowired
    private StudyRepository studyRepository;
    @Autowired
    private HeartRepository heartRepository;

    @Auth
    @PostMapping (value = "/addStudy")
    public ResponseEntity<Map<String, Objects>> addStudy(@RequestBody StudySaveRequestDto studySaveRequestDto, @RequestAttribute AuthProfile authProfile) {
        System.out.println(authProfile.getId());
        User user = new User();
        user.setId(authProfile.getId());
        Long savedStudyId = studyService.save(studySaveRequestDto, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getStudy")
    public List<StudyWithTagDto> getStudyWithTags() {
        List<Study> studies = studyRepository.findAll();
        List<StudyWithTagDto> studyWithTagDtos = new ArrayList<>();

        for (Study study : studies) {
            StudyWithTagDto studyWithTagDto = StudyWithTagDto.builder()
                    .id(study.getId())
                    .title(study.getTitle())
                    .content(study.getContent())
                    .recruitStatus(study.getRecruitStatus())
                    .creatorNickName(study.getCreatorNickName())
                    .createdTime(study.getCreatedTime())
                    .totalComment(study.getTotalComment())
                    .totalHeart(study.getTotalHeart())
                    .tagNames(new ArrayList<>()) // 태그 이름을 저장할 리스트 초기화
                    .build();

            // 해당 스터디의 태그들을 가져와서 StudyWithTagDto에 추가
            for (HashTagMapping hashTagMapping : study.getHashTagMappings()) {
                HashTag hashTag = hashTagMapping.getHashTag();
                studyWithTagDto.getTagNames().add(hashTag.getTagName());
            }

            studyWithTagDtos.add(studyWithTagDto);
        }

        return studyWithTagDtos;
    }

    @Auth
    @GetMapping("/studyDetailPage")
    public ResponseEntity<Map<String, Object>> getStudyDetail(@RequestParam long id, @RequestAttribute AuthProfile authProfile) {
        boolean userLikedStudy = heartRepository.isUserLikedStudy(authProfile.getId(), id); // 좋아요 누른 게시물인지
        Optional<Study> study = studyRepository.findById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("creatorNickName" , study.get().getCreatorNickName());
        response.put("createdTime" , study.get().getCreatedTime());
        response.put("title" , study.get().getTitle());
        response.put("content" , study.get().getContent());
        response.put("totalHearts", study.get().getTotalHeart());
        response.put("userLikedStudy", userLikedStudy);
        return ResponseEntity.ok().body(response);
    }
}
