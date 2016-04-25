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


public class ExerciseRepo {
    private DbHandler dbHandler;

    public ExerciseRepo(Context context) {
        dbHandler = new DbHandler(context);
    }

    //insert into database
    public int insert(Exercise exercise) {
        //write data
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();

        //set pair values
        values.put(Exercise.KEY_SESSION_ID, exercise.session_ID);
        values.put(Exercise.KEY_LETTER, exercise.letter);
        values.put(Exercise.KEY_POSITION, exercise.position);
        values.put(Exercise.KEY_SCORE, exercise.score);

        //insert row
        long _Id = db.insert(Exercise.TABLE, null, values);
        db.close();
        return (int) _Id;
    }

    //delete from database
    public void delete(int _Id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(Exercise.TABLE, Exercise.KEY_EXERCISE_ID + "= ?", new String[] {String.valueOf(_Id) } );
        db.close();
    }

    //update database
    public void update(Exercise exercise) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();

        //set pair values
        values.put(Exercise.KEY_SESSION_ID, exercise.session_ID);
        values.put(Exercise.KEY_LETTER, exercise.letter);
        values.put(Exercise.KEY_POSITION, exercise.position);
        values.put(Exercise.KEY_SCORE, exercise.score);

        //update values
        db.update(Exercise.TABLE, values, Exercise.KEY_EXERCISE_ID + "= ?", new String[]{String.valueOf(exercise.exercise_ID)});
        db.close();
    }

    public ArrayList<HashMap<String, String>> getExerciseList(int Id) {
        //query to select exercises in a session
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String selectQuery = "SELECT " +
                Exercise.KEY_EXERCISE_ID + "," +
                Exercise.KEY_SESSION_ID + "," +
                Exercise.KEY_LETTER + "," +
                Exercise.KEY_POSITION + "," +
                Exercise.KEY_SCORE +
                " FROM " + Exercise.TABLE +
                " WHERE " +
                Exercise.KEY_SESSION_ID + "= ?";

        //new array list for list of sessions with given client id
        ArrayList<HashMap<String, String>> exerciseList = new ArrayList<HashMap<String, String>>();

        //loops through all the rows, adds to list\
        Cursor cursor = db.rawQuery(selectQuery,  new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> exercise = new HashMap<String, String>();
                exercise.put("exercise_ID", cursor.getString(cursor.getColumnIndex(Exercise.KEY_EXERCISE_ID)));
                exercise.put("session_ID", cursor.getString(cursor.getColumnIndex(Exercise.KEY_SESSION_ID)));
                exercise.put("letter", cursor.getString(cursor.getColumnIndex(Exercise.KEY_LETTER)));
                exercise.put("position", cursor.getString(cursor.getColumnIndex(Exercise.KEY_POSITION)));
                exercise.put("score", cursor.getString(cursor.getColumnIndex(Exercise.KEY_SCORE)));
                exerciseList.add(exercise);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return exerciseList;
    }
}