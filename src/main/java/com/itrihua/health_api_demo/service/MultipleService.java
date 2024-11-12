package com.itrihua.health_api_demo.service;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.core.builder.ChatBuilder;
import com.baidubce.qianfan.model.chat.ChatResponse;
import com.itrihua.health_api_demo.context.UserContext;
import com.itrihua.health_api_demo.mapper.MessagesMapper;
import com.itrihua.health_api_demo.mapper.SessionMapper;
import com.itrihua.health_api_demo.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MultipleService {

    @Value("${QIANFAN.model}")
    private String model;

    private final Logger logger = LoggerFactory.getLogger(MultipleService.class);

    private final MessagesMapper messagesMapper;
    private final Qianfan qianfan;
    private final UserContext userContext;
    private final SessionMapper sessionMapper;

    public MultipleService(MessagesMapper messagesMapper, Qianfan qianfan, UserContext userContext, SessionMapper sessionMapper) {
        this.messagesMapper = messagesMapper;
        this.qianfan = qianfan;
        this.userContext = userContext;
        this.sessionMapper = sessionMapper;
    }

    /**
     * 多轮对话 -- 最多支持大约110条对话,50条内较为适宜,遍历时长大约为0.035ms
     */
    @Transactional
    public String multipleChat(Messages messagesByUser) {
        //  获取sessionId
        //  先将数据添加到数据库,再提取全部数据,进行多轮对话
        //  获取对话数据
        //  创建对话,并进行多轮对话,获取回复
        //  将结果插入数据库
        //  返回机器回复

        Long sessionId = messagesByUser.getSessionId();
        messagesMapper.addMessage(messagesByUser);

        List<MessagesVO> PastMessages = messagesMapper.getMessage(messagesByUser.getSessionId());
        ChatBuilder builder = qianfan.chatCompletion().model(model);
        for (MessagesVO pastMessage : PastMessages) {
            builder.addMessage(pastMessage.getSender(),pastMessage.getMessage());
        }
        ChatResponse response = builder.execute();

        logger.info("response:{}",response.getResult());

        Messages ResponseMessages = Messages.builder()
                .sessionId(sessionId)
                .sender("assistant")
                .message(response.getResult())
                .isMe(false)
                .createdAt(LocalDateTime.now())
                .build();

        messagesMapper.addMessage(ResponseMessages);

        return response.getResult();
    }

    /**
     * 增加新会话
     */
    @Transactional
    public Long createSession(String name) {
        String openId = userContext.getCurrentId();

        Session session = new Session();
        session.setOpenId(openId);
        session.setName(name);
        session.setCreatedAt(LocalDateTime.now());

        sessionMapper.createSession(session);
        Long id = session.getId();
        logger.info("id:{}",id);

        return id;
    }

    /**
     * 获取会话
     */
    public List<SessionVO> getSession() {

        List<SessionVO> voList = sessionMapper.getSession(userContext.getCurrentId());

        logger.info("voList_session_id:{}",voList);

        return voList;
    }

    /**
     * 获取聊天记录信息
     * @param sessionId
     * @return
     */
    public List<MessagesVO> getMessages(Long sessionId) {

        List<MessagesVO> messagesVOList = messagesMapper.getMessage(sessionId);
        logger.info("messagesVOList:{}",messagesVOList);

        return messagesVOList;
    }
}
