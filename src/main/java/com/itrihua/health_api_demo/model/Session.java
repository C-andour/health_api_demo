package com.itrihua.health_api_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 会话
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private Long id;
    private String name;
    private String openId;
    private LocalDateTime createdAt;
}
