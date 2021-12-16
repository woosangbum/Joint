package com.example.joint;

public class Notification {
    private String date;
    private String content;
    private String studentId;

    public Notification(String date, String content, String studentId){
        this.content = content;
        this.date = date;
        this.studentId = studentId;
    }
    public String getStudentId(){return studentId;}

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getContent(){ return content; }

    public void setContent(String content){
        this.content = content;
    }


    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }
}