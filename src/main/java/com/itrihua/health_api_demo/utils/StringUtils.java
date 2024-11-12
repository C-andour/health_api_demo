package com.itrihua.health_api_demo.utils;

/**
 * 字符串工具类,判断是否为null,空字符串
 */
public class StringUtils {
    public static boolean isEmptyOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static void main(String[] args) {
        String openid1 = null;
        String openid2 = "";
        String openid3 = "   ";
        String openid4 = "content";

        System.out.println("openid1: " + (StringUtils.isEmptyOrBlank(openid1) ? "No content" : "Has content"));
        System.out.println("openid2: " + (StringUtils.isEmptyOrBlank(openid2) ? "No content" : "Has content"));
        System.out.println("openid3: " + (StringUtils.isEmptyOrBlank(openid3) ? "No content" : "Has content"));
        System.out.println("openid4: " + (StringUtils.isEmptyOrBlank(openid4) ? "No content" : "Has content"));
    }
}
