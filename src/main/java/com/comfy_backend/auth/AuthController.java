package com.comfy_backend.auth;

import com.comfy_backend.auth.request.SignupRequest;
import com.comfy_backend.auth.util.Hash;
import com.comfy_backend.auth.util.Jwt;
import com.comfy_backend.user.entity.User;
import com.comfy_backend.user.entity.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    Hash hash;
    @Autowired
    Jwt jwt;
    @Autowired
    UserRepository userRepository;
    @Value("${app.login.url}")
    private String loginUrl;
    @Value("${app.home.url}")
    private String homeUrl;
    @Value("${app.cookie.domain}")
    private String cookieDomain;
    @PostMapping("/signUp")
    public ResponseEntity signUp (@RequestParam("email") String email,
                                  @RequestParam("nickName") String nickName,
                                  @RequestParam("password") String password,
                                  @RequestParam("profileImage") MultipartFile profileImage) {
        try{
            System.out.println(email);
            System.out.println(profileImage);
           SignupRequest signupRequest = new SignupRequest();
            signupRequest.setEmail(email);
            signupRequest.setNickName(nickName);
            signupRequest.setPassword(password);
            signupRequest.setProfileImage(profileImage);

        long userId = authService.createIdentity(signupRequest);

        return ResponseEntity.status(HttpStatus.OK).body(userId);
        }
        catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity signIn(@RequestParam String email,
                                 @RequestParam String password,
                                 HttpServletResponse res){
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()){  // 매칭 되는 email 없으면 401 Unauthorized
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(ServletUriComponentsBuilder
                            .fromHttpUrl(loginUrl + "?err=Unauthorized")
                            .build().toUri()).build();
        }  // email 확인 if문

        boolean isVerified = hash.verifyHash(password, user.get().getSecret());

        if (!isVerified){  //secret 일치여부 확인 후 일치하지 않으면 401 Unauthorized 반환
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(ServletUriComponentsBuilder
                            .fromHttpUrl(loginUrl + "?err=Unauthorized")
                            .build().toUri()).build();
        }
        String token = jwt.createToken(
                user.get().getId());
        System.out.println("토큰------------------------" + token);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge((int)(jwt.TOKEN_TIMEOUT/1000));
        cookie.setDomain(this.cookieDomain);
        res.addCookie(cookie);
    return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(ServletUriComponentsBuilder
                    .fromHttpUrl(homeUrl)
                    .build().toUri())
            .build();
    } // sign in 괄호
}
