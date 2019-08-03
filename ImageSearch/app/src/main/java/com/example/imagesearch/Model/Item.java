package com.example.imagesearch.Model;

public class Item {
    String title;
    String thumbnailURL;
    String docURL;
    int width;
    int height;


    public Item(String thumbnailURL, String docURL) {
        //super();
        this.thumbnailURL = thumbnailURL;
        this.docURL = docURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setDocURL(String docURL) {
        this.docURL = docURL;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getDocURL() {
        return docURL;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
