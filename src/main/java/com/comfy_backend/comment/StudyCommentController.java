package com.comfy_backend.comment;

import com.comfy_backend.auth.Auth;
import com.comfy_backend.auth.AuthProfile;
import com.comfy_backend.study.entity.Study;
import com.comfy_backend.study.entity.StudyRepository;
import com.comfy_backend.user.entity.User;
import com.comfy_backend.user.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/studyComment")
public class StudyCommentController {
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StudyCommentRepository studyCommentRepository;
    @Auth
    @PostMapping("/addComment")
    public ResponseEntity<Map<String, Objects>> addComment (@RequestParam long id,
                                                            @RequestBody StudyComment studyComment,
                                                            @RequestAttribute AuthProfile authProfile){
        Optional<Study> study = studyRepository.findById(id);
        Optional<User> user = userRepository.findById(authProfile.getId());
        if (study.isEmpty() || user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        System.out.println(studyComment.getContent());
        studyComment.setStudy(study.get());
        studyComment.setUserId(authProfile.getId());
        studyComment.setContent(studyComment.getContent());
        studyComment.setNickName(user.get().getNickName());
        studyCommentRepository.save(studyComment);
        return ResponseEntity.ok().build();
    }
}
