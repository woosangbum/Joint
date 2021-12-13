package com.example.joint;

public class UserPurchase {
    private String id; // 구매번호
    private String studentId; // 학번
    private String itemId; // 물품 Id
    private String productCount; // 구매 개수
    private String productPrice; // 가격
    private String isReceipt;
    private String purchaseDate;

    public UserPurchase(String id, String studentId, String productId, String productCount,
                String productPrice, String isReceipt, String purchaseDate){
        this.id = id;
        this.studentId = studentId;
        this.itemId = productId;
        this.productCount = productCount;
        this.productPrice = productPrice;
        this.purchaseDate = purchaseDate;
        this.isReceipt = isReceipt;
    }

    public void setId(String id) { this.id = id; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setItemId(String itemId) { this.itemId = itemId; }
    public void setProductCount(String productCount) { this.productCount = productCount; }
    public void setProductPrice(String productPrice) { this.productPrice = productPrice; }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }
    public void setIsReceipt(String isReceipt) { this.isReceipt = isReceipt; }

    public String getId() { return id;}
    public String getStudentId() { return studentId; }
    public String getIsReceipt() { return isReceipt; }
    public String getPurchaseDate() { return purchaseDate; }
    public String getProductPrice() { return productPrice; }
    public String getItemId() { return itemId; }
    public String getProductCount() { return productCount; }
}
