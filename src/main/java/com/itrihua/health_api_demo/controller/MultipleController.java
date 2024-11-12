package com.itrihua.health_api_demo.controller;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.core.builder.ChatBuilder;
import com.baidubce.qianfan.model.chat.ChatResponse;
import com.itrihua.health_api_demo.model.*;
import com.itrihua.health_api_demo.service.MultipleService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MultipleController {

    @Value("${QIANFAN.model}")
    private String model;

    private final Logger logger = LoggerFactory.getLogger(MultipleController.class);

    private final String user = "user";
    private final String assistant = "assistant";

    /**
     * Spring Bean
     */
    private final Qianfan qianfan;
    private final MultipleService multipleService;

    MultipleController(Qianfan qianfan, MultipleService multipleService){
        this.qianfan = qianfan;
        this.multipleService = multipleService;
    }

    @GetMapping("/test")
    public String test1(){
        return "成功";
    }

    /**
     * 交流
     * @param multipleRequestDTO 前端请求体
     * @return
     */
    @PostMapping("/multichat")
    public ResponseResult multichat2(@RequestBody MultipleRequestDTO multipleRequestDTO){
        logger.info("问题 message:" + multipleRequestDTO.getMessage());

        /*封装message*/
        Messages messages = new Messages();
        messages.setSessionId(multipleRequestDTO.getSessionId());
        messages.setSender(user);
        messages.setMessage(multipleRequestDTO.getMessage());
        messages.setMe(true);
        messages.setCreatedAt(LocalDateTime.now());

        String response = multipleService.multipleChat(messages);

        return ResponseResult.okResult(response);
    }

    /**
     * 根据sessionid获取聊天记录
     * @return
     */
    @PostMapping("/getmessages")
    public ResponseResult getMessages(@RequestParam("sessionId") Long sessionId){
        logger.info("sessionId:{}",sessionId);
        List<MessagesVO> messages = multipleService.getMessages(sessionId);

        return ResponseResult.okResult(messages);
    }

    /**
     * 创建会话
     * @param name
     * @return
     */
    @PostMapping("/opensession")
    public ResponseResult createSession(@RequestParam("name") String name){
        logger.info("新会话名称:{}",name);
        Long Id = multipleService.createSession(name);

        return ResponseResult.okResult(Id);
    }

    /**
     * 获取会话记录
     * @return
     */
    @GetMapping("/getsession")
    public ResponseResult getSession(){
        logger.info("获取会话记录");
        List<SessionVO> voList = multipleService.getSession();

        return ResponseResult.okResult(voList);
    }



}
