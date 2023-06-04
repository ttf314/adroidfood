package com.example.oders2.po;


import java.io.Serializable;

public class Question implements Serializable {////������ѯ�е�����
    private static final long serialVersionUID = 1L;
    public static final String QUERY="query";
    public static final String INSERT="insert";
    public static final String UPDATE="update";
    private int id;
    private String questionContent;
    private String questionTime;
    private String answerContent;
    private String answerTime;
    private String questionStatus;
    private String whoneedanswer;
    private int dianid;
    private int isDelete;

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getWhoneedanswer() {
        return whoneedanswer;
    }

    public void setWhoneedanswer(String whoneedanswer) {
        this.whoneedanswer = whoneedanswer;
    }

    public int getDianid() {
        return dianid;
    }

    public void setDianid(int dianid) {
        this.dianid = dianid;
    }

    public int getQuestionId() {
        return id;
    }
    public void setQuestionId(int id) {
        this.id = id;
    }
    public String getQuestionContent() {
        return questionContent;
    }
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }
    public String getQuestionTime() {
        return questionTime;
    }
    public void setQuestionTime(String questionTime) {
        this.questionTime = questionTime;
    }
    public String getAnswerContent() {
        return answerContent;
    }
    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }
    public String getAnswerTime() {
        return answerTime;
    }
    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }
    public String getQuestionStatus() {
        return questionStatus;
    }
    public void setQuestionStatus(String questionStatus) {
        this.questionStatus = questionStatus;
    }
    @Override
    public String toString() {
        return "Question [id=" + id + ", questionContent="
                + questionContent + ", questionTime=" + questionTime
                + ", answerContent=" + answerContent + ", answerTime="
                + answerTime + ", questionStatus=" + questionStatus + "]";
    }

}