package com.comfy_backend.auth;

import com.comfy_backend.auth.request.SignupRequest;
import com.comfy_backend.auth.util.Hash;
import com.comfy_backend.user.entity.User;
import com.comfy_backend.user.entity.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private UserRepository userRepository;
    @Autowired
    Hash hash;
    @Autowired
    public AuthService(UserRepository userRepository){
    this.userRepository = userRepository;
    }
    @Transactional
    public long createIdentity(SignupRequest signupReq){
        User user = User.builder()
                .name(signupReq.getName())
                .email(signupReq.getEmail())
                .secret(hash.createHash(signupReq.getPassword()))
                .build();

        userRepository.save(user);

        return user.getId();
    }
}
