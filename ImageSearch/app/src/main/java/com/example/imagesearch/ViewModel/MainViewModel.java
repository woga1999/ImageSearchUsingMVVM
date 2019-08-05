package com.example.imagesearch.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.imagesearch.Model.MetaData;
import com.example.imagesearch.Model.Item;
import com.example.imagesearch.Model.RestAPIService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Item>> imageList;
    String TAG = "MainViewModel";

    public LiveData<ArrayList<Item>> getData(String query){

        imageList = new MutableLiveData<>();
        loadRestAPI(query);
        //원래 imagelist가 null이 아닐때는 new를 하지 않는데 재검색을 위해.
        return imageList;
    }
    public int totalCount(){
        return MetaData.getInstance().getTotalCount();
    }

    private void loadRestAPI(String query){
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RestAPIService.Url)
                .build();
        RestAPIService api = retrofit.create(RestAPIService.class);
        Call<JsonObject> call = api.getResult(RestAPIService.API_KEY, query, RestAPIService.size);
        Log.e(TAG, call.toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject result = response.body();
                JsonArray document = (JsonArray) result.get("documents");

                JsonObject metaObject = (JsonObject) result.get("meta");
                boolean isEnd = metaObject.get("is_end").getAsBoolean();
                int pageCount = metaObject.get("pageable_count").getAsInt();
                int totalCount = metaObject.get("total_count").getAsInt();
                MetaData.getInstance().setIsEnd(isEnd);
                MetaData.getInstance().setTotalCount(totalCount);

                ArrayList<Item> imageItem = new ArrayList<>();

                if(document != null){ //검색결과 0이 아닐 때
                    for(int i=0; i<document.size(); i++){
                        JsonElement documentElement = document.get(i);
                        JsonObject docuObject = documentElement.getAsJsonObject();
                        String thumbnail_url = docuObject.get("thumbnail_url").getAsString();
                        String img_url = docuObject.get("image_url").getAsString();
                        String doc_url = docuObject.get("doc_url").getAsString();
                        int width = docuObject.get("width").getAsInt();
                        int height = docuObject.get("height").getAsInt();
                        String sitename = docuObject.get("display_sitename").getAsString();
                        String originalDate = docuObject.get("datetime").getAsString();
                        String dateTime = originalDate.substring(0 ,originalDate.lastIndexOf("T"));

                        imageItem.add(new Item(thumbnail_url,doc_url,img_url, width, height, sitename, dateTime));
                    }
                    imageList.setValue(imageItem);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
