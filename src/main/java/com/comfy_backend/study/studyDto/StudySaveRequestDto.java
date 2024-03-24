package com.comfy_backend.study.studyDto;

import com.comfy_backend.study.entity.Study;
import com.comfy_backend.user.entity.User;
import lombok.Getter;
import java.util.List;

public class StudySaveRequestDto {
    private String title;

    private String content;
    private List<String> tagNames;

    public Study toEntity(User user) {
        return Study.builder()
                .title(title)
                .content(content)
                .creatorNickName(user.getNickName())
                .build();
    }

    public List<String> tagNames() {
        return this.tagNames;
    }
}

