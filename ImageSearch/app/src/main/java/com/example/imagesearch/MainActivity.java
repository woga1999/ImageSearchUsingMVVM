package com.example.imagesearch;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    MenuItem mSearch;
    String TAG = "MainActivity";
    APIClient client = null;
    RecyclerView recyclerView = null;
    RecyclerAdapter adapter = null;
    public Retrofit retrofit = null;
    ArrayList<Item> imageList;
    Boolean isEnd;
    int pageCount;
    int totalCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("박효완");
        client = new APIClient();


//        imageList.add(new Item("https://search4.kakaocdn.net/argon/130x130_85_c/9d3F8Bwbei3","http://filternews.tistory.com/225"));
//        imageList.add(new Item("https://search4.kakaocdn.net/argon/130x130_85_c/4Key6MXTFf9","http://filternews.tistory.com/225"));
//        imageList.add(new Item("https://search4.kakaocdn.net/argon/130x130_85_c/C9TzArGy0xR","http://filternews.tistory.com/225"));
        initViews();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu) ;
        mSearch=menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView)mSearch.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                //검색 버튼이 눌리면
                Toast.makeText(getApplicationContext(), "버튼눌렸고"+query, Toast.LENGTH_LONG).show();
                getData(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stu
                //검색창에 글자를 쓰면 여기로 옴
                //Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return true;
    }

    private void initViews(){
        recyclerView = findViewById(R.id.recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        imageList = new ArrayList<>();
        adapter = new RecyclerAdapter(getApplicationContext(),imageList);
        recyclerView.setAdapter(adapter);
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
                adapter.updateData(imageList);
                Log.e(TAG, "어답터 움직임");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
