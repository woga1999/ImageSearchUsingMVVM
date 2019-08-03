package com.example.imagesearch;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class RestAPIResponse {
    @SerializedName("document")
    Document document;
    @SerializedName("meta")
    Meta meta;

    public class Document{
        public List<info> info = new ArrayList<>();
        public List<info> getInfo() {return info;}
        public class info{
            @SerializedName("doc_url") String doc_url;
            @SerializedName("thumbnail_url") String thumbnail_url;
            @SerializedName("width") int width;
            @SerializedName("height") int height;

            public String getDoc_url() {return doc_url;}
            public String getThumbnail_url() {return thumbnail_url;}
        }
    }

    public class Meta{
        @SerializedName("is_end") boolean isEnd;
        @SerializedName("pageable_count") int pageCount;
        @SerializedName("total_count") int totalCount;

        public boolean getIsEnd() {return isEnd;}
        public int getPageCount() {return pageCount;}
        public int getTotalCount() {return totalCount;}
    }
    public Document getDocument() {return document;}
    public  Meta getMeta() {return meta;}
}
