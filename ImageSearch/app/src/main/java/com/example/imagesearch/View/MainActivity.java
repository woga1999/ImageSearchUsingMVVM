package com.example.imagesearch.View;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imagesearch.Model.Item;
import com.example.imagesearch.Model.MetaData;
import com.example.imagesearch.OnSwipeTouchListener;
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
    InputMethodManager mInputManager;
    SearchView searchView;
    OnSwipeTouchListener onSwipeTouchListener;
    String searchedQuery;
    int pageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("박효완");
        Log.e(TAG,"Create");
        mInputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        pageCount = 1;

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
                searchedQuery = query;
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
        LinearLayoutManager linerLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linerLayoutManager);
        onSwipeTouchListener = new OnSwipeTouchListener(getApplicationContext()) {
            //아래에서 위로 스와이프 이벤트
            public void onSwipeTop() {
                if(searchedQuery != null){
                    udateAdapter(searchedQuery, ++pageCount);
                }
            }
            //위에서 아래로 스와이프 이벤트
            public void onSwipeBottom() {
                //키보드 내려가기
                mInputManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
            }
        };
        //스와이프 터치 리스너 추가
        recyclerView.setOnTouchListener(onSwipeTouchListener);
    }

    //스와이프 액션과 스크롤 액션 둘 다 가능하게 하기 위한 함수
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        onSwipeTouchListener.getGestureDetector().onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void setupAdapter(String query){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getData(query,1).observe(this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                adapter = new RecyclerAdapter(getApplicationContext(), items);
                recyclerView.setAdapter(adapter);
                pageCount = 1;
                //검색 결과가 없을 시 스낵바 이벤트
                if(MetaData.getInstance().getTotalCount() == 0){
                    Snackbar.make(recyclerView, "검색결과가 없습니다.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void udateAdapter(String query, int page){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        if(page < MetaData.getInstance().getPageCount()) {
            viewModel.getData(query, page).observe(this, new Observer<ArrayList<Item>>() {
                @Override
                public void onChanged(ArrayList<Item> items) {
                    adapter.updateData(items);
                    Log.e(TAG, "업데이트");
                }
            });
        }
    }

}
