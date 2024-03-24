package com.comfy_backend.study.service;

import com.comfy_backend.study.entity.Study;
import com.comfy_backend.study.entity.StudyRepository;
import com.comfy_backend.study.studyDto.StudySaveRequestDto;
import com.comfy_backend.tag.studyHashTagMapping.service.StudyHashTagService;
import com.comfy_backend.user.entity.User;
import com.comfy_backend.user.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class StudyService {
    @Autowired
    StudyHashTagService studyHashTagService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    StudyRepository studyRepository;
    public Long save(StudySaveRequestDto studySaveRequestDto, User user) {
        User creatorNickName = userRepository.findByEmail(user.getNickName()).get();
        Study savedStudy = studyRepository.save(studySaveRequestDto.toEntity(creatorNickName));
        studyHashTagService.saveHashTag(savedStudy, studySaveRequestDto.tagNames());

        return savedStudy.getId();
    }
}
