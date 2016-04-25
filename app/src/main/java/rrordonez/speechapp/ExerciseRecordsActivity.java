package rrordonez.speechapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ReyDonez on 12/8/2015.
 */
public class ExerciseRecordsActivity extends ListActivity implements android.view.View.OnClickListener  {

    Button btnBack;
    private int _Session_Id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_records);

        //buttons
        btnBack = (Button) findViewById(R.id.btnBack);

        //on click functions
        btnBack.setOnClickListener(this);

        //get selected session id
        _Session_Id = 0;
        Intent intent = getIntent();
        _Session_Id = intent.getIntExtra("_Id", 0);

        //gets sessions for selected client
        ExerciseRepo exerciseRepo = new ExerciseRepo(this);
        //lists the sessions of selected client
        ArrayList<HashMap<String, String>> exerciseList = exerciseRepo.getExerciseList(_Session_Id);
        if (exerciseList.size() != 0) {
            ListView listView = getListView();
            ListAdapter adapter = new SimpleAdapter(ExerciseRecordsActivity.this, exerciseList, R.layout.view_exercise_entry,
                    new String[]{"_Id", "letter", "position", "score"}, new int[]{R.id._ID, R.id.letter, R.id.position, R.id.score});
            setListAdapter(adapter);
        }
    }

    //finish ExerciseRecordsActivity when back button is clicked
    public void onClick(View view) {
        if (view == findViewById(R.id.btnBack)) {
            finish();
        }
    }
}
