package com.example.firstlayout.POJO;

public class NewsModel {
    public static final int IMAGETYPE=0;
    public static final int TEXTTYPE=1;
    public static final int TEXTONLY=2;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int type;

    public NewsModel(String mainHeading, String heading, String time,int images,int type) {
        this.mainHeading = mainHeading;
        this.heading = heading;
        this.time = time;
        this.images=images;
        this.type=type;
    }

    public String getMainHeading() {
        return mainHeading;
    }

    public void setMainHeading(String mainHeading) {
        this.mainHeading = mainHeading;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String mainHeading,heading,time;

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    int images;
}
