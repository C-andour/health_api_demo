package com.itrihua.health_api_demo.model;

import lombok.Data;

@Data
public class chatRequest {
    private String problem;

    public String getProblem(){
        return problem;
    }

    public void setProblem(String problem){
        this.problem = problem;
    }
}
