package com.example.imagesearch.View;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagesearch.Model.MetaData;
import com.example.imagesearch.Model.Item;
import com.example.imagesearch.R;
import com.example.imagesearch.RecyclerAdapter;
import com.example.imagesearch.ViewModel.MainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    MenuItem mSearch;
    String TAG = "MainActivity";
    RecyclerView recyclerView = null;
    RecyclerAdapter adapter = null;
    ArrayList<Item> imageList;
    InputMethodManager mInputManager;
    boolean first = false;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("박효완");
        Log.e(TAG,"Create");
        mInputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        initViews();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu) ;
        mSearch=menu.findItem(R.id.app_bar_search);
        searchView = (SearchView)mSearch.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                //검색 버튼이 눌리면
                setupAdapter(query);
                Log.e(TAG, "검색 버튼 press");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stu
                //검색창에 글자를 쓰면 여기로 옴
                return true;
            }
        });

        return true;
    }

    private void initViews(){
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void setupAdapter(String query){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getData(query).observe(this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                adapter = new RecyclerAdapter(getApplicationContext(), items);
                recyclerView.setAdapter(adapter);
                if(MetaData.getInstance().getTotalCount() == 0){
                    Snackbar.make(recyclerView, "검색결과가 없습니다.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
    private void udateAdapter(String query){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getData(query).observe(this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                adapter.updateData(items);
                Log.e(TAG, "업데이트");
            }
        });
    }

}
