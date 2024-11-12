package com.itrihua.health_api_demo.mapper;

import com.itrihua.health_api_demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 查询id
     * @param openId 用户凭证
     * @return id
     */
    @Select("SELECT id from user WHERE openId = #{openid}")
    Long getId(String openId);

    @Insert("INSERT INTO user (name, openId, icon_url) VALUES (#{user.name},#{user.openId},#{user.icon_url})")
    void addUser(@Param("user") User user);
}
