package com.comfy_backend.study.controller;

import com.comfy_backend.auth.Auth;
import com.comfy_backend.auth.AuthProfile;
import com.comfy_backend.study.entity.Study;
import com.comfy_backend.study.entity.StudyRepository;
import com.comfy_backend.study.service.StudyService;
import com.comfy_backend.study.studyDto.StudySaveRequestDto;
import com.comfy_backend.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/study")
public class StudyController {
    @Autowired
    private StudyService studyService;
    @Autowired
    private StudyRepository studyRepository;

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
}
