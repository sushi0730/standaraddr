package com.sushi.pojo;

/**
 * 规则Pojo
 * @Author sushi
 * @create 2019-11-16 4:48 PM
 */
public class Rule {
    //规则的内容
    private String content;
    //规则的类型
    private String type;

    public Rule(String type) {
        this.type = type;
    }

    public Rule() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
