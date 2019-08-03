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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagesearch.Model.Item;
import com.example.imagesearch.R;
import com.example.imagesearch.RecyclerAdapter;
import com.example.imagesearch.ViewModel.MainViewModel;

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
                //Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean menuSelectionHandeled = super.onOptionsItemSelected(item);
        // menu_search is the id of the menu item in the ActionBar
        if (item.getItemId() == R.id.app_bar_search) {
            mInputManager.showSoftInput(null, InputMethodManager.SHOW_IMPLICIT);
            Log.e(TAG, "서치 버튼 눌렸삼");
        }
        return menuSelectionHandeled;
    }

    private void initViews(){
        recyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void setupAdapter(String query){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getData(query).observe(this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                adapter = new RecyclerAdapter(getApplicationContext(), items);
                imageList = items;
                recyclerView.setAdapter(adapter);
                Log.e(TAG, "업데이트");
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
