package com.itrihua.health_api_demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Messages {

    private Long id;
    private Long sessionId;
    private String sender;
    private String message;
    private boolean isMe;
    private LocalDateTime createdAt;

}
