package com.comfy_backend.user.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModifyUserDto {

    private String nickname;

    private String introduction;
    @Column(columnDefinition = "LONGTEXT")
    private String userImg;

}

