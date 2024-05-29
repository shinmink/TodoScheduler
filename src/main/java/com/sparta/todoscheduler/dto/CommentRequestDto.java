package com.sparta.todoscheduler.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    private String content;
    private String username;
    private Long schedulerId; // 이 필드는 컨트롤러에서 설정될 것입니다.
}
