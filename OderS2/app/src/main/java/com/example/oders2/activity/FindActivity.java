package com.example.oders2.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.example.oders2.Gongju.AccessToServer;
import com.example.oders2.Gongju.Constants;
import com.example.oders2.Gongju.Global;
import com.example.oders2.Gongju.ImageUtil;
import com.example.oders2.Gongju.Util;
import com.example.oders2.R;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oders2.adapter.ConsultAdapter;
import com.example.oders2.po.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.List;

import static com.example.oders2.activity.MainActivity.m;


public class FindActivity extends AppCompatActivity {
    private RadioGroup rgMain;
    private List<Question> questionList;
    private ListView questionListView;// 问题列表
    private Handler mHandler;// Handler消息处理对象
    private List<Question> questions;// 问题列表
    private Gson gson = new Gson();
    private Button submit, search;// 提交问题按钮、查询按钮
    private String questionString;// 问题字符串
    private EditText contentView;
    private EditText keywordView;
    private String uid,name=null;
    private TextView txfind;
    private  int REQUEST_IMAGE,REQUEST_IMAGE2,number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXingLibrary.initDisplayOpinion(this);
        //二维码跳
        Intent intent =getIntent();
        name=intent.getStringExtra("name");

        uid=String.valueOf(m.get("id"));
        setContentView(R.layout.activity_find);
        initView();
        show();
       /* mHandler.post(new Runnable() {
            @Override
            public void run() {

                showLvData2(message4);


            }
        });*/
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String result = (String) msg.obj;
                result = (String) msg.obj;
                switch (msg.what) {
                    case Constants.GET_QUESTIONS:
                        if ("exception".equals(result) || "error".equals(result)
                                || "".equals(result)) {
                            Toast.makeText(FindActivity.this, "访问服务器异常!",
                                    Toast.LENGTH_SHORT);
                            return;
                        }
                        questions = gson.fromJson(result,
                                new TypeToken<List<Question>>() {
                                }.getType());

                        if (questions != null) {
                            showQuestions();
                        }
                        break;
                    case Constants.SUBMIT_QUESTION:
                        if ("exception".equals(result) || "error".equals(result)
                                || "".equals(result)) {
                            Toast.makeText(FindActivity.this, "访问服务器异常!",
                                    Toast.LENGTH_SHORT);
                            return;
                        }
                        if ("true".equals(result)) {// 提交成功
                            Toast.makeText(FindActivity.this, "问题提交成功，请等待回复！",
                                    Toast.LENGTH_LONG).show();
                            contentView.setText("");
                            onResume();
                        } else {// 提交失败
                            Toast.makeText(FindActivity.this, "问题提交失败，请重新提交！",
                                    Toast.LENGTH_LONG).show();
                        }
                        break;


                    case Constants.UPDATE_QUESTION:
                        if ("exception".equals(result) || "error".equals(result)
                                || "".equals(result)) {
                            Toast.makeText(FindActivity.this, "访问服务器异常!",
                                    Toast.LENGTH_SHORT);
                            return;
                        }
                        if ("true".equals(result)) {// 提交成功
                            Toast.makeText(FindActivity.this, "回复成功,请等待审核",
                                    Toast.LENGTH_LONG).show();
                            onResume();
                        } else {// 提交失败
                            Toast.makeText(FindActivity.this, "回复失败，请重试！",
                                    Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        break;
                }
            }
        };// 初始化Handler对象
        txfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"扫一扫", "本地打开"};
                AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);
                builder.setTitle("选择扫码还是本地打开照片吧！！！");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0:
                                Intent intent2 = new Intent(FindActivity.this, CaptureActivity.class);
                                startActivityForResult(intent2, REQUEST_IMAGE2);
                                break;
                            case 1:
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("image/*");
                                startActivityForResult(intent, REQUEST_IMAGE);
                                break;
                            case 2:
                                // 选择了蓝色
                                break;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }


    private void initView() {
        rgMain = findViewById(R.id.rg_main);

        questionListView = findViewById(R.id.questionList);// 根据id获取列表控件
        contentView = findViewById(R.id.content);//获取编辑框按钮id
        submit = findViewById(R.id.submit);//获取提交按钮Id
        search = findViewById(R.id.search);// 根据Id找到控件
        keywordView = findViewById(R.id.keyword);//获取查询编辑框Id
        txfind= findViewById(R.id.txfind);
        mHandler = new Handler(getMainLooper());
        if(name!=null){
            txfind.setText(name);
        }

        search.setOnClickListener(new View.OnClickListener() {// 查询按钮的单击事件处理
            @Override
            public void onClick(View v) {
                getHistoryQuestion(keywordView.getText().toString().trim());
            }
        });

        questionListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // 长按某一项进行回复
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view, int position, long id) {
                updateQuestion(questions.get(position));
                return false;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {// 提交按钮的事件处理
            public void onClick(View v) {
                String questionContent = contentView.getText().toString()
                        .trim();// 获取输入的问题
                if ("".equals(questionContent)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            FindActivity.this);
                    builder.setMessage("请输入咨询的问题，内容不能为空！");// 对话框显示的内容
                    builder.create().show();// 创建并显示对话框
                } else {
                    Question question = new Question();
                    question.setQuestionContent(contentView.getText()
                            .toString());
                    question.setQuestionTime(Util.getDate());
                    question.setWhoneedanswer(uid);
                    try {
                        String text = txfind.getText().toString();
                        number = Integer.parseInt(text);
                        // 对整数进行其他操作
                    } catch (NumberFormatException e) {
                        // 处理异常情况，例如提示用户输入合法的整数
                    }
                    question.setDianid(number);
                    questionString = gson.toJson(question);

                    submitQuestion();
                }
            }
        });// 提问按钮的单击事件处理
    }
    private void show() {

        //下方按钮点击事件
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home://主页
                        Intent intent = new Intent(FindActivity.this, UserMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent, 1);
                        break;
                    case R.id.rb_type://分类
                        Intent intent2 = new Intent(FindActivity.this, TypeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent2, 1);
                        break;
                    case R.id.rb_community://发现
                        Intent intent3 = new Intent(FindActivity.this, FindActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent3, 1);
                        break;
                    case R.id.rb_cart://购物车
                        Intent intent4 = new Intent(FindActivity.this, CarActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent4, 1);
                        break;
                    case R.id.rb_user://用户中心
                        Intent intent5 = new Intent(FindActivity.this, UserActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);;
                        startActivityForResult(intent5, 1);
                        break;
                    default:
                        break;
                }
            }
        });
    }



    public void showQuestions() {// 显示问题列表
        ConsultAdapter consultAdapter=new ConsultAdapter(FindActivity.this,questions);
        questionListView.setAdapter(consultAdapter);
    }

    public void getHistoryQuestion(final String keyword) {// 获取以往的问题
        if (Util.isNetworkConnected(FindActivity.this)) {
            new Thread() {
                public void run() {// 线程执行体
                    AccessToServer accessToServer = new AccessToServer();
                    String result = accessToServer.doPost(Global.URL
                                    + "QuestionServlet", new String[] { "flag",
                                    "keyword","name" },
                            new String[] { Question.QUERY, keyword, name });
                    System.out.println("result="+result);
                    Message message = Message.obtain();
                    message.obj = result;
                    message.what = Constants.GET_QUESTIONS;
                    mHandler.sendMessage(message);
                }
            }.start();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getHistoryQuestion(keywordView.getText().toString().trim());
    }
    public void submitQuestion() {
        if (Util.isNetworkConnected(FindActivity.this)) {
            new Thread() {
                public void run() {// 线程执行体
                    AccessToServer accessToServer = new AccessToServer();
                    String result = accessToServer.doPost(Global.URL
                            + "QuestionServlet", new String[] { "question",
                            "flag" }, new String[] { questionString,
                            Question.INSERT });
                    Message message = Message.obtain();
                    message.obj = result;
                    message.what = Constants.SUBMIT_QUESTION;
                    mHandler.sendMessage(message);
                }
            }.start();
        }
    }


    public void updateQuestion(final Question question) {// 更新问题状态
        AlertDialog.Builder builder = new AlertDialog.Builder(FindActivity.this);//实例化Builder对象
        builder.setTitle("我来回答");
        final EditText answerText = new EditText(FindActivity.this);//实例化EditText对象
        builder.setView(answerText);//把编辑框以View的形式加入对话框
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {//设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String answer = answerText.getText().toString();
                if (!"".equals(answer)) {// 如果回答不为空，则更新数据库
                    String oldAnswer = question.getAnswerContent();
                    if (oldAnswer != null && !"null".equals(oldAnswer)) {
                        oldAnswer += "#" + answer;
                    } else {
                        oldAnswer = answer;
                    }
                    question.setAnswerContent(oldAnswer);
                    String oldAnswerTime = question.getAnswerTime();
                    if (oldAnswerTime != null && !"null".equals(oldAnswerTime)) {
                        oldAnswerTime += "#" + Util.getDate();
                    } else {
                        oldAnswerTime = Util.getDate();
                    }
                    question.setAnswerTime(oldAnswerTime);
                    update(question);
                }
            }
        });
        builder.create().show();// 创建对话框
    }

    public void update(Question question) {
        Gson gson = new Gson();
        questionString = gson.toJson(question);
        if (Util.isNetworkConnected(FindActivity.this)) {
            new Thread() {
                public void run() {// 线程执行体
                    AccessToServer accessToServer = new AccessToServer();
                    String result = accessToServer.doPost(Global.URL
                            + "QuestionServlet", new String[] { "question",
                            "flag" }, new String[] { questionString,
                            Question.UPDATE });
                    Message message = Message.obtain();
                    message.obj = result;
                    message.what = Constants.UPDATE_QUESTION;
                    mHandler.sendMessage(message);
                }
            }.start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = getContentResolver();
                try {
                    Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);//显得到bitmap图片

                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this,uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

                            Toast.makeText(FindActivity.this, result+"号店铺的信息", Toast.LENGTH_LONG).show();
                            //bundle.putSerializable("meals", (Serializable) item);
                            Intent intent = new Intent(FindActivity.this, FindActivity.class);
                            intent.putExtra("name", result);
                            startActivityForResult(intent, 1);
                        }
                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(FindActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });

                    if (mBitmap != null) {
                        mBitmap.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }}else if(requestCode == REQUEST_IMAGE2){
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(FindActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
