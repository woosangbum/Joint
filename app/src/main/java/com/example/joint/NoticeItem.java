package com.example.joint;

import java.time.LocalDate;

public class NoticeItem {
//    private String id;
    private String title;
    private String content;
    private String date;

//    public NoticeItem(String id, String title, String content){
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.date = LocalDate.now().toString();
//    }
    public NoticeItem(String title, String date, String content){
        this.title = title;
        this.content = content;
        this.date = date;
    }
//    public void setId(String id) {
//        this.id = id;
//    }
    public void setTitle(String title) { this.title = title ; }
    public void setContent(String content) {
        this.content = content ;
    }
    public void setDate(String date) {
        this.date = date ;
    }

//    public String getId() {
//        return this.id;
//    }
    public String getTitle() {
        return this.title ;
    }
    public String getContent() {
        return this.content ;
    }
    public String getDate() {
        return this.date ;
    }
}
