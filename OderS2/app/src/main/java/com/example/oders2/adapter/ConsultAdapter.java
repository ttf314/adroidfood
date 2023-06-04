package com.example.oders2.adapter;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oders2.R;
import com.example.oders2.po.Question;

import java.util.List;


public class ConsultAdapter extends BaseAdapter {
    private Context context;
    private List<Question> questionList;// 问题列表

    public ConsultAdapter
            (Context context, List<Question> questionList ) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.questionList=questionList;
    }

    @Override
    public int getCount() {//获取列表数量
        // TODO Auto-generated method stub
        return questionList.size();
    }

    @Override
    public Object getItem(int position) {//获取列表子项
        // TODO Auto-generated method stub
        return questionList;
    }

    @Override
    public long getItemId(int position) {//获取列表项Id
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder=new ViewHolder();//实例化ViewHolder对象
        if (convertView == null) {
//将布局转换成View对象
            convertView = LayoutInflater.from(this.context).inflate(R.layout.list_item_question, null, false);

            //关联Id
            holder.contentView=(TextView)convertView.findViewById(R.id.questionContent);
            holder.timeView=(TextView)convertView.findViewById(R.id.questionTime);
            holder.answerView=(TextView) convertView.findViewById(R.id.answer);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }

        //设置对应显示对象
        holder.contentView.setText(position + 1 + "."
                + questionList.get(position).getQuestionContent());//显示问题内容
        holder.timeView.setText("来自"+questionList.get(position).getWhoneedanswer()+"号用户的提问，时间是：" + questionList.get(position).getQuestionTime());//显示问题时间

        if ("".equals(questionList.get(position).getAnswerTime()) || null == questionList.get(position).getAnswerTime()) {
            holder.answerView.setText("暂无回复");
        } else {
            String[] answers = questionList.get(position).getAnswerContent().split("#");// 将多个回复分割开来
            String[] answerTimes = questionList.get(position).getAnswerTime().split("#");// 将多个回复的时间分割开来
            String answerResult = "";
            for (int i = 0; i < answers.length; i++) {//显示回复内容和时间
                answerResult += "<p><big>" + answers[i]
                        + "</big></p><i><small>回复时间：" + answerTimes[i]
                        + "</small></i>";
            }
            holder.answerView.setText(Html.fromHtml(answerResult));
        }

        return convertView;
    }

    /**存放控件*/
    public final class ViewHolder{
        public TextView contentView;
        public TextView timeView;
        public TextView answerView;

    }
}
