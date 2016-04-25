package rrordonez.speechapp;

/**
 * Created by ReyDonez on 10/22/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;

public class clientRepo {
    private DbHandler dbHandler;

    public clientRepo(Context context) {
        dbHandler = new DbHandler(context);
    }

    //insert into database
    public int insert(Client client) {
        //write data
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();

        //set pair values
        values.put(Client.KEY_CLIENT_ID, client.client_ID);
        values.put(Client.KEY_AGE, client.age);

        //insert row
        long _Id = db.insert(Client.TABLE, null, values);
        db.close();
        return (int) _Id;
    }

    //delete from database
    public void delete(int _Id, Context context) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(Client.TABLE, Client.KEY_ID + "= ?", new String[]{String.valueOf(_Id)});
        SessionRepo sessionRepo = new SessionRepo(context);
        sessionRepo.delete(_Id, context);
        db.close();
    }

    //update database
    public void update(Client client) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();

        //set pair values
        values.put(Client.KEY_CLIENT_ID, client.client_ID);
        values.put(Client.KEY_AGE, client.age);

        //update values
        db.update(Client.TABLE, values, Client.KEY_ID + "= ?", new String[]{String.valueOf(client._ID)});
        db.close();
    }

    //hashmap
    public ArrayList<HashMap<String, String>> getClientList() {
        //open read only database
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String selectQuery = "SELECT " +
                Client.KEY_ID + "," +
                Client.KEY_CLIENT_ID + "," +
                Client.KEY_AGE +
                " FROM " + Client.TABLE;

        ArrayList<HashMap<String, String>> clientList = new ArrayList<HashMap<String, String>>();

        //loops through all rows, adds to list
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                HashMap<String, String> client = new HashMap<String, String>();
                client.put("_Id", cursor.getString(cursor.getColumnIndex(Client.KEY_ID)));
                client.put("client_id", cursor.getString(cursor.getColumnIndex(Client.KEY_CLIENT_ID)));
                client.put("age", cursor.getString(cursor.getColumnIndex(Client.KEY_AGE)));
                clientList.add(client);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return clientList;
    }

    public Client getClientById(int Id) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String selectQuery = "SELECT " +
                Client.KEY_ID + "," +
                Client.KEY_CLIENT_ID + "," +
                Client.KEY_AGE +
                " FROM " + Client.TABLE +
                " WHERE " +
                Client.KEY_ID + "= ?";

        int i = 0;          //counter
        Client client = new Client();

        //set cursor to id
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                client._ID = cursor.getInt(cursor.getColumnIndex(Client.KEY_ID));
                client.client_ID = cursor.getInt(cursor.getColumnIndex(Client.KEY_CLIENT_ID));
                client.age = cursor.getInt(cursor.getColumnIndex(Client.KEY_AGE));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return client;
    }
}
