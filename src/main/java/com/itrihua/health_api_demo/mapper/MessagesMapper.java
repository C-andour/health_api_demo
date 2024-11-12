package com.itrihua.health_api_demo.mapper;

import com.itrihua.health_api_demo.model.Messages;
import com.itrihua.health_api_demo.model.MessagesVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessagesMapper {

    /**
     * 插入数据
     */
    void addMessage(Messages messages);

    /**
     *
     * @param sessionId
     * @return
     */
    List<MessagesVO> getMessage(Long sessionId);
}
