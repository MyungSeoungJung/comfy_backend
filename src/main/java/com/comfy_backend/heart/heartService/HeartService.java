package com.comfy_backend.heart.heartService;

import com.comfy_backend.heart.entity.Heart;
import com.comfy_backend.heart.entity.HeartRepository;
import com.comfy_backend.heart.entity.HeartRequestDto;
import com.comfy_backend.study.entity.Study;
import com.comfy_backend.study.entity.StudyRepository;
import com.comfy_backend.user.entity.User;
import com.comfy_backend.user.entity.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HeartService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    StudyRepository studyRepository;
    @Autowired
    HeartRepository heartRepository;

    @Transactional
    public ResponseEntity<?> insert(HeartRequestDto heartRequestDto){

        User user = userRepository.findById(heartRequestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("Could not found member id : " + heartRequestDto.getMemberId()));

        Study study = studyRepository.findById(heartRequestDto.getBoardId())
                .orElseThrow(() -> new RuntimeException("Could not found board id : " + heartRequestDto.getBoardId()));

        // 이미 좋아요되어있으면 에러 반환
        if (heartRepository.findByUserAndStudy(user, study).isPresent()){

            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Heart heart = Heart.builder()
                .study(study)
                .user(user)
                .build();

        heartRepository.save(heart);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public void delete(HeartRequestDto heartRequestDto) {

        User user = userRepository.findById(heartRequestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("Could not found member id : " + heartRequestDto.getMemberId()));

        Study study = studyRepository.findById(heartRequestDto.getBoardId())
                .orElseThrow(() -> new RuntimeException("Could not found board id : " + heartRequestDto.getBoardId()));

        Heart heart = heartRepository.findByUserAndStudy(user, study)
                .orElseThrow(() -> new RuntimeException("Could not found heart id"));

        heartRepository.delete(heart);
    }

}
