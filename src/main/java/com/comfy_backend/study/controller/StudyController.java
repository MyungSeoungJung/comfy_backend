package com.comfy_backend.study.controller;

import com.comfy_backend.auth.Auth;
import com.comfy_backend.auth.AuthProfile;
import com.comfy_backend.heart.entity.HeartRepository;
import com.comfy_backend.study.entity.Study;
import com.comfy_backend.study.entity.StudyRepository;
import com.comfy_backend.study.service.StudyService;
import com.comfy_backend.study.studyDto.StudySaveRequestDto;
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
    @GetMapping (value = "/getStudy")
    public List<Study> getStudy(){
        List<Study> list = studyRepository.findAll();
        return list;
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
