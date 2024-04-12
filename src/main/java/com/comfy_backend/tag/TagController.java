package com.comfy_backend.tag;

import com.comfy_backend.auth.Auth;
import com.comfy_backend.tag.hashTag.entity.HashTagRepository;
import com.comfy_backend.tag.studyHashTagMapping.entity.StudyHashTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("tag")
public class TagController {
    @Autowired
    StudyHashTagRepository studyHashTagRepository;
    @Autowired
    HashTagRepository hashTagRepository;

//    public ResponseEntity<Map<String, Objects>> getStudyTag (@RequestParam long id){
//
//        return null;
//    }
}
