package rrordonez.speechapp;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by ReyDonez on 11/3/2015.
 */
public class Session {
    //table name
    public static final String TABLE = "Session";

    //table column names
    public static final String KEY_SESSION_ID = "session_ID";
    public static final String KEY_CLIENT_ID = "client_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_START = "start";
    public static final String KEY_END = "end";
    public static final String KEY_AVERAGE = "average";

    //create table string
    public static final String CREATE_TABLE_SESSION = "CREATE TABLE " + Session.TABLE + "("
            + Session.KEY_SESSION_ID + " INTEGER PRIMARY KEY, "
            + Session.KEY_CLIENT_ID + " INTEGER, "
            + Session.KEY_DATE + " TEXT, "
            + Session.KEY_START + " INTEGER, "
            + Session.KEY_END + " INTEGER, "
            + Session.KEY_AVERAGE + " INTEGER)";

    //properties
    public int session_ID;
    public int client_ID;
    public String date;
    public long start;
    public long end;
    public int average;

}
