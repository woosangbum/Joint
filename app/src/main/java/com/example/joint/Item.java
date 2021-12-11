package com.example.joint;

public class Item {
    private String id;
    private String name;
    private int icon;
    private String deadlineDate;

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) { this.name = name; }
    public void setIcon(int icon) {
        this.icon = icon;
    }
    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public int getIcon() {
        return this.icon;
    }
    public String getDeadlineDate() {
        return this.deadlineDate;
    }
}
