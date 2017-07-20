package com.aditya.ormdemo;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static DatabaseManager instance;
    private DatabaseHelper helper;

    public static void init(Context ctx) {
        if (null == instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    public DatabaseHelper getHelper() {
        return helper;
    }


    public ArrayList<TableEntry> getAllQuestions() {
        ArrayList<TableEntry> questionList = null;
        try {
            questionList = (ArrayList<TableEntry>) getHelper().getQuestionsDAO().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionList;
    }

    public void addQuestionEntry(TableEntry entry) {
        try {
            getHelper().getQuestionsDAO().create(entry);
            Log.d("Entry Added", entry.getQuestion());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshQuestion(TableEntry tableEntry) {
        try {
            getHelper().getQuestionsDAO().refresh(tableEntry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuestion(TableEntry wishList) {
        try {
            getHelper().getQuestionsDAO().update(wishList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuestion(int catId) {
        try {
            DeleteBuilder<TableEntry, Integer> deleteBuilder = getHelper().getQuestionsDAO().deleteBuilder();
            deleteBuilder.where().eq("id", catId);
            deleteBuilder.delete();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getAns(String Ques)
    {
        // get our query builder from the DAO
        QueryBuilder<TableEntry, Integer> queryBuilder = getHelper().getQuestionsDAO().queryBuilder();
// the 'password' field must be equal to "qwerty"
        try {
            queryBuilder.where().eq(TableEntry.QUESTION_FIELD_NAME, Ques);
            PreparedQuery<TableEntry> preparedQuery = queryBuilder.prepare();
            List<TableEntry> quesList = getHelper().getQuestionsDAO().query(preparedQuery);
            return quesList.get(0).getAnswer();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
// prepare our sql statement

// query for all accounts that have "qwerty" as a password
        return "Not Found";

    }


}