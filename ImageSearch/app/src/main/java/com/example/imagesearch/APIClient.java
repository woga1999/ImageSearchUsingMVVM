package com.example.imagesearch;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public Retrofit retrofit = null;
    String TAG = "APIClient";
    ArrayList<Item> imageList;
    Boolean isEnd;
    int pageCount;
    int totalCount;

    public APIClient(){

    }

    public void getData(String query){
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RestAPIService.Url)
                .build();
        RestAPIService api = retrofit.create(RestAPIService.class);
        Call<JsonObject> call = api.getResult(RestAPIService.API_KEY,query, RestAPIService.size);
        Log.e(TAG, call.toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject result = response.body();
                Log.e(TAG, result.toString());
                JsonArray document = (JsonArray) result.get("documents");
                Log.e(TAG, document.toString());

                JsonObject metaObject = (JsonObject) result.get("meta");
                isEnd = metaObject.get("is_end").getAsBoolean();
                pageCount = metaObject.get("pageable_count").getAsInt();
                totalCount = metaObject.get("total_count").getAsInt();
                Log.e(TAG, isEnd.toString());

                imageList = new ArrayList<>();
                for(int i=0; i<document.size(); i++){
                    JsonElement documentElement = document.get(i);
                    JsonObject docuObject = documentElement.getAsJsonObject();
                    String thumbnail_url = docuObject.get("thumbnail_url").getAsString();

                    String doc_url = docuObject.get("doc_url").getAsString();
                    imageList.add(new Item(thumbnail_url,doc_url));
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public ArrayList<Item> getImageArrayList(){
        return imageList;
    }
}
