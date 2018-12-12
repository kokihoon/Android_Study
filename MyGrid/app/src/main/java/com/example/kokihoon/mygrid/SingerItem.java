package com.example.kokihoon.mygrid;

public class SingerItem {

    private String name;
    private String mobile;
    int resId;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public SingerItem(String name, String mobile, int resId) {
        this.name = name;
        this.mobile = mobile;
        this.resId =resId;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    @Override
    public String toString() {
        return "SingerItem{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", resId=" + resId +
                '}';
    }
}
