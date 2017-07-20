package com.aditya.ormdemo;

/**
 * Created by Adi on 20/07/17.
 */

public class DataModelQuestion {
    private String question, answer;

    DataModelQuestion(String question, String answer)
    {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
