package rrordonez.speechapp;

/**
 * Created by ReyDonez on 11/4/2015.
 */
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class RecordsActivity extends ListActivity implements android.view.View.OnClickListener {
    //buttons
    Button btnAdd, btnGetAll;

    //invisible text view
    TextView client_Id;

    @Override
    public void onClick(View view) {
        //if you click the add button then start ClientAdd activity
        if (view == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, ClientAdd.class);
            intent.putExtra("_Id", 0);
            startActivity(intent);

        } else {

            clientRepo repo = new clientRepo(this);

            //lists the clients on screen and listens for one to be selected
            ArrayList<HashMap<String, String>> clientList = repo.getClientList();
            if (clientList.size() != 0) {
                ListView lv = getListView();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        client_Id = (TextView) view.findViewById(R.id._ID);
                        String clientId = client_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(), ClientAdd.class);
                        objIndent.putExtra("_Id", Integer.parseInt(clientId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter(RecordsActivity.this, clientList, R.layout.view_client_entry,
                        new String[]{"_Id", "client_id"}, new int[]{R.id._ID, R.id.client_ID});
                setListAdapter(adapter);
            } else {
                Toast.makeText(this, "No student!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        clientRepo repo = new clientRepo(this);

        //lists the clients on screen and listens for one to be selected
        ArrayList<HashMap<String, String>> clientList = repo.getClientList();
        if (clientList.size() != 0) {
            ListView lv = getListView();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    client_Id = (TextView) view.findViewById(R.id._ID);
                    String clientId = client_Id.getText().toString();
                    Intent objIndent = new Intent(getApplicationContext(),ClientAdd.class);
                    objIndent.putExtra("_Id", Integer.parseInt(clientId));
                    startActivity(objIndent);
                }
            });
            ListAdapter adapter = new SimpleAdapter(RecordsActivity.this, clientList, R.layout.view_client_entry,
                    new String[]{"_Id", "client_id"}, new int[]{R.id._ID, R.id.client_ID});
            setListAdapter(adapter);
        } else {
            Toast.makeText(this, "No student!", Toast.LENGTH_SHORT).show();

        }
    }
}