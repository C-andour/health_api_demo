package com.itrihua.health_api_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionVO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
