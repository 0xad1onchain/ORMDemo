package com.aditya.ormdemo;

import com.j256.ormlite.field.DatabaseField;

public class TableEntry {
    public static final String QUESTION_FIELD_NAME = "question";
    @DatabaseField(generatedId=true)
    private int id;

    @DatabaseField(unique = true, columnName = QUESTION_FIELD_NAME)
    private String question;

    @DatabaseField(columnName = "answer")
    private String answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return this.question;
    }


}
