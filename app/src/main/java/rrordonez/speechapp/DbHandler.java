package rrordonez.speechapp;

/**
 * Created by ReyDonez on 10/13/2015.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DbHandler extends SQLiteOpenHelper {

    //version number
    private static final int DATABASE_VERSION = 24;

    //database name
    private static final String DATABASE_NAME = "ClientDb.db";


    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //creates clients table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Client.CREATE_TABLE_CLIENT);
        db.execSQL(Session.CREATE_TABLE_SESSION);
        db.execSQL(Exercise.CREATE_TABLE_EXERSICE);
    }

    //updates database if new version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Client.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Session.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Exercise.TABLE);

        //creates tables again
        onCreate(db);
    }

}
