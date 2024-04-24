package com.comfy_backend.study.controller;

import com.comfy_backend.auth.Auth;
import com.comfy_backend.auth.AuthProfile;
import com.comfy_backend.heart.entity.HeartRepository;
import com.comfy_backend.study.entity.Study;
import com.comfy_backend.study.entity.StudyRepository;
import com.comfy_backend.study.entity.StudySort;
import com.comfy_backend.study.entity.StudyState;
import com.comfy_backend.study.entity.dto.ModifyStudyDto;
import com.comfy_backend.study.entity.dto.PopularStudyDto;
import com.comfy_backend.study.entity.dto.StudyWithTagDto;
import com.comfy_backend.study.service.StudyService;
import com.comfy_backend.study.studyDto.StudySaveRequestDto;
import com.comfy_backend.tag.hashTag.entity.HashTag;
import com.comfy_backend.tag.studyHashTagMapping.entity.HashTagMapping;
import com.comfy_backend.tag.studyHashTagMapping.entity.StudyHashTagRepository;
import com.comfy_backend.tag.studyHashTagMapping.service.StudyHashTagService;
import com.comfy_backend.user.entity.User;
import com.comfy_backend.user.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/study")
public class StudyController {
    @Autowired
    private StudyService studyService;

    @Autowired
    private StudyHashTagService studyHashTagService;
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

//    @GetMapping(value = "/getStudy")
//    public List<StudyWithTagDto> getStudyWithTags() {
//        List<Study> studies = studyRepository.findAll();
//        List<StudyWithTagDto> studyWithTagDtos = new ArrayList<>();
//
//        for (Study study : studies) {
//            StudyWithTagDto studyWithTagDto = StudyWithTagDto.builder()
//                    .id(study.getId())
//                    .title(study.getTitle())
//                    .content(study.getContent())
//                    .recruitStatus(study.getRecruitStatus())
//                    .creatorNickName(study.getCreatorNickName())
//                    .createdTime(study.getCreatedTime())
//                    .totalComment(study.getTotalComment())
//                    .totalHeart(study.getTotalHeart())
//                    .tagNames(new ArrayList<>()) // 태그 이름을 저장할 리스트 초기화
//                    .build();
//
//            // 해당 스터디의 태그들을 가져와서 StudyWithTagDto에 추가
//            for (HashTagMapping hashTagMapping : study.getHashTagMappings()) {
//                HashTag hashTag = hashTagMapping.getHashTag();
//                studyWithTagDto.getTagNames().add(hashTag.getTagName());
//            }
//            studyWithTagDtos.add(studyWithTagDto);
//        }
//        return studyWithTagDtos;
//    }

    @Auth
    @GetMapping("/studyDetailPage")
    public ResponseEntity<Map<String, Object>> getStudyDetail(@RequestParam long id, @RequestAttribute AuthProfile authProfile) {
        boolean userLikedStudy = heartRepository.isUserLikedStudy(authProfile.getId(), id); // 좋아요 누른 게시물인지
        Optional<Study> studyOptional = studyRepository.findById(id);
        long userId = studyOptional.get().getCreatorId();
        Optional<User> user = userRepository.findById(userId);
        if (studyOptional.isPresent()) {
            Study study = studyOptional.get();
            Map<String, Object> response = new HashMap<>();
            response.put("creatorNickName", study.getCreatorNickName());
            response.put("createdTime", study.getCreatedTime());
            response.put("title", study.getTitle());
            response.put("content", study.getContent());
            response.put("totalHearts", study.getTotalHeart());
            response.put("userLikedStudy", userLikedStudy);
            response.put("recruitStatus", study.getRecruitStatus());
            response.put("writerImg", user.get().getProfileImage());
            response.put("writerId", user.get().getId());
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
                    .title(study.getTitle())
                    .build();
            popularStudyDtos.add(popularStudyDto);
        }

        return ResponseEntity.ok().body(popularStudyDtos);
    }

