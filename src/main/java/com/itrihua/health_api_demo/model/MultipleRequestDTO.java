package com.itrihua.health_api_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 多轮对话,前端请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultipleRequestDTO {
    private String message;
    private Long sessionId;
}
