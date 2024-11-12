package com.itrihua.health_api_demo.configuration;

import com.baidubce.qianfan.Qianfan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QianfanConfig {

    @Value("${QIANFAN.QIANFAN_ACCESS_KEY}")
    private String QIANFAN_ACCESS_KEY;

    @Value("${QIANFAN.QIANFAN_SECRET_KEY}")
    private String QIANFAN_SECRET_KEY;

    @Bean
    public Qianfan qianfan(){
        return new Qianfan(QIANFAN_ACCESS_KEY,QIANFAN_SECRET_KEY);
    }
}
