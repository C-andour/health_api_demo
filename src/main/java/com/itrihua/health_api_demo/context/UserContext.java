package com.itrihua.health_api_demo.context;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    private final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public void setCurrentId(String openId){
        threadLocal.set(openId);
    }
    public String getCurrentId(){
        return threadLocal.get();
    }
    public void removeCurrentId(){
        threadLocal.remove();
    }
}
