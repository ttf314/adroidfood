package com.example.oders2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oders2.Gongju.OnDelBtnClickListener;
import com.example.oders2.R;
import com.example.oders2.po.Order;
import com.example.oders2.po.Order2;

import java.util.List;

public class LvDingDanInfoAdapter2 extends BaseAdapter {

    private Context context;
    private List<Order2> orders;
    private TextView tp_tv_price;
    private OnDelBtnClickListener onDelBtnClickListener;     // 添加按钮 点击事件的监听实例

    public LvDingDanInfoAdapter2(Context context, List<Order2> orders) {
        this.context = context;
        this.orders = orders;
    }

    public void setDingDanInfos(List<Order2> dingDanInfos) {
        this.orders = dingDanInfos;
    }

    public void setOnDelBtnClickListener(OnDelBtnClickListener onDelBtnClickListener) {
        this.onDelBtnClickListener = onDelBtnClickListener;
    }


    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            //实例化
            convertView = LayoutInflater.from(context).inflate(R.layout.dingdan_list_item2, null);
            //实例化
            viewHolder = new ViewHolder();
            viewHolder.tv_id = convertView.findViewById(R.id.tv_dingdanid);
            viewHolder.tv_num = convertView.findViewById(R.id.tv_num);
            viewHolder.tv_money = convertView.findViewById(R.id.tv_money);
            viewHolder.tv_table = convertView.findViewById(R.id.tv_table);
            viewHolder.tv_time = convertView.findViewById(R.id.tv_time);
            viewHolder.tv_detel = convertView.findViewById(R.id.tv_detel);
            viewHolder.btn_edit = convertView.findViewById(R.id.btn_edit);
            viewHolder.tv_sdren = convertView.findViewById(R.id.tv_sdren);

            viewHolder.tv_time2 = convertView.findViewById(R.id.tv_time2);
            convertView.setTag(viewHolder);//存viewHolder
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 这里进行数据填充
        Order2 item = orders.get(position);
        viewHolder.tv_id.setText(item.getId()+"");
        viewHolder.tv_money.setText(String.valueOf(item.getPrice()));
        viewHolder.tv_num.setText(item.getCount()+"");
        viewHolder.tv_time.setText(item.getDate());
        viewHolder.tv_table.setText(item.getWeizhi());
        viewHolder.tv_detel.setText(item.getDetel());

        viewHolder.tv_sdren.setText(item.getEmpname());
        viewHolder.tv_time2.setText(item.getDate2());
        // 修改按钮的点击事件
        // 删除按钮
        viewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelBtnClickListener.onDelBtnClick(v,position);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        private TextView tv_sdren,tv_dingdanid2, tv_detel, tv_money, tv_num, tv_id, tv_time,tv_table,tv_time2;
        private ImageView btn_edit, btn_delete3, btn_add;
    }
}