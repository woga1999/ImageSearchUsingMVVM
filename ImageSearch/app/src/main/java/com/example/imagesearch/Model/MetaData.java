package com.example.imagesearch.Model;

public class MetaData {
    private static MetaData metaData;
    boolean is_end ;
    int totalCount;
    int pageCount;

    public static MetaData getInstance(){
        if(metaData == null){
            metaData = new MetaData();
        }
        return metaData;
    }

    public boolean getIsEnd() {
        return is_end;
    }

    public void setIsEnd(boolean is_end) {
        this.is_end = is_end;
    }
    public int getTotalCount(){
        return totalCount;
    }

    public void setTotalCount(int totalCount){
        this.totalCount = totalCount;
    }
    public void setPageCount(int pageCount){this.pageCount = pageCount; }
    public int getPageCount(){return pageCount;}
}
