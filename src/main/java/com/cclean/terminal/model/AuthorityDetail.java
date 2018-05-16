package com.cclean.terminal.model;

import java.util.List;

public class AuthorityDetail {

    private Integer type;

    private String title;

    private List<String> content;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "authorityDetail{" +
                "type='" + type + '\'' +
                ", content=" + content +
                '}';
    }
}
