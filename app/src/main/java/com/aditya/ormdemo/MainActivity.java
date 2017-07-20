package com.aditya.ormdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private TextView quesText, ansText;
    private List<TableEntry> QuestionList;
    private List<DataModelQuestion> qList;
    Button questionSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionSelect = (Button) findViewById(R.id.button);
        qList = new ArrayList<>();

        qList.add(new DataModelQuestion("Who plays against the USA in golf's Walker Cup?", "Great Britain"));
        qList.add(new DataModelQuestion("Who did Elton John originally duet with on Don't Go Breaking My Heart?", "Kiki Dee"));
        qList.add(new DataModelQuestion("What color is the M in McDonald's?", "Yellow"));
        qList.add(new DataModelQuestion("Where does Greg Norman come from?", "Australia"));
        qList.add(new DataModelQuestion("In which Park was the New York marathon run until 1970?", "Central Park"));


        DatabaseManager.init(this);


        quesText = (TextView) findViewById(R.id.ques_view);
        ansText = (TextView) findViewById(R.id.ans_view);

        QuestionList = new ArrayList<>();
        //set data to views

        questionSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
                builderSingle.setTitle("Select Question");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
                for(TableEntry question : QuestionList)
                {
                    arrayAdapter.add(question.getQuestion());
                }

                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        quesText.setVisibility(View.VISIBLE);
                        ansText.setVisibility(View.VISIBLE);
                        quesText.setText(strName);
                        //String Answer = DatabaseManager.getInstance().getAns(strName); //Doesnt always give answer
                        String Answer = QuestionList.get(which).getAnswer();
                        ansText.setText(Answer);
                    }
                });
                builderSingle.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Main", "resume");


            clearDB();
            addDataToDB(qList);
            getDataFromDB();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            //Intent i = new Intent(this, AddingActivity.class);
            //startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getDataFromDB() {
        if (QuestionList != null) QuestionList.clear();

        //get all data to Lists
        ArrayList<TableEntry> tableEntryArrayList = DatabaseManager.getInstance().getAllQuestions();
        for (int i = 0; i < tableEntryArrayList.size(); i++) {
            QuestionList.add(tableEntryArrayList.get(i));
            Log.d("Question", tableEntryArrayList.get(i).getQuestion() + tableEntryArrayList.get(i).getAnswer());
        }

        if (QuestionList.size() == 0) {
            //no data in database

        } else {
            //adapter.notifyDataSetChanged();
        }
    }

    public TableEntry getQuestionEnty(String Question, String Answer)
    {
        TableEntry entry = new TableEntry();
        entry.setQuestion(Question);
        entry.setAnswer(Answer);
        return entry;
    }

    public void addDataToDB(List<DataModelQuestion> queslist)
    {
        for(DataModelQuestion question : queslist)
        {
            TableEntry entry = getQuestionEnty(question.getQuestion(), question.getAnswer());
            DatabaseManager.getInstance().addQuestionEntry(entry);
        }
    }

    public void clearDB()
    {
        ArrayList<TableEntry> tableEntryArrayList = DatabaseManager.getInstance().getAllQuestions();
        if(null == tableEntryArrayList)
        {
            //Do Nothing
        }
        else
        {
            for (int i = 0; i < tableEntryArrayList.size(); i++) {
                int id = tableEntryArrayList.get(i).getId();
                DatabaseManager.getInstance().deleteQuestion(id);
            }
        }

    }

}
