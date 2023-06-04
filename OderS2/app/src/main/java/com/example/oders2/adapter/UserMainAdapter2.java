package com.example.oders2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.R;
import com.example.oders2.activity.DeTelMealActivity;
import com.example.oders2.activity.TypeActivity;
import com.example.oders2.po.Cars;
import com.example.oders2.po.Meals;

import java.util.List;

public class UserMainAdapter2 extends RecyclerView.Adapter<UserMainAdapter2.ProductListItemViewHolder> {
    private Context mContext;
    private List<Meals> meals2;
    private LayoutInflater mLayoutInflater;
    String newurl;

    public UserMainAdapter2(Context context, List<Meals> datas) {
        mContext = context;
        meals2 = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UserMainAdapter2.ProductListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.foods_list_item3, parent, false);
        return new UserMainAdapter2.ProductListItemViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull UserMainAdapter2.ProductListItemViewHolder holder, int position) {
        int leftIndex = position * 2;
        int rightIndex = position * 2 + 1;

        // 显示左侧商品信息
        if (leftIndex < meals2.size()) {
            final Meals leftProduct = meals2.get(leftIndex);
            holder.mTvLeftName.setText(leftProduct.getName());
            holder.mTvLeftLabel.setText(leftProduct.getDesccription());
            holder.mTvLeftPrice.setText(leftProduct.getPrice());
            Glide.with(mContext).load(Constants.url+leftProduct.getUrl1().replace("\\","/")).into(holder.mIvLeftImage);
            holder.mLeftProductLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 处理点击事件
                    String mealName = leftProduct.getName();
                    System.out.println(mealName);
                    Intent intent = new Intent(mContext, DeTelMealActivity.class);
                    intent.putExtra("item", mealName);
                    mContext.startActivity(intent);
                    // 这里可以根据需要获取其他值
                    // 使用获取到的值进行后续操作
                }
            });
        } else {
            // 如果数据源中没有左侧商品的信息，则将左侧的商品信息 View 设置为隐藏
            holder.mLeftProductLayout.setVisibility(View.INVISIBLE);
        }

        // 显示右侧商品信息
        if (rightIndex < meals2.size()) {
            final Meals rightProduct = meals2.get(rightIndex);
            holder.mTvRightName.setText(rightProduct.getName());
            holder.mTvRightLabel.setText(rightProduct.getDesccription());
            holder.mTvRightPrice.setText(rightProduct.getPrice());
            Glide.with(mContext).load(Constants.url+rightProduct.getUrl1().replace("\\","/")).into(holder.mIvRightImage);
            holder.mRightProductLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 处理点击事件
                    String mealName = rightProduct.getName();
                    System.out.println(mealName);
                    Intent intent = new Intent(mContext, DeTelMealActivity.class);
                    intent.putExtra("item", mealName);
                    mContext.startActivity(intent);
                    // 这里可以根据需要获取其他值
                    // 使用获取到的值进行后续操作
                }
            });
        } else {
            // 如果数据源中没有右侧商品的信息，则将右侧的商品信息 View 设置为隐藏
            holder.mRightProductLayout.setVisibility(View.INVISIBLE);
        }
    }

/*
    @Override
    public void onBindViewHolder(@NonNull UserMainAdapter2.ProductListItemViewHolder holder, int position) {
        int leftIndex = position * 2;
        int rightIndex = position * 2 + 1;

        // 显示左侧商品信息
        if (leftIndex < meals2.size()) {
            Meals leftProduct = meals2.get(leftIndex);
            holder.mTvLeftName.setText(leftProduct.getName());
            holder.mTvLeftLabel.setText(leftProduct.getDesccription());
            holder.mTvLeftPrice.setText(leftProduct.getPrice());
            Glide.with(mContext).load(url+leftProduct.getUrl1().replace("\\","/")).into(holder.mIvLeftImage);
        } else {
            // 如果数据源中没有左侧商品的信息，则将左侧的商品信息 View 设置为隐藏
            holder.mLeftProductLayout.setVisibility(View.INVISIBLE);
        }

        // 显示右侧商品信息
        if (rightIndex < meals2.size()) {
            Meals rightProduct = meals2.get(rightIndex);
            holder.mTvRightName.setText(rightProduct.getName());
            holder.mTvRightLabel.setText(rightProduct.getDesccription());
            holder.mTvRightPrice.setText(rightProduct.getPrice());
            Glide.with(mContext).load(url+rightProduct.getUrl1().replace("\\","/")).into(holder.mIvRightImage);
        } else {
            // 如果数据源中没有右侧商品的信息，则将右侧的商品信息 View 设置为隐藏
            holder.mRightProductLayout.setVisibility(View.INVISIBLE);
        }
    }*/

    @Override
    public int getItemCount() {
        // 如果数据源的数量为奇数，那么最后剩下的一项就只能放在左侧，此时需要特殊处理
        if (meals2.size() % 2 != 0) {
            return meals2.size() / 2 + 1;
        } else {
            return meals2.size() / 2;
        }
    }

    public static class ProductListItemViewHolder extends RecyclerView.ViewHolder {
        private View mLeftProductLayout;
        private TextView mTvLeftName;
        private TextView mTvLeftLabel;
        private TextView mTvLeftPrice;
        private ImageView mIvLeftImage;

        private View mRightProductLayout;
        private TextView mTvRightName;
        private TextView mTvRightLabel;
        private TextView mTvRightPrice;
        private ImageView mIvRightImage;

        public ProductListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mLeftProductLayout = itemView.findViewById(R.id.left_product_layout);
            mTvLeftName = itemView.findViewById(R.id.tv_left_name);
            mTvLeftLabel = itemView.findViewById(R.id.tv_left_label);
            mTvLeftPrice = itemView.findViewById(R.id.tv_left_price);
            mIvLeftImage = itemView.findViewById(R.id.iv_left_image);
            mRightProductLayout = itemView.findViewById(R.id.right_product_layout);
            mTvRightName = itemView.findViewById(R.id.tv_right_name);
            mTvRightLabel = itemView.findViewById(R.id.tv_right_label);
            mTvRightPrice = itemView.findViewById(R.id.tv_right_price);
            mIvRightImage = itemView.findViewById(R.id.iv_right_image);
        }
    }
}
