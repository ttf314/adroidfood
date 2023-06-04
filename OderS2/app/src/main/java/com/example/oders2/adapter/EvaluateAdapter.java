package com.example.oders2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oders2.Gongju.AddSubView;
import com.example.oders2.R;
import com.example.oders2.po.Evaluates;

import java.util.List;

public class EvaluateAdapter extends BaseAdapter {

    String price;
    private int num=1;
    private AddSubView addSubView2;
    private Context context;
    private List<Evaluates> evaluates;

   /*public MealsAdapter(Context context, List<Meals> meals) {
        this.context = context;
        this.meals = meals;
    }*/



    public EvaluateAdapter(Context context, List<Evaluates> evaluates) {
        this.context = context;
        this.evaluates = evaluates;

    }


    private double getTotalPri() {
        double totalPrice = 0.0;


        return totalPrice;
    }

    public void setMeals(List<Evaluates> evaluates) {
        this.evaluates = evaluates;
    }


    @Override
    public int getCount() {
        return evaluates.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.evaluate_list, null);
            //实例化
            viewHolder = new ViewHolder();
            viewHolder.tv_id = convertView.findViewById(R.id.id_tv_uid);
            viewHolder.tv_detel = convertView.findViewById(R.id.id_tv_detel);
            viewHolder.tv_time = convertView.findViewById(R.id.id_tv_time);
            convertView.setTag(viewHolder);//存viewHolder
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_id.setText("来自用户编号--"+evaluates.get(position).getUid()+"的评价");
        viewHolder.tv_detel.setText(evaluates.get(position).getVdedtel());
        viewHolder.tv_time.setText("评价时间："+evaluates.get(position).getTimes());
        return convertView;
    }


    // 自定义内部类
    private class ViewHolder{
        private TextView tv_id, tv_detel, tv_time,tv_price2,tv_num,tv_gmNum;

    }
}
