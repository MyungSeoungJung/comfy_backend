package com.comfy_backend.comment;

import com.comfy_backend.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class StudyCommentDto {

    @Id
    private long id;
    private String userNickName;
    private long userId;
    private String content;

    @Column(columnDefinition = "LONGTEXT")
    private String userImg;
}
