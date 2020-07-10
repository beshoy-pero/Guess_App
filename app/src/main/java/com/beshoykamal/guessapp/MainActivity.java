package com.beshoykamal.guessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.nisrulz.sensey.FlipDetector;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements ShakeDetector.ShakeListener, TextToSpeech.OnInitListener, FlipDetector.FlipListener {
    TextView wrongrighttext, countwrongtext,go;
    int x, s, wrong, good, close, num;
    Button next;




    ArrayList<Integer> numpers = new ArrayList<>();
    TextView lasttext;


    TextView[] viewtext;
    Switch switch1;
    int last;
    int score = 0, game = 0;
    byte sw=0;

    List<Integer> colortext = Arrays.asList(Color.parseColor("#79F44336"),Color.parseColor("#37A600D4"),
            Color.parseColor("#57009688"),Color.parseColor("#3E21F3DB"),Color.parseColor("#45FFEB3B"),
            Color.parseColor("#57005248"),Color.parseColor("#9C021986"),Color.parseColor("#8D2196F3")
            ,Color.parseColor("#5B00FA06"));


    List<Integer> numpertext = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    TextToSpeech tts;
    String statuof, statuon;
    SharedPreferences pref;
    MediaPlayer  player1 ;
    MediaPlayer  player4 ;
    MediaPlayer  player3 ;
    MediaPlayer  player2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wrongrighttext = findViewById(R.id.wrongrighttext);
        countwrongtext = findViewById(R.id.countwrongtext);
        next = findViewById(R.id.next);


        go = findViewById(R.id.go);
        Bundle b = getIntent().getExtras();
        String user = b.getString("Name");
        String password = b.getString("password");
        go.setText("Welcom : "+user+" your pass : "+password);



        player1 = MediaPlayer.create(this, R.raw.reset);
        player3 = MediaPlayer.create(this, R.raw.good);
        player4 = MediaPlayer.create(this, R.raw.bump);
        player2 = MediaPlayer.create(this, R.raw.perfect1);


        viewtext = Arrays.asList(findViewById(R.id.text1), findViewById(R.id.text2), findViewById(R.id.text3),
                findViewById(R.id.text4), findViewById(R.id.text5), findViewById(R.id.text6),
                findViewById(R.id.text7), findViewById(R.id.text8), findViewById(R.id.text9)).toArray(new TextView[0]);

        switch1 = findViewById(R.id.switch1);
        lasttext = findViewById(R.id.lasttext);

        pref = getPreferences(MODE_PRIVATE);
        last = pref.getInt("SCORE", 0);
        int gm = pref.getInt("FROM", 0);
        if (last != 0)
            lasttext.setText("last score  " + last + " from " + gm + " game");


        //     tts=new TextToSpeech(this,this);


        Sensey.getInstance().init(this);
        Sensey.getInstance().startShakeDetection(this);
        Sensey.getInstance().startFlipDetection(this);
        if (!switch1.getTextOff().toString().equals(statuon)){

            player2.start();
        }
    }

    @Override
    public void onBackPressed() {

        if (score > 0) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("SCORE", score);
            editor.putInt("FROM", game);
            editor.commit();
        }
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {


        player2.start();

        super.onResume();
    }

    @Override
    protected void onPause() {
        player2.pause();
        super.onPause();
    }

    public void onoff(View view) {


         if (switch1.isChecked()) {

            statuon = switch1.getTextOn().toString();
            Toast.makeText(this, "sound " + statuon, Toast.LENGTH_SHORT).show();
                 player1.pause();

                 player3.pause();
                 player4.pause();

        }
        else {
            statuon = switch1.getTextOff().toString();
            Toast.makeText(this, "sound " + statuon, Toast.LENGTH_SHORT).show();
             player2.pause();
        }
    }

    public void start(View view) {
        player2.pause();
        game++;

        Random r = new Random();
        x = r.nextInt(9) + 1;




        Toast.makeText(this, "gess a color", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "color numper " + x, Toast.LENGTH_SHORT).show();
        countwrongtext.setText("");
        wrongrighttext.setText("");
        good = 0;
        wrong = 0;
        numpers.clear();

        YoYo.with(Techniques.Wave).duration(200).repeat(1).playOn(next);

        close++;
        if (close == 3) {
            finish();

        }
        if (!switch1.getTextOff().toString().equals(statuon)){

            player1.start();
        }



        Collections.shuffle(Arrays.asList(viewtext));
        Collections.shuffle(numpertext);


        for (int i = 0; i < viewtext.length; i++) {
            viewtext[i].setText("" + numpertext.get(i));

        }
        Collections.shuffle(colortext);

        for (int i = 1; i < viewtext.length; i++) {
            viewtext[i].setBackgroundColor(colortext.get(i));
            YoYo.with(Techniques.Bounce).duration(800).repeat(1).playOn((viewtext[i]));



        }

        }


    public void answer(View view) {




        if (x == 0) {
            Toast.makeText(this, "pleas press start or SHAKE phone", Toast.LENGTH_SHORT).show();
            return;

        }

//            player1.stop();

        TextView tv = (TextView) view;
        int num = Integer.parseInt(tv.getText().toString());
////////////////////////////////////////////////////////////////////////////////////

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            tts.speak(""+num,TextToSpeech.QUEUE_FLUSH,null,null);
//        }
//        else
//            tts.speak(""+num,TextToSpeech.QUEUE_FLUSH,null);
//////////////////////////////////////////////////////////////////////////////////////

        YoYo.with(Techniques.Wave).duration(200).repeat(1).playOn(view);

        if (numpers.contains(num)) {
            Toast.makeText(this, "another color", Toast.LENGTH_SHORT).show();
            return;
        }

        if (num == x) {
            wrongrighttext.setText("Good ");
            good++;
            score++;

            countwrongtext.setText("" + good);
            x = 0;

            YoYo.with(Techniques.Flash).duration(800).playOn(view);
            close = 0;
            if (!switch1.getTextOff().toString().equals(statuon)) {

                player3.start();
            }
        }
        else {


            wrongrighttext.setText("wrong ");
            wrong++;
            countwrongtext.setText("" + wrong);
            numpers.add(num);
            YoYo.with(Techniques.Wave).duration(200).repeat(1).playOn(view);
//            YoYo.with(Techniques.BounceInUp).duration(100).repeat(1).playOn(view);
            if (!switch1.getTextOff().toString().equals(statuon)) {

                player4.start();

            }

            close = 0;

        }
        if (wrong == 4) {
            Toast.makeText(this, "game over \n press 3 start to exit", Toast.LENGTH_SHORT).show();
            x = 0;

        }
    }


    @Override
    public void onShakeDetected() {


    }

    @Override
    public void onShakeStopped() {

    }

    @Override
    public void onInit(int status) {
//        if(status==TextToSpeech.SUCCESS){
//            tts.setPitch(0.7f);
//        }
//        else
//            Toast.makeText(this, "version not support", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onFaceDown() {

    }

    @Override
    public void onFaceUp() {


    }

}
