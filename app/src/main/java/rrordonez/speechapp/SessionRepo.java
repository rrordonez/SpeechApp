package rrordonez.speechapp;

/**
 * Created by ReyDonez on 11/4/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;


public class SessionRepo {
    private DbHandler dbHandler;

    public SessionRepo(Context context) {
        dbHandler = new DbHandler(context);
    }

    //insert into database
    public int insert(Session session) {
        //write data
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();

        //set pair values
        values.put(Session.KEY_CLIENT_ID, session.client_ID);
        values.put(Session.KEY_DATE, session.date);
        values.put(Session.KEY_START, session.start);
        values.put(Session.KEY_END, session.end);
        values.put(Session.KEY_AVERAGE, session.average);

        //insert row
        long _Id = db.insert(Session.TABLE, null, values);
        db.close();
        return (int) _Id;
    }

    //delete from database
    public void delete(int _Id, Context context) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(Session.TABLE, Session.KEY_SESSION_ID + "= ?", new String[]{String.valueOf(_Id)});
        ExerciseRepo exerciseRepo = new ExerciseRepo(context);
        exerciseRepo.delete((int)getKey(context));
        db.close();
    }

    //update database
    public void update(Session session) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();

        //set pair values
        values.put(Session.KEY_CLIENT_ID, session.client_ID);
        values.put(Session.KEY_DATE, session.date);
        values.put(Session.KEY_START, session.start);
        values.put(Session.KEY_END, session.end);
        values.put(Session.KEY_AVERAGE, session.average);

        //update values
        db.update(Session.TABLE, values, Session.KEY_SESSION_ID + "= ?", new String[]{String.valueOf(session.session_ID)});
        db.close();
    }
    //returns a list of sessions with selected client id
    public ArrayList<HashMap<String, String>> getSessionList(int Id) {
        //query to select sessions with id
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String selectQuery = "SELECT " +
                Session.KEY_SESSION_ID + "," +
                Session.KEY_CLIENT_ID + "," +
                Session.KEY_DATE + "," +
                Session.KEY_START + "," +
                Session.KEY_END + "," +
                Session.KEY_AVERAGE +
                " FROM " + Session.TABLE +
                " WHERE " +
                Session.KEY_CLIENT_ID + "= ?";

        //new array list for list of sessions with given client id
        ArrayList<HashMap<String, String>> sessionList = new ArrayList<HashMap<String, String>>();

        //loops through all the rows, adds to list\
        Cursor cursor = db.rawQuery(selectQuery,  new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> session = new HashMap<String, String>();
                session.put("session_ID", cursor.getString(cursor.getColumnIndex(Session.KEY_SESSION_ID)));
                session.put("client_id", cursor.getString(cursor.getColumnIndex(Session.KEY_CLIENT_ID)));
                session.put("date", cursor.getString(cursor.getColumnIndex(Session.KEY_DATE)));
                session.put("start", cursor.getString(cursor.getColumnIndex(Session.KEY_START)));
                session.put("end", cursor.getString(cursor.getColumnIndex(Session.KEY_END)));
                session.put("average", cursor.getString(cursor.getColumnIndex(Session.KEY_AVERAGE)));
                sessionList.add(session);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return sessionList;
    }

    public Session getSessionByClientId(int Id) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String selectQuery = "SELECT " +
                Session.KEY_SESSION_ID + "," +
                Session.KEY_CLIENT_ID + "," +
                Session.KEY_DATE + "," +
                Session.KEY_START + "," +
                Session.KEY_END + "," +
                Session.KEY_AVERAGE + "," +
                " FROM " + Session.TABLE +
                " WHERE " +
                Session.KEY_CLIENT_ID + "= ?";

        int i = 0;          //counter
        Session session = new Session();

        //set cursor to client id
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                session.session_ID = cursor.getInt(cursor.getColumnIndex(Session.KEY_SESSION_ID));
                session.client_ID = cursor.getInt(cursor.getColumnIndex(Session.KEY_CLIENT_ID));
                session.date = cursor.getString(cursor.getColumnIndex(Session.KEY_DATE));
                session.start = cursor.getInt(cursor.getColumnIndex(Session.KEY_START));
                session.end = cursor.getInt(cursor.getColumnIndex(Session.KEY_END));
                session.average = cursor.getInt(cursor.getColumnIndex(Session.KEY_AVERAGE));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return session;
    }
    public long getKey(Context context){
        long key = 0;
        dbHandler = new DbHandler(context);
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        String sql = "SELECT " +
                Session.KEY_SESSION_ID + "," +
                Session.KEY_CLIENT_ID + "," +
                Session.KEY_DATE + "," +
                Session.KEY_START + "," +
                Session.KEY_END + "," +
                Session.KEY_AVERAGE +
                " FROM " + Session.TABLE +
                " ORDER BY " +
                Session.KEY_SESSION_ID +
                " DESC LIMIT 1";

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()){
            key = ((int) cursor.getLong(0));
        }
        //query number of elements in table
        String query = "SELECT COUNT(*) FROM " + Session.TABLE;

        Cursor cursor1 = db.rawQuery(query, null);
        cursor1.moveToFirst();
        int i = cursor1.getInt(0);
        //if table is empty, key = 1
        if(i == 0){
            key = 1;
        }//else add 1 to key
        else
        {
            key = key +1;
        }
        cursor.close();
        db.close();
        return key;
    }
}


