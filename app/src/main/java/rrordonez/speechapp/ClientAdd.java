package rrordonez.speechapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ReyDonez on 10/22/2015.
 */
public class ClientAdd extends ListActivity implements android.view.View.OnClickListener {
    //invisible text view
    TextView session_Id;

    //edit text views
    EditText editTextID;
    EditText editTextAge;

    //buttons
    Button btnSave;
    Button btnDelete;
    Button btnClose;
    private int _Client_Id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_add);

        //edit text fields
        editTextID = (EditText) findViewById(R.id.editTextID);
        editTextAge = (EditText) findViewById(R.id.editTextAge);

        //buttons
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        //on click functions
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        //gets this client from database
        _Client_Id = 0;
        Intent intent = getIntent();
        _Client_Id = intent.getIntExtra("_Id", 0);
        clientRepo repo = new clientRepo(this);
        Client client = new Client();
        client = repo.getClientById(_Client_Id);

        //sets the textview to this client
        editTextAge.setText(String.valueOf(client.age));
        editTextID.setText(String.valueOf(client.client_ID));

        //gets sessions for selected client
        SessionRepo sessionRepo = new SessionRepo(this);
        //lists the sessions of selected client
        ArrayList<HashMap<String, String>> sessionList = sessionRepo.getSessionList(_Client_Id);
        if (sessionList.size() != 0) {
            ListView lv = getListView();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    session_Id = (TextView) view.findViewById(R.id._ID);
                    String sessionId = session_Id.getText().toString();
                    Intent intent1 = new Intent(getApplicationContext(), ExerciseRecordsActivity.class);
                    intent1.putExtra("_Id", Integer.parseInt(sessionId));
                    startActivity(intent1);
                }
            });
            ListAdapter adapter = new SimpleAdapter(ClientAdd.this, sessionList, R.layout.view_session_entry,
                      new String[]{"session_ID", "date", "average"}, new int[]{R.id._ID, R.id.date, R.id.average});
            setListAdapter(adapter);
           }
        }

    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)) {
            //get text fields and save to new client
            clientRepo repo = new clientRepo(this);
            Client client = new Client();
            client.age = Integer.parseInt(editTextAge.getText().toString());
            client.client_ID = Integer.parseInt(editTextID.getText().toString());
            client._ID = _Client_Id;

            //if new client is not in data insert client, else update client
            if (_Client_Id == 0) {
                _Client_Id = repo.insert(client);

                Toast.makeText(this, "New Student Insert", Toast.LENGTH_SHORT).show();
            } else {

                repo.update(client);
                Toast.makeText(this, "Student Record updated", Toast.LENGTH_SHORT).show();
            }
        } else if (view == findViewById(R.id.btnDelete)) {
            clientRepo repo = new clientRepo(this);
            repo.delete(_Client_Id, this);
            Toast.makeText(this, "Student Record Deleted", Toast.LENGTH_SHORT).show();
            finish();
        } else if (view == findViewById(R.id.btnClose)) {
            finish();
        }
    }
}
