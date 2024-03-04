package com.comfy_backend.auth.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest {
    private String email;
    private String nickName;
    private String password;
}
