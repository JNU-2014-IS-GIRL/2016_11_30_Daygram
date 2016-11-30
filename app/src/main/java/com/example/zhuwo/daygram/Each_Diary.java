package com.example.zhuwo.daygram;

import java.io.Serializable;

public class Each_Diary implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;
    private String id;
    private String date;
    private String week;
    private String content;

    public Each_Diary(String id, String date, String week,
                      String content) {
        this.id = id;
        this.date = date;
        this.week = week;
        this.content = content;
    }

    public Each_Diary() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}