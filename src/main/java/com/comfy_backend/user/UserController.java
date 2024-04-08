package com.comfy_backend.user;

import com.comfy_backend.auth.Auth;
import com.comfy_backend.auth.AuthProfile;
import com.comfy_backend.user.dto.ModifyUserDto;
import com.comfy_backend.user.entity.User;
import com.comfy_backend.user.entity.UserRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {

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
}
