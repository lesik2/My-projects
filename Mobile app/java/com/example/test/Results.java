package com.example.test;

public class Results {
    private String userName,testName,result;
    private int attempt;

    public Results() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        if(userName ==null)
        {
            return "Название теста: "+testName+", результат: "+result+" | "+attempt;
        }
       return  "Имя пользователя: "+userName +", название теста: "+testName+", результат: "+result+" | "+attempt;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }
}
