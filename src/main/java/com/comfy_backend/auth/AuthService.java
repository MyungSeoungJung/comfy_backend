package com.comfy_backend.auth;

import com.comfy_backend.auth.request.SignupRequest;
import com.comfy_backend.auth.util.Hash;
import com.comfy_backend.user.entity.User;
import com.comfy_backend.user.entity.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AuthService {
    private UserRepository userRepository;
    private final String POST_FILE_PATH = "files/profileImage";
    @Autowired
    Hash hash;
    @Autowired
    public AuthService(UserRepository userRepository){
    this.userRepository = userRepository;
    }
    @Transactional(rollbackOn = IOException.class)
    public long createIdentity(SignupRequest signupReq) throws IOException {
        try{
            User user = User.builder()
                    .nickName(signupReq.getNickName())
                    .email(signupReq.getEmail())
                    .secret(hash.createHash(signupReq.getPassword()))
                    .profileImage(ChangeUUIDImage(signupReq.getProfileImage()))
                    .build();

            userRepository.save(user);
            return user.getId();
        }
        catch (IOException e) {
            throw new IOException("회원가입 중 파일 처리 오류 발생", e);
        }
    }

    public String ChangeUUIDImage (MultipartFile file) throws IOException{
        try {
            Path dirPath = Paths.get(POST_FILE_PATH);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);

            // UUID를 사용하여 파일명 생성
            String mainImgUuid = UUID.randomUUID().toString() + "." + extension;

            byte[] fileBytes = file.getBytes();
            Path filePath = dirPath.resolve(mainImgUuid);
            Files.write(filePath, fileBytes);

            return mainImgUuid;

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
