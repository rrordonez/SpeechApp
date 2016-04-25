package rrordonez.speechapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ReyDonez on 11/12/2015.
 */


public class ExerciseActivity extends ActionBarActivity implements android.view.View.OnClickListener {

    //display word
    TextView word;
    //display score
    TextView score;

    //date
    Date date = new Date();
    long start;

    //correct button
    Button btnCorrect;
    //incorrect button
    Button btnIncorrect;
    //button for new exercise in same session
    Button btnNewExercise;

    //score = correct / total
    long correct = 0;
    long total = 0;

    //letter and position selected in SessionSelection2
    String letter = "";
    String position = "";

    //client and session id
    private long _Session_Id;


    //name of the word list file
    String testFileName = "wordList.txt";

    List<String> wordList;

    //counter to iterate through list of words
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        //text views
        word = (TextView) findViewById(R.id.word);
        score = (TextView) findViewById(R.id.score);

        //start time of session
        start = date.getTime();

        //buttons
        btnCorrect = (Button) findViewById(R.id.btnCorrect);
        btnIncorrect = (Button) findViewById(R.id.btnIncorrect);
        btnNewExercise = (Button) findViewById(R.id.btnNewExercise);

        //button listener
        btnCorrect.setOnClickListener(this);
        btnIncorrect.setOnClickListener(this);
        btnNewExercise.setOnClickListener(this);

        //get letter and position from selection2
        Intent intent = getIntent();
        letter = intent.getStringExtra("letter");
        position = intent.getStringExtra("position");
        wordList = getWordsFromFile("wordList.txt", this, letter, position);

        //create repo for exercise and session tables
        ExerciseRepo exerciseRepo = new ExerciseRepo(this);
        SessionRepo sessionRepo = new SessionRepo(this);

        //display first word
        word.setText(wordList.get(0));

        //get session id
        _Session_Id = 0;
        _Session_Id = intent.getLongExtra("session", 0);
    }

    public void onClick(View view) {
        //if correct then move to next word
        if (view == btnCorrect) {
            //if i is less than the size of the list then display next word
            if (wordList.size() >= 1) {
                word.setText(wordList.get(i));
                i++;
                correct++;      //add 1 to correct answers
                total++;        //add 1 to total done
                //else stop
            } else {
                word.setText("End of List");
            }
            //if incorrect then move to next word
        } else if (view == btnIncorrect) {
            //if i is less than the size of the list then display next word
            if (wordList.size() >= 1) {
                word.setText(wordList.get(i));
                i++;
                total++;        //only add 1 to the total done
                // else stop
            } else {
                word.setText("End of List");
            }
        }else if (view == btnNewExercise) {
            ExerciseRepo exerciseRepo = new ExerciseRepo(this);
            //new exercise
            Exercise exercise = new Exercise();

            //set exercise variables
            exercise.letter = letter;
            exercise.position = position;
            if (total > 0) {
                exercise.score = (int)((correct/total)*100);
            }
            exercise.session_ID = _Session_Id;
            Toast.makeText(this, String.valueOf(_Session_Id), Toast.LENGTH_SHORT).show();
            exerciseRepo.insert(exercise);

            finish();
        }
            score.setText(String.valueOf(correct) + "/" + String.valueOf(total));
    }

    //gets a list of words from a text file that have the selected letter in the selected position
    public List<String> getWordsFromFile(String textFileName, Context context, String letter, String position) {
        List<String> wordList = new ArrayList<>();
        AssetManager am = context.getAssets();

        //buffer to read in a line
        BufferedReader bufferedReader = null;
        String line;

        try {
            InputStream is = am.open(textFileName);
            bufferedReader = new BufferedReader(new InputStreamReader(is));
            //while line does not equal null
            while ((line = bufferedReader.readLine()) != null) {
                //if the position == initial
                if (position.equals("initial")) {
                    //if line starts with letter then add it to wordList
                    if (line.startsWith(letter)) {
                        wordList.add(line);
                    } else {
                        word.setText("No word with letter " + letter + " at the position " + position);
                    }
                }//else if the position == middle
                else if (position.equals("middle")) {
                    //if line has the letter in the middle of the word then add word to list
                    for(int i=1; i < line.length()-1; i++) {
                        String middleLetter = String.valueOf(line.charAt(i));
                        if (letter.equals(middleLetter)) {
                            wordList.add(line);
                        }
                    }
                }//else if the position == end
                else if (position.equals("end")) {
                    //if line ends with letter then add it to wordList
                    if (line.endsWith(letter)) {
                        wordList.add(line);
                    } else {
                        word.setText("No word with letter " + letter + " at the position " + position);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return wordList;
    }
}