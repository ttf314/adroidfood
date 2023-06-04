package com.example.oders2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.oders2.Gongju.OnAddBtnClickListener;
import com.example.oders2.R;
import com.example.oders2.po.ItemCategory;
import com.example.oders2.po.Meals;

import java.util.List;

public class ItemCategoryAdapter extends BaseAdapter {
    String newurl;
    private Context context;
    private List<ItemCategory> itemCategories;


    public ItemCategoryAdapter(Context context, List<ItemCategory> itemCategories) {
        this.context = context;
        this.itemCategories = itemCategories;
    }

    public void setItemCategories(List<ItemCategory> itemCategories) {
        this.itemCategories = itemCategories;
    }

    @Override
    public int getCount() {
        return itemCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            //实例化
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item1, null);
            //实例化
            viewHolder = new ViewHolder();

            viewHolder.tv_name = convertView.findViewById(R.id.tv_itemname);
            viewHolder.tv_id = convertView.findViewById(R.id.tv_itemid);
            convertView.setTag(viewHolder);//存viewHolder
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 这里进行数据填充显示
       /* ItemCategory item = itemCategories.get(position);
        viewHolder.tv_name.setText(item.getName());
*/
        //viewHolder.tv_id.setText(itemCategories.get(position).getId()+"");
        viewHolder.tv_name.setText(itemCategories.get(position).getName());
        //viewHolder.tv_price.setText(itemCategories.get(position).getPrice());
        //viewHolder.tv_id.setText("1");//不知道为什么可以了，因为这里只是显示，
        // 获取值在其他代码已经实现，所以不需要给界面赋值了，所以这段可以不要

        return convertView;
    }


    // 自定义内部类
    private class ViewHolder{
        private TextView  tv_name,tv_id;
    }
}
