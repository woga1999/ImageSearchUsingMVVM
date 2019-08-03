package com.example.imagesearch;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.imagesearch.Model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Item> imageItems;
    String TAG = "RecyclerAdapter";


    public RecyclerAdapter(Context context, ArrayList<Item> imageItems) {
        this.context = context;
        this.imageItems = imageItems;
    }
    //추가된 부분 notifyDataSet부분
    public void updateData(ArrayList<Item> viewModels) {
        Log.e(TAG, "어답터 업데함수");
        imageItems.addAll(viewModels);
        notifyDataSetChanged();
    }

    public void clearData(ArrayList<Item> items){
        imageItems.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Item item = imageItems.get(position);
        //holder.thumbnail.setImageResource(R.drawable.app_icon);
        Picasso.with(context).load(item.getThumbnailURL()).into(holder.thumbnail);
    }



    @Override
    public int getItemCount() {
        return imageItems == null ? 0: imageItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.imageView);
            linearLayout = itemView.findViewById(R.id.linearItem);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }


}
