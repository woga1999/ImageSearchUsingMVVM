package com.example.imagesearch.Model;

public class MetaData {
    private static MetaData metaData;
    public boolean is_end ;
    public int totalCount;

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
}
