package com.comfy_backend.auth;

import com.comfy_backend.auth.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity signUp (@RequestBody SignupRequest signupRequest){
        long userId = authService.createIdentity(signupRequest);
     return ResponseEntity.status(HttpStatus.OK).body(userId);
    }
}
