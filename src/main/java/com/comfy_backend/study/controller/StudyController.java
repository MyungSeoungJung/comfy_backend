package com.comfy_backend.study.controller;

import com.comfy_backend.auth.Auth;
import com.comfy_backend.auth.AuthProfile;
import com.comfy_backend.heart.entity.HeartRepository;
import com.comfy_backend.study.entity.Study;
import com.comfy_backend.study.entity.StudyRepository;
import com.comfy_backend.study.entity.dto.PopularStudyDto;
import com.comfy_backend.study.entity.dto.StudyWithTagDto;
import com.comfy_backend.study.service.StudyService;
import com.comfy_backend.study.studyDto.StudySaveRequestDto;
import com.comfy_backend.tag.hashTag.entity.HashTag;
import com.comfy_backend.tag.studyHashTagMapping.entity.HashTagMapping;
import com.comfy_backend.tag.studyHashTagMapping.entity.StudyHashTagRepository;
import com.comfy_backend.user.entity.User;
import com.comfy_backend.user.entity.UserRepository;
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
    @Autowired
    private StudyHashTagRepository studyHashTagRepository;
    @Autowired
    private UserRepository userRepository;

    @Auth
    @PostMapping(value = "/addStudy")
    public ResponseEntity<Long> addStudy(@RequestBody StudySaveRequestDto studySaveRequestDto, @RequestAttribute AuthProfile authProfile) {
        System.out.println(authProfile.getId());
        User user = new User();
        user.setId(authProfile.getId());
        Long savedStudyId = studyService.save(studySaveRequestDto, user);
        return ResponseEntity.ok().body(savedStudyId);
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
        Optional<Study> studyOptional = studyRepository.findById(id);

        if (studyOptional.isPresent()) {
            Study study = studyOptional.get();
            Map<String, Object> response = new HashMap<>();
            response.put("creatorNickName", study.getCreatorNickName());
            response.put("createdTime", study.getCreatedTime());
            response.put("title", study.getTitle());
            response.put("content", study.getContent());
            response.put("totalHearts", study.getTotalHeart());
            response.put("userLikedStudy", userLikedStudy);

            // 연관된 해시태그 정보 추가
            List<String> tagNames = new ArrayList<>();
            for (HashTagMapping hashTagMapping : study.getHashTagMappings()) {
                HashTag hashTag = hashTagMapping.getHashTag();
                tagNames.add(hashTag.getTagName());
            }
            response.put("hashTags", tagNames);

            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/popularStudy")
    public ResponseEntity<List<PopularStudyDto>> popularStudy() {
        // 토탈 하트가 높은 상위 5개 스터디 가져오기
        List<Study> studies = studyRepository.findTop5ByTotalHeartGreaterThanOrderByTotalHeartDesc();
        // DTO 리스트 생성
        List<PopularStudyDto> popularStudyDtos = new ArrayList<>();

        // 상위 5개 스터디를 순회하면서 PopularStudyDto 객체 생성
        for (Study study : studies) {
            // 스터디 작성자로부터 사용자 이미지 가져오기
            long creatorId = study.getCreatorId();
            Optional<User> user = userRepository.findById(creatorId);

            // PopularStudyDto 객체 생성 및 리스트에 추가
            PopularStudyDto popularStudyDto = PopularStudyDto.builder()
                    .userNickName(study.getCreatorNickName())
                    .userImg(user.get().getProfileImage())
                    .content(study.getContent())
                    .build();
            popularStudyDtos.add(popularStudyDto);
        }

        return ResponseEntity.ok().body(popularStudyDtos);
    }

}