package com.example.joint;

public class User {
    private String name;
    private String phoneNumber;
    private String email;
    private String studentId;

    public User(String name, String phoneNumber, String email, String studentId){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.studentId = studentId;
    }
    public String getStudentId(){return studentId;}

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail(){ return email; }

    public void setEmail(String email){
        this.email = email;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
}
