package com.example.oders2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.oders2.Gongju.AddSubView;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.Gongju.OnAddBtnClickListener;
import com.example.oders2.Gongju.OnDelBtnClickListener;
import com.example.oders2.R;
import com.example.oders2.po.Meals;
import com.example.oders2.po.Meals2;

import java.util.List;

public class MealsAdapter2 extends BaseAdapter {
    String newurl;
    String price;
    private int num=1;
    private AddSubView addSubView2;
    private Context context;
    private List<Meals> meals;
    private TextView tp_tv_price;
    private OnAddBtnClickListener onAddBtnClickListener;     // 添加按钮 点击事件的监听实例
    private OnDelBtnClickListener onDelBtnClickListener;     // 添加按钮 点击事件的监听实例
    /*public MealsAdapter(Context context, List<Meals> meals) {
        this.context = context;
        this.meals = meals;
    }*/
    public MealsAdapter2(Context context, List<Meals> meals, TextView tp_tv_price) {
        this.context = context;
        this.meals = meals;
        this.tp_tv_price = tp_tv_price;
        showTotalPri();
    }

    private void showTotalPri() {
        tp_tv_price.setText(getTotalPri()+"");

    }

    private double getTotalPri() {
        double totalPrice = 0.0;
        return totalPrice;
    }

    public void setMeals(List<Meals> meals) {
        this.meals = meals;
    }

    public void setOnAddBtnClickListener(OnAddBtnClickListener onAddBtnClickListener) {
        this.onAddBtnClickListener = onAddBtnClickListener;
    }
    public void setOnDelBtnClickListener(OnDelBtnClickListener onDelBtnClickListener) {
        this.onDelBtnClickListener = onDelBtnClickListener;
    }
    @Override
    public int getCount() {
        return meals.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.foods_list_item2, null);
            //实例化
            viewHolder = new ViewHolder();
            viewHolder.tv_id = convertView.findViewById(R.id.tv_id);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price = convertView.findViewById(R.id.tv_price);

            //viewHolder.tv_num = convertView.findViewById(R.id.tv_num);

            //viewHolder.tv_num = convertView.findViewById(R.id.tv_num);
            viewHolder.tu = convertView.findViewById(R.id.tu);
            //viewHolder.addSubView=convertView.findViewById(R.id.addSubView);
            //viewHolder.addSubView = (AddSubView) convertView.findViewById(R.id.addSubView);
           /* viewHolder.btn_add=convertView.findViewById(R.id.btn_add);
            viewHolder.btn_ed=convertView.findViewById(R.id.btn_ed);*/
            convertView.setTag(viewHolder);//存viewHolder
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 这里进行数据填充
        Meals item = meals.get(position);
        viewHolder.tv_id.setText(item.getId()+".");
        viewHolder.tv_name.setText(item.getName());
        viewHolder.tv_num.setText(item.getType());

        //viewHolder.tv_num.setText("0");
        //转化类型
        viewHolder.tv_price.setText(String.valueOf(item.getPrice()));
        //viewHolder.tv_price2.setText(viewHolder.addSubView.());
        //viewHolder.tv_price2.setText((int) (Double.valueOf(viewHolder.addSubView.getValue()) * Double.valueOf(item.getPrice())));
        //viewHolder.tu.setImageResource(R.drawable.add);
        //viewHolder.addSubView.setValue(2);
        //viewHolder.addSubView.getValue();
        //System.out.println(addSubView2.getValue());

        System.out.println(item.getUrl1());
        newurl=item.getUrl1().replace("\\","/");
        System.out.println(newurl);
        Glide.with(context).load(Constants.url+newurl).into(viewHolder.tu);
        //viewHolder.tv_price2.setText(num * 2+"");
        System.out.println(viewHolder.tv_price2.getText());
        //设置商品数量的变化
        /*viewHolder.addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                num++;
                addSubView2.getValue();
                System.out.println("num"+num);
            }
        });*/

        //Glide.with(context).load("https://test.ttf314.repl.co/st.png").into(viewHolder.tu);
        // 修改按钮的点击事件
        viewHolder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddBtnClickListener.onAddBtnClick(v, position);

        }
        });

        viewHolder.btn_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelBtnClickListener.onDelBtnClick(v, position);
            }
        });
        return convertView;
    }


    // 自定义内部类
    private class ViewHolder{
        private TextView tv_id, tv_name, tv_price,tv_price2,tv_num,tv_typ;
        private ImageView btn_add,btn_ed,tu;
        private AddSubView addSubView;
    }
}
