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

            Picasso.with(context).load(item.getThumbnailURL()).into(thumbnail);
            String imageSIze = String.valueOf(item.getHeight())+" x " + String.valueOf(item.getWidth());
            textImageSize.setText(imageSIze);

            Picasso.with(context).load(item.getImgURL()).into(subImage);
            subTextDate.setText(item.getDate());
            subTextSiteName.setText(item.getSitename());
            subTextUrl.setText(item.getDocURL());
            changeVisibility(selectedItems.get(position));
            Log.e(TAG+"onBind", String.valueOf(position));
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linearItem:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
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
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 150;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            va.setDuration(300);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();

                    subImage.getLayoutParams().height = value;
                    subImage.requestLayout();

                    subTextDate.setTextSize(8);
                    subTextDate.setGravity(Gravity.CENTER);
                    subTextSiteName.setTextSize(10);
                    subTextSiteName.setGravity(Gravity.CENTER);
                    subTextUrl.setTextSize(10);
                    subTextUrl.setGravity(Gravity.CENTER);

                    // imageView 확장축속
                    subImage.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    //textview 확장축소
                    subTextDate.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    subTextSiteName.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    subTextUrl.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

                }
            });
            va.start();
        }
        public void intentPage(String url){
            Intent intent = new Intent(context, WebPageView.class);
            intent.putExtra("url",url);
            context.startActivity(intent);
        }
    }
}
