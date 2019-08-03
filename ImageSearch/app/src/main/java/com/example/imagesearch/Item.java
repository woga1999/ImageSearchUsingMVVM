package com.example.imagesearch;

public class Item {
    String title;
    String thumbnailURL;
    String docURL;

    public Item(String thumbnailURL, String docURL) {
        super();
        this.thumbnailURL = thumbnailURL;
        this.docURL = docURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setDocURL(String docURL) {
        this.docURL = docURL;
    }
}
