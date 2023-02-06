package com.example.test;

import java.util.List;

public class Test {
    private String name,timer;
    private int size;
    private List<QuestionList>questionsList;
    private int id;

    public Test(String name, String timer, int size, List<QuestionList> questionsList,int id) {
        this.name = name;
        this.timer = timer;
        this.size = size;
        this.questionsList = questionsList;
        this.id=id;
    }
    public Test() {
    }

    public Test(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<QuestionList> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<QuestionList> questionsList) {
        this.questionsList = questionsList;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Название теста: "+name;
    }
}
