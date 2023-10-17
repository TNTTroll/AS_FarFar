package com.example.far_faraway;

import static com.example.far_faraway.MainActivity.player;
import static com.example.far_faraway.MainActivity.setLevel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Ending extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        Object back = (Object) findViewById(R.id.endBack);
        back.setOnClickListener(v -> {
            setLevel(1);
            startActivity( new Intent(this, MainActivity.class) );
        });

        TextView text = (TextView) findViewById(R.id.endText);
        text.setText("Thank you for playing, " + player.getName());
    }

}