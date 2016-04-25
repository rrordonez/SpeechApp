package rrordonez.speechapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    TextView mainTextView;
    Button start_button;
    Button view_button;
    Button report_button;
    Button option_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find main_title id
        mainTextView = (TextView) findViewById(R.id.main_title);

        //find start button and listen for it here
        start_button = (Button) findViewById(R.id.start_session);

        //find view button and listen for it here
        view_button = (Button) findViewById(R.id.view_records);

        //find report_button and listen for it here
        report_button = (Button) findViewById(R.id.generate_report);

        //find option_button and listen for it here
        option_button = (Button) findViewById(R.id.options);
    }

    public void session(View v){
        Intent i = new Intent(getApplicationContext(), sessionActivity.class);
        startActivity(i);
    }

    public void reports(View v){
        Intent i = new Intent(getApplicationContext(), RecordsActivity.class);
        startActivity(i);
    }
}
