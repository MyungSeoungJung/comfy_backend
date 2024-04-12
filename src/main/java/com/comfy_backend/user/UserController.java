package com.comfy_backend.user;

import com.comfy_backend.auth.Auth;
import com.comfy_backend.auth.AuthProfile;
import com.comfy_backend.user.dto.ModifyUserDto;
import com.comfy_backend.user.entity.User;
import com.comfy_backend.user.entity.UserRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {
    private final String POST_FILE_PATH = "files/profileImage";
    @Autowired
    UserRepository userRepository;

    @Auth
    @GetMapping("getUserInfo")
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestAttribute AuthProfile authProfile){
        Optional<User> user = userRepository.findById(authProfile.getId());
        Map<String, Object> response = new HashMap<>();
        response.put("userImg", user.get().getProfileImage());
        response.put("userNickName" ,user.get().getNickName());
        response.put("introduce" ,user.get().getIntroduce());
        response.put("userId" ,user.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Auth
    @PutMapping("putUserInfo")
    public ResponseEntity<Map<String, Object>> putUserInfo(@RequestAttribute AuthProfile authProfile, @RequestBody ModifyUserDto userInfo){
        Optional<User> user = userRepository.findById(authProfile.getId());
        User modifyUser = user.get();
        System.out.println(userInfo.getIntroduction());
        modifyUser.setNickName(userInfo.getNickname());
        modifyUser.setIntroduce(userInfo.getIntroduction());
        modifyUser.setProfileImage(userInfo.getUserImg());
        userRepository.save(modifyUser);
        return null;
    }

    @GetMapping("/file/{uuidFilename}")
    public ResponseEntity<Resource> getImage(@PathVariable("uuidFilename") String uuidFilename) throws IOException {
        Path imagePath = Paths.get(POST_FILE_PATH, uuidFilename); // 이미지 파일 경로

        byte[] imageBytes = Files.readAllBytes(imagePath);

        ByteArrayResource resource = new ByteArrayResource(imageBytes);
        System.out.println("resource"+ resource);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + uuidFilename)
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(imageBytes.length)
                .body(resource);
    }
}
