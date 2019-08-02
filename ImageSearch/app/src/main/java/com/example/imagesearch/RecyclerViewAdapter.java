//package com.example.imagesearch;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
//
//    Context context;
//    ArrayList<Item> list;
//
//    public RecyclerVIewAdapter(Context context, ArrayList<Item> list) {
//        super();
//        this.context = context;
//        this.list = list;
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.image.setImageResource(list.get(position).image);
//        holder.title.setText(list.get(position).title);
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder{
//
//        ImageView image;
//        TextView title;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.imageView);
//            title = itemView.findViewById(R.id.textView);
//        }
//    }
//}
