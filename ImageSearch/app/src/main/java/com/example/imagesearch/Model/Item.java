package com.example.imagesearch.Model;

public class Item {
    //이미지 검색 후 결과 데이터 클래스
    String thumbnailURL;
    String docURL;
    String imgURL;
    int width;
    int height;
    String sitename;
    String date;

    public Item(String thumbnailURL, String docURL, String imgURL, int width, int height, String sitename, String date) {
        this.thumbnailURL = thumbnailURL;
        this.docURL = docURL;
        this.imgURL = imgURL;
        this.width = width;
        this.height = height;
        this.sitename = sitename;
        this.date =date;
    }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getDocURL() {
        return docURL;
    }

    public String getSitename() {
        return sitename;
    }

    public String getDate() {
        return date;
    }
}
