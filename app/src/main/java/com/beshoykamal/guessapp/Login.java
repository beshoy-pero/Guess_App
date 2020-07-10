package com.beshoykamal.guessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText nametext,passwordtext;
    MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        player = MediaPlayer.create(this, R.raw.gesssong);
        player.start();


    }

    @Override
    public void onBackPressed() {
        if (player != null)
            player.stop();
        super.onBackPressed();
    }

    public void next(View view) {
        nametext=findViewById(R.id.nametext);
        passwordtext=findViewById(R.id.passwordtext);

        Intent in=new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("Name",nametext.getText().toString());
        b.putString("password",passwordtext.getText().toString());

            in.putExtras(b);

            String s=nametext.getText().toString();
            String ps=passwordtext.getText().toString();
            if ((!s.equals(""))&&(!ps.equals(""))) {
                startActivity(in);
                if (player != null)
                    player.stop();

            }
            else
        Toast.makeText(this, "enter name and password correct", Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "enter "+b, Toast.LENGTH_SHORT).show();
//        return;


    }

}
