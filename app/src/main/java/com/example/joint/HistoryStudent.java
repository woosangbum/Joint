package com.example.joint;

public class HistoryStudent {
    private String name;
    private String studentId;
    private String productCount;

    public HistoryStudent(String name, String studentId, String productCount){
        this.name = name;
        this.productCount = productCount;
        this.studentId = studentId;
    }
    public String getStudentId(){return studentId;}

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getProductCount(){ return productCount; }

    public void setProductCount(String email){
        this.productCount = productCount;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
