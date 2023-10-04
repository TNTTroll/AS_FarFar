package com.example.far_faraway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.far_faraway.Puzzles._PUZZLES;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // <<< Scene 1
    public static ArrayList<HolderInfo> holders1 = new ArrayList<>();
    public static ArrayList<ObjectInfo> objects1 = new ArrayList<>();
    public static ArrayList<PuzzleInfo> puzzles1 = new ArrayList<>();

    // <<< Scene 2
    public static ArrayList<HolderInfo> holders2 = new ArrayList<>();
    public static ArrayList<ObjectInfo> objects2 = new ArrayList<>();
    public static ArrayList<PuzzleInfo> puzzles2 = new ArrayList<>();

    // <<< Scene 3
    public static ArrayList<HolderInfo> holders3 = new ArrayList<>();
    public static ArrayList<ObjectInfo> objects3 = new ArrayList<>();
    public static ArrayList<PuzzleInfo> puzzles3 = new ArrayList<>();

    // <<< Additional
    public static boolean[] firstSignsTurn = new boolean[_PUZZLES.firstSignsSequence.length];

    public static boolean firstElectricity = false;
    public static int[] firstPipesAngle = new int[_PUZZLES.firstPipesSequence.length];

    public static boolean firstTableTookAnti = false;
    public static boolean firstTableMedicineDone = false;

    public static boolean[] flowers = new boolean[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            getItems();
        } catch (IOException ignored) {}

        Button play = (Button) findViewById(R.id.menu_btn_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scene = new Intent(MainActivity.this, Scene.class);
                startActivity(scene);
            }
        });
    }

    private void getItems() throws IOException {

        String text = "";

        try {
            InputStream file = getAssets().open("Items.txt");
            byte[] buffer = new byte[file.available()];

            file.read(buffer);

            text = new String(buffer);
            String[] words = text.split("\n");

            int count = 0;
            int isObjects = 1;
            for (int i = 0; i < words.length; i++) {

                String word = words[i].trim();

                if ( word.equals("") ) {}
                else if ( word.equals("=scene") )
                    count += 1;
                else if ( word.equals("-objects") )
                    isObjects = 1;
                else if ( word.equals("-holders") )
                    isObjects = 2;
                else if ( word.equals("-puzzles") )
                    isObjects = 3;

                else {

                    String[] parameter = word.split("//");
                    switch (count) {
                        case 1:
                            if      (isObjects == 1)
                                objects1.add(new ObjectInfo(parameter[0], parameter[1]));
                            else if (isObjects == 2)
                                holders1.add(new HolderInfo(parameter[0], parameter[1], parameter[2]));
                            else
                                puzzles1.add(new PuzzleInfo(parameter[0], parameter[1], parameter[2]));
                            break;
                        case 2:
                            if      (isObjects == 1)
                                objects2.add(new ObjectInfo(parameter[0], parameter[1]));
                            else if (isObjects == 2)
                                holders2.add(new HolderInfo(parameter[0], parameter[1], parameter[2]));
                            else
                                puzzles2.add(new PuzzleInfo(parameter[0], parameter[1], parameter[2]));
                            break;
                        case 3:
                            if      (isObjects == 1)
                                objects3.add(new ObjectInfo(parameter[0], parameter[1]));
                            else if (isObjects == 2)
                                holders3.add(new HolderInfo(parameter[0], parameter[1], parameter[2]));
                            else
                                puzzles3.add(new PuzzleInfo(parameter[0], parameter[1], parameter[2]));
                            break;
                    }
                }

            }

            file.close();

        } catch (IOException ignored) {}

    }

}