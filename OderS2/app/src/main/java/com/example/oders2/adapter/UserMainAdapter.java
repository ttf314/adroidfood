package com.example.oders2.adapter;

import android.content.Context;
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
import com.example.oders2.activity.UserMainActivity;
import com.example.oders2.po.Cars;
import com.example.oders2.po.Meals;

import java.util.ArrayList;
import java.util.List;

public class UserMainAdapter extends RecyclerView.Adapter<UserMainAdapter.ProductListItemViewHolde> {
    private Context mContext;
    private List<Meals> meals2;
    private LayoutInflater mLayoutInflater;
    String newurl;

    public UserMainAdapter(Context context, List<Meals> datas) {
        mContext = context;
        meals2 = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public UserMainAdapter.ProductListItemViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_product_list2,parent,false);
        return new UserMainAdapter.ProductListItemViewHolde(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull UserMainAdapter.ProductListItemViewHolde holder, int position) {

        Meals productItem = meals2.get(position);
        holder.mTvName.setText(productItem.getName());
        holder.mTvLabel.setText(productItem.getDesccription());
        holder.mTvPrice.setText(productItem.getPrice());
        System.out.println(productItem.getUrl1());
        newurl=productItem.getUrl1().replace("\\","/");
        System.out.println(newurl);
        Glide.with(mContext).load(Constants.url+newurl).into(holder.mIvImage);
    }

    @Override
    public int getItemCount() {
        return meals2.size();
    }

    public interface OnProductListener{
        void onProductAdd(Cars productItem);

        void onProductSub(Cars productItem);
    }
    private CarsAdapter.OnProductListener mOnProductListener;

    public void setOnProductListener(CarsAdapter.OnProductListener mOnProductListener) {
        this.mOnProductListener = mOnProductListener;
    }


    class ProductListItemViewHolde extends  RecyclerView.ViewHolder{

        public ImageView mIvImage;
        public TextView mTvName;
        public TextView mTvLabel;
        public TextView mTvPrice;

        public ImageView mIvAdd;
        public ImageView mIvSub;
        public TextView mTvCount;


        public ProductListItemViewHolde(@NonNull View itemView) {
            super(itemView);

            mIvImage = (ImageView) itemView.findViewById(R.id.id_iv_image);
            mTvName = (TextView) itemView.findViewById(R.id.id_tv_name);
            mTvLabel = (TextView) itemView.findViewById(R.id.id_tv_label);
            mTvPrice = (TextView) itemView.findViewById(R.id.id_tv_price);
           /* mTvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = meals2.get(getAdapterPosition()).getName();
                    // 使用name进行后续操作
                }
            });*/



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* UserMainActivity.launch(mContext, meals2.get(getAdapterPosition()));*/
                }
            });

        }
    }
}
