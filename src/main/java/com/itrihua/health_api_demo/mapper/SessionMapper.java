package com.itrihua.health_api_demo.mapper;

import com.itrihua.health_api_demo.model.Session;
import com.itrihua.health_api_demo.model.SessionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface SessionMapper {

    void createSession(Session session);

    @Select("SELECT id FROM session WHERE openId = #{openId} AND created_at = #{createdAt}")
    Long getId(Session session);

    List<SessionVO> getSession(@Param("openId") String openId);
}
