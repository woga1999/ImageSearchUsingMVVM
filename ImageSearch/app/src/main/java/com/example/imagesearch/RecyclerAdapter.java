package com.example.imagesearch;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<Item> imageItems;
    String TAG = "RecyclerAdapter";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.imageView);
        }
    }

    public RecyclerAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.imageItems = items;
    }
    //추가된 부분 notifyDataSet부분
    public void updateData(ArrayList<Item> viewModels) {
        Log.e(TAG, "어답터 업데함수");
        imageItems.addAll(viewModels);
        notifyDataSetChanged();
    }
    public void addImageItem(String thumbnail, String doc){
        imageItems.add(new Item(thumbnail,doc));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Item item = imageItems.get(position);
        //holder.thumbnail.setImageResource(R.drawable.app_icon);
        Picasso.with(context).load(item.thumbnailURL).into(holder.thumbnail);
    }


    @Override
    public int getItemCount() {
        return this.imageItems.size();
    }



}
