package com.comfy_backend.study.studyDto;

import com.comfy_backend.study.entity.Study;
import com.comfy_backend.user.entity.User;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StudySaveRequestDto {
    private String title;

    private String content;
    private List<String> tagNames;

    public Study toEntity(User user) {
        return Study.builder()
                .title(this.title)
                .content(this.content)
                .creatorNickName(user.getNickName())
                .recruitStatus("모집중")
                .createdTime(LocalDateTime.now())
                .creatorId(user.getId())
//               모집중
                .build();
    }

    public List<String> tagNames() {
        return this.tagNames;
    }
}

