package rrordonez.speechapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ReyDonez on 11/4/2015.
 */
public class SessionSelectionActivity extends ActionBarActivity implements android.view.View.OnClickListener {

    TextView TextID;
    TextView TextAge;
    Button btnNext;
    Button btnClose;
    private int _Client_Id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_selection);

        //edit text fields
        TextID = (TextView) findViewById(R.id.textView);
        TextAge = (TextView) findViewById(R.id.textView2);

        //buttons
        btnNext = (Button) findViewById(R.id.btnNext);
        btnClose = (Button) findViewById(R.id.btnClose);

        //on click functions
        btnNext.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        //gets this client from database
        _Client_Id = 0;
        Intent intent = getIntent();
        _Client_Id = intent.getIntExtra("_Id", 0);
        clientRepo repo = new clientRepo(this);
        Client client = new Client();
        client = repo.getClientById(_Client_Id);

        //sets the textview to this client
        TextAge.setText("Age: " + String.valueOf(client.age));
        TextID.setText("Id: " + String.valueOf(client.client_ID));
    }

    public void onClick(View view) {
        if (view == findViewById(R.id.btnNext)) {
            //if next button then go to selection 2
            Intent i = new Intent(getApplicationContext(), SessionSelection2Activity.class);
            i.putExtra("_Id", _Client_Id);
            startActivity(i);

        } else if (view == findViewById(R.id.btnClose)) {
            finish();
        }
    }
}
