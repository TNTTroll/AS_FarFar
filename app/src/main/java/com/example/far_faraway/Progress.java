package com.example.far_faraway;

import static com.example.far_faraway.MainActivity.player;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Progress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        ImageButton bg = (ImageButton) findViewById(R.id.progressBG);
        bg.setEnabled(false);

        Object back = (Object) findViewById(R.id.progressBack);
        back.setOnClickListener(v -> {
            startActivity( new Intent(this, MainActivity.class) );
        });

        TextView text = (TextView) findViewById(R.id.progressText);
        text.setText("Hello, " + player.getName());
    }

}