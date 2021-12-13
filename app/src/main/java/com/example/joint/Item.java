package com.example.joint;

public class Item {
    private String id;
    private String name;
    private String icon;
    private String deadlineDate;
    private String content;
    private String targetNum;   // 목표 개수
    private String currNum;     // 현재 개수
    private String price;       // 정가
    private String discountPrice; // 할인가
    private String creationDate; // 생성일

    public Item(String id, String name, String icon, String deadlineDate,
                String content, String targetNum, String currNum, String price, String discountPrice, String creationDate ){
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.deadlineDate = deadlineDate;
        this.content = content;
        this.targetNum = targetNum;
        this.currNum = currNum;
        this.price = price;
        this.discountPrice = discountPrice;
        this.creationDate = creationDate;
    }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setIcon(String icon) { this.icon = icon; }
    public void setDeadlineDate(String deadlineDate) { this.deadlineDate = deadlineDate; }
    public void setContent(String content) { this.content = content; }
    public void setTargetNum(String targetNum) { this.targetNum = targetNum; }
    public void setCurrNum(String currNum) { this.currNum = currNum; }
    public void setPrice(String price) { this.price = price; }
    public void setDiscountPrice(String discountPrice) { this.discountPrice = discountPrice; }
    public void setCreationDate(String creationDate) { this.creationDate = creationDate; }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public String getIcon() { return this.icon; }
    public String getDeadlineDate() { return this.deadlineDate; }
    public String getContent() { return this.content; }
    public String getTargetNum() { return this.targetNum; }
    public String getCurrNum() { return this.currNum; }
    public String getPrice() { return this.price; }
    public String getDiscountPrice() { return this.discountPrice; }
    public String getCreationDate() { return this.creationDate; }
}
