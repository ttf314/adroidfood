package com.example.oders2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.R;
import com.example.oders2.po.Cars;
import com.example.oders2.po.Meals;
import com.example.oders2.test.ProductListAdapter;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ProductListItemViewHolde> {
    private Context mContext;
    private List<Cars> cars2;
    private LayoutInflater mLayoutInflater;
    String newurl;

    public CarsAdapter(Context context, List<Cars> datas) {
        mContext = context;
        cars2 = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public CarsAdapter.ProductListItemViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_product_list,parent,false);
        return new CarsAdapter.ProductListItemViewHolde(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarsAdapter.ProductListItemViewHolde holder, int position) {

        Cars productItem = cars2.get(position);
        holder.mTvName.setText(productItem.getMname());

        holder.mTvCount.setText(productItem.getZk()+"");

        holder.mTvLabel.setText(productItem.getDescription());
        holder.mTvPrice.setText(productItem.getPrice());
        System.out.println(productItem.getUrl());
        newurl=productItem.getUrl().replace("\\","/");
        System.out.println(newurl);
        Glide.with(mContext).load(Constants.url+newurl).into(holder.mIvImage);
    }

    @Override
    public int getItemCount() {
        return cars2.size();
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

            mIvAdd = (ImageView) itemView.findViewById(R.id.id_iv_add);
            mIvSub = (ImageView) itemView.findViewById(R.id.id_iv_sub);
            mTvCount = (TextView) itemView.findViewById(R.id.id_tv_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ProductListActivity.launch(mContext, meals2.get(getAdapterPosition()));

                }
            });

            mIvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    // 修改数据集
                    Cars productItem = cars2.get(position);
                    productItem.setZk(productItem.getZk()+1);
                    System.out.println(productItem.getMname()+"当前值"+productItem.getZk());

                    // 修改UIa
                    mTvCount.setText("" + productItem.getZk());
                    // 回调
                    if (mOnProductListener != null) {

                        mOnProductListener.onProductAdd(productItem);
                    }
                }
            });

            mIvSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    // 修改数据集
                    Cars productItem = cars2.get(position);

                    if (productItem.getZk() == 0) {
                        System.out.println("已经是0了，你想干嘛~~~");
                        return;
                    }
                    productItem.setZk(productItem.getZk() - 1);
                    System.out.println(productItem.getMname()+"当前值"+productItem.getZk());
                    // 修改UI
                    mTvCount.setText("" + productItem.getZk());
                    // 回调
                    if (mOnProductListener != null) {
                        mOnProductListener.onProductSub(productItem);
                    }
                }
            });

        }
    }
}
