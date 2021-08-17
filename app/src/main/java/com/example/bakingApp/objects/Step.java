package com.example.bakingApp.objects;

import java.io.Serializable;

public class Step implements Serializable {
    private int Id;
    private String ShortDesc;
    private String Desc;
    private String VideoUrl;
    private String ThumbUrl;

    public Step(int id, String shortDesc, String desc, String videoUrl, String thumbUrl) {
        Id = id;
        ShortDesc = shortDesc;
        Desc = desc;
        VideoUrl = videoUrl;
        ThumbUrl = thumbUrl;
    }

    public int getId() {return Id;}
    public String getShortDesc() {return ShortDesc;}
    public String getDesc() {return Desc;}
    public String getVideoUrl() {return VideoUrl;}
    public String getThumbUrl() {return ThumbUrl;}
}
