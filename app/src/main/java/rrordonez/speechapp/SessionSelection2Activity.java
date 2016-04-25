package rrordonez.speechapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by ReyDonez on 10/22/2015.
 */
public class SessionSelection2Activity extends ActionBarActivity implements android.view.View.OnClickListener {

    EditText editTextLetter;
    Button btnInitial;
    Button btnMiddle;
    Button btnEnd;
    Button btnBegin;
    Button btnBack;
    Button btnFinishSession;

    String position = "";
    String letter = "";

    private int _Client_Id;

    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_selection2);

        //edit text fields
        editTextLetter = (EditText) findViewById(R.id.editTextLetter);

        //buttons
        btnInitial = (Button) findViewById(R.id.btnInitial);
        btnMiddle = (Button) findViewById(R.id.btnMiddle);
        btnEnd = (Button) findViewById(R.id.btnEnd);
        btnBegin = (Button) findViewById(R.id.btnBegin);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnFinishSession = (Button) findViewById(R.id.btnFinishSession);


        //on click functions
        btnBegin.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnInitial.setOnClickListener(this);
        btnMiddle.setOnClickListener(this);
        btnEnd.setOnClickListener(this);
        btnFinishSession.setOnClickListener(this);

        _Client_Id = 0;
        Intent intent = getIntent();
        _Client_Id = intent.getIntExtra("_Id", 0);


    }

    //on click
    public void onClick(View view) {
        if (view == btnBegin) {
            //if begin button is clicked then go to Exercise
            Intent exerciseActivity = new Intent(getApplicationContext(), ExerciseActivity.class);
            letter = editTextLetter.getText().toString().toLowerCase();
            if (letter != null || position != null) {
                exerciseActivity.putExtra("letter", letter);
                exerciseActivity.putExtra("position", position);
                SessionRepo sessionRepo = new SessionRepo(this);
                Toast.makeText(this, String.valueOf(sessionRepo.getKey(this)), Toast.LENGTH_SHORT).show();
                exerciseActivity.putExtra("session", sessionRepo.getKey(this));
                startActivity(exerciseActivity);
            }
            else {
                Toast.makeText(this, "Must enter letter or position", Toast.LENGTH_SHORT).show();
            }



        }else if (view == btnFinishSession) {
            SessionRepo sessionRepo = new SessionRepo(this);
            Session session = new Session();
            //set session variables
            session.average = 100;
            //set date
            DateFormat dateFormat = DateFormat.getDateInstance();
            Date date = new Date();
            session.date = dateFormat.format(date);
            session.client_ID = _Client_Id;
            session.start = 0;
            Date endDate = new Date();
            session.end = endDate.getTime();

            sessionRepo.insert(session);

            finish();

        }else if (view == findViewById(R.id.btnBack)) {
            finish();

          //if initial button is pressed then set it to red
        } else if (view == findViewById(R.id.btnInitial)) {
            btnInitial.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
            btnMiddle.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
            btnEnd.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
            position = "initial";

            //if middle button is pressed then set it to red
        } else if (view == btnMiddle){
            btnInitial.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
            btnMiddle.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
            btnEnd.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
            position = "middle";

            //if end button is pressed then set it to red
        } else if (view == btnEnd){
            btnInitial.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
            btnMiddle.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
            btnEnd.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
            position = "end";
        }
    }
}
