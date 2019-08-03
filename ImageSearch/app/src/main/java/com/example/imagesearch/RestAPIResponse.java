package com.example.imagesearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class RestAPIResponse {
    @SerializedName("document")
    @Expose
    Document document;
    @SerializedName("meta")
    @Expose
    Meta meta;

    public class Document{
        public List<info> info = new ArrayList<>();
        public List<info> getInfo() {return info;}
        public class info{
            @SerializedName("doc_url") @Expose String doc_url;
            @SerializedName("thumbnail_url") @Expose String thumbnail_url;
            @SerializedName("width") @Expose int width;
            @SerializedName("height") @Expose int height;

            public String getDoc_url() {return doc_url;}
            public String getThumbnail_url() {return thumbnail_url;}
        }
    }

    public class Meta{
        @SerializedName("is_end") @Expose boolean isEnd;
        @SerializedName("pageable_count") @Expose int pageCount;
        @SerializedName("total_count") @Expose int totalCount;

        public boolean getIsEnd() {return isEnd;}
        public int getPageCount() {return pageCount;}
        public int getTotalCount() {return totalCount;}
    }
    public Document getDocument() {return document;}
    public  Meta getMeta() {return meta;}
}
