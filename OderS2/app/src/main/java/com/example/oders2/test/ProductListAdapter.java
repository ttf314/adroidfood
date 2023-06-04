package com.example.oders2.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oders2.R;
import com.example.oders2.po.Meals;
import com.example.oders2.po.Meals2;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListItemViewHolde> {
    private Context mContext;
    private List<Meals> meals2;
    private LayoutInflater mLayoutInflater;
    String newurl;
    String url="http://192.168.10.108:8080";//图片获取的地址

    public ProductListAdapter(Context context, List<Meals> datas) {
        mContext = context;
        meals2 = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ProductListItemViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_product_list,parent,false);
        return new ProductListItemViewHolde(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListItemViewHolde holder, int position) {

        Meals productItem = meals2.get(position);
        holder.mTvName.setText(productItem.getName());

        holder.mTvCount.setText(productItem.getType()+"");

        holder.mTvLabel.setText(productItem.getDesccription());
        holder.mTvPrice.setText(productItem.getPrice());
        System.out.println(productItem.getUrl1());
        newurl=productItem.getUrl1().replace("\\","/");
        System.out.println(newurl);
        Glide.with(mContext).load(url+newurl).into(holder.mIvImage);
    }

    @Override
    public int getItemCount() {
        return meals2.size();
    }

    public interface OnProductListener{
        void onProductAdd(Meals productItem);

        void onProductSub(Meals productItem);
    }
    private OnProductListener mOnProductListener;

    public void setOnProductListener(OnProductListener mOnProductListener) {
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
                    Meals productItem = meals2.get(position);
                    productItem.setType(productItem.getType()+1);
                    System.out.println(productItem.getName()+"当前值"+productItem.getType());

                    // 修改UIa
                    mTvCount.setText("" + productItem.getType());
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
                    Meals productItem = meals2.get(position);

                    if (productItem.getType() == 0) {
                        System.out.println("已经是0了，你想干嘛~~~");
                        return;
                    }

                    productItem.setType(productItem.getType() - 1);
                    System.out.println(productItem.getName()+"当前值"+productItem.getType());
                    // 修改UI
                    mTvCount.setText("" + productItem.getType());
                    // 回调
                    if (mOnProductListener != null) {

                       mOnProductListener.onProductSub(productItem);
                    }
                }
            });

        }
    }
}