    @GetMapping(value = "/getStudyPaging")
    public Page<StudyWithTagDto> getStudyPaging(@RequestParam int page, @RequestParam int size){
        page = page - 1; // 페이지 번호를 0부터 시작하도록 조정
        System.out.println(page + "+" + size);
        Sort sort = Sort.by("id").descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Study> studyPage = studyRepository.findAll(pageRequest);

        // Study 페이지를 StudyWithTagDto 페이지로 변환
        Page<StudyWithTagDto> studyWithTagDtoPage = studyPage.map(study -> {
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
            return studyWithTagDto;
        });

        return studyWithTagDtoPage;
    }
    
//   모집중 모집완료에 맞춰 get 메서드
@GetMapping(value = "/getStudyState")
public ResponseEntity<?> getStudyState(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam StudyState state,
        @RequestParam StudySort sort
) {
    // 페이지 번호를 0부터 시작하도록 조정
    page = page - 1;

    // 페이지 요청 객체 생성
    PageRequest pageRequest = PageRequest.of(page, size);

    // 스터디 상태와 정렬에 따라 스터디 조회
    Page<StudyWithTagDto> studyPage;
    switch (state) {
        case ALL:
            studyPage = getStudiesByState("all", sort, pageRequest);
            break;
        case RECRUITING:
            studyPage = getStudiesByState("모집중", sort, pageRequest);
            break;
        case COMPLETED:
            studyPage = getStudiesByState("모집완료", sort, pageRequest);
            break;
        default:
            throw new IllegalArgumentException("Invalid study state: " + state);
    }

    // 조회된 스터디 페이지를 StudyWithTagDto 페이지로 변환 및 반환
    return ResponseEntity.ok(studyPage);
    }
    private Page<StudyWithTagDto> getStudiesByState(String state, StudySort sort, Pageable pageable) {
        switch (sort) {
            case RECENT:
                return studyRepository.findByRecruitStatusOrderByCreatedTimeDesc(state, pageable)
                        .map(this::convertToDto);
            case SCORE:
                return studyRepository.findByRecruitStatusOrderByTotalHeartDesc(state, pageable)
                        .map(this::convertToDto);
            case COMMENTS:
                return studyRepository.findByRecruitStatusOrderByTotalCommentDesc(state, pageable)
                        .map(this::convertToDto);
            default:
                return studyRepository.findByRecruitStatusOrderByCreatedTimeDesc(state, pageable)
                        .map(this::convertToDto);
        }
    }


    private StudyWithTagDto convertToDto(Study study) {
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
        return studyWithTagDto;
    }

    @Auth
    @GetMapping(value = "/getMyStudy")
    public Page<StudyWithTagDto> getMyStudyPaging(@RequestAttribute AuthProfile authProfile, @RequestParam int page, @RequestParam int size){
        page = page - 1; // 페이지 번호를 0부터 시작하도록 조정
        System.out.println(page + "+" + size);
        Sort sort = Sort.by("id").ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Study> studyPage = studyRepository.findByCreatorId(authProfile.getId(), pageRequest);

        // Study 페이지를 StudyWithTagDto 페이지로 변환
        return studyPage.map(study -> StudyWithTagDto.builder()
                .id(study.getId())
                .title(study.getTitle())
                .content(study.getContent())
                .recruitStatus(study.getRecruitStatus())
                .createdTime(study.getCreatedTime())
                .totalComment(study.getTotalComment())
                .totalHeart(study.getTotalHeart())
                .build());
    }

    @GetMapping(value = "/getSimilarStudy")
    public List<Study> getSimilarStudy(@RequestParam long id) {
        Optional<Study> optionalStudy = studyRepository.findById(id);
        if (optionalStudy.isPresent()) {
            Study study = optionalStudy.get();
            Set<HashTag> studyTags = study.getHashTagMappings().stream()
                    .map(HashTagMapping::getHashTag)
                    .collect(Collectors.toSet());

        List<Study> similarStudies = studyRepository.findAllByHashTagMappings_HashTagIn(studyTags);
        similarStudies = similarStudies.stream()
                    .filter(s -> s.getId() != id)
                    .collect(Collectors.toList());

            return similarStudies;
        } else {
            return Collections.emptyList();
        }
    }

    @PutMapping(value = "/studyModify")
    public ResponseEntity<String> studyModify(@RequestBody ModifyStudyDto modifyStudyDto){
        System.out.println(modifyStudyDto);
        Optional<Study> optionalStudy = studyRepository.findById(modifyStudyDto.getId());
        if (optionalStudy.isPresent()) {
            Study study = optionalStudy.get();
            study.setContent(modifyStudyDto.getContent());
            study.setTitle(modifyStudyDto.getTitle());
            study.setRecruitStatus(modifyStudyDto.getRecruitStatus());


            // 스터디 저장
            studyRepository.save(study);
//           태그 삭제 후 수정
            studyHashTagService.updateHashTagsForStudy(study, modifyStudyDto.getTagNames());

            return ResponseEntity.ok("Study modified successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    } // 끝





