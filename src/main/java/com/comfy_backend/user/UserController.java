package com.comfy_backend.user;

import com.comfy_backend.auth.Auth;
import com.comfy_backend.auth.AuthProfile;
import com.comfy_backend.user.entity.User;
import com.comfy_backend.user.entity.UserRepository;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
