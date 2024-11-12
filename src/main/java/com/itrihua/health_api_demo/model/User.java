package com.itrihua.health_api_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private String openId;
    private String phoneNumber;
    private String icon_url;

    /**
     * 载入openid,名称与头像使用默认头像
     * @param openId 用户凭证
     */
    public User(String openId,String name,String icon_url){
        this.openId = openId;
        this.name = name;
        this.icon_url = icon_url;
    }

}
