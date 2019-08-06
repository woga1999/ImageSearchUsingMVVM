package com.example.imagesearch;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.imagesearch.Model.Item;
import com.example.imagesearch.View.WebPageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Item> imageItems;
    String TAG = "RecyclerAdapter";
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    public RecyclerAdapter(Context context, ArrayList<Item> imageItems) {
        this.context = context;
        this.imageItems = imageItems;
    }

    //결과 더 보여주기 위한 함수
    public void updateData(ArrayList<Item> viewModels) {
        imageItems.addAll(viewModels);
        notifyDataSetChanged();
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
        holder.onBind(item, position);
    }



    @Override
    public int getItemCount() {
        return imageItems == null ? 0: imageItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout linearLayout;
        ImageView thumbnail;
        TextView textImageSize;
        ImageView subImage;
        TextView subTextUrl;
        TextView subTextDate;
        TextView subTextSiteName;

        private Item item;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.imageView);
            textImageSize = itemView.findViewById(R.id.textImageSize);
            subImage = itemView.findViewById(R.id.subImageView);
            subTextUrl = itemView.findViewById(R.id.subTextView);
            subTextDate = itemView.findViewById(R.id.subTextDate);
            subTextSiteName = itemView.findViewById(R.id.subTextSiteName);

            linearLayout = itemView.findViewById(R.id.linearItem);
            linearLayout.setOnClickListener(this);
            thumbnail.setOnClickListener(this);
            subImage.setOnClickListener(this);
            subTextUrl.setOnClickListener(this);
        }
        void onBind(Item item, int position){
            this.item = item;
            this.position = position;
            //이미지는 피카소 라이브러리 사용해서 set하고 텍스트는 setText 이용
            //리스트 뷰
            Picasso.with(context).load(item.getThumbnailURL()).into(thumbnail);
            String imageSIze = String.valueOf(item.getWidth())+" x " + String.valueOf(item.getHeight());
            textImageSize.setText(imageSIze);

            //expandable 리스트
            Picasso.with(context).load(item.getImgURL()).into(subImage);
            subTextDate.setText(item.getDate());
            subTextSiteName.setText(item.getSitename());
            subTextUrl.setText(item.getDocURL());

            //onClick한 리스트뷰 확장시키거나 축소시키는 함수
            changeVisibility(selectedItems.get(position));
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linearItem:
                    if (selectedItems.get(position)) {
                        Log.e(TAG, "확장");
                        //리스템 아이템 축소
                        selectedItems.delete(position);
                    } else {
                        //리스트 아이템 확장
                        //전에 선택한 리스트 값 지우고 현재 리스트 값 넣기
                        selectedItems.delete(prePosition);
                        selectedItems.put(position, true);
                    }
                    //해당 포지션의 변화 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    prePosition = position;
                    break;
                case R.id.subImageView:
                    intentPage(item.getDocURL());
                    break;
                case R.id.subTextView:
                    intentPage(item.getDocURL());
                    break;
            }
        }
        private void changeVisibility(final boolean isExpanded) {
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            va.setDuration(300);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();

                    subImage.getLayoutParams().height = value;
                    subImage.requestLayout();

                    subTextDate.setTextSize(8);
                    subTextDate.setGravity(Gravity.CENTER);
                    subTextSiteName.setTextSize(10);
                    subTextSiteName.setGravity(Gravity.CENTER);
                    subTextUrl.setTextSize(10);
                    subTextUrl.setGravity(Gravity.CENTER);

                    //imageView 확장축소
                    subImage.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    //textview 확장축소
                    subTextDate.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    subTextSiteName.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    subTextUrl.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            va.start();
        }

        //확장된 아이템 subImageView, subTextUrl 클릭 시 웹뷰 열기
        public void intentPage(String url){
            Intent intent = new Intent(context, WebPageView.class);
            intent.putExtra("url",url);
            context.startActivity(intent);
        }
    }
}
