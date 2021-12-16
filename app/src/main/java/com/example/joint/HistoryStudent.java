package com.example.joint;

public class HistoryStudent {
    private String name;
    private String studentId;
    private String productCount;
    private String isChecked;
    private String productId;

    public HistoryStudent(String name, String studentId, String productCount, String isChecked, String productId){
        this.name = name;
        this.productCount = productCount;
        this.studentId = studentId;
        this.isChecked = isChecked;
        this.productId = productId;
    }
    public String getStudentId(){return studentId;}
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getProductId(){return productId;}
    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getIsChecked(){
        return isChecked;
    }

    public void setIsChecked(String name){
        this.isChecked = isChecked;
    }
}
