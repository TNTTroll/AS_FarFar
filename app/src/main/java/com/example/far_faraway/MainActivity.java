package com.example.far_faraway;

import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.reloadInventory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.far_faraway.Puzzles._PUZZLES;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // MainActivity.setLevel(lvl);
    // MainActivity.setAchievement(index);
    // MainActivity.playAudio("song");
    // Scene.showText(_PUZZLES.lore[index]);

    // <<< Savings
    public static final String saveFile = "Player.txt";
    public static Player player = new Player();

    private Dialog dialog_player, dialog_lvl, dialog_tutorial;

    public static MainActivity thisContext;

    public static Display display;

    public static boolean[] loreSaw = new boolean[_PUZZLES.lore.length];

    // <<< Additional
    public static int wateredFlowers = 1;
    public static int[] flowers = new int[3];
    public static boolean canTook = false;

    // <<< FIRST
    public static ArrayList<HolderInfo> holders1 = new ArrayList<>();
    public static ArrayList<ObjectInfo> objects1 = new ArrayList<>();
    public static ArrayList<PuzzleInfo> puzzles1 = new ArrayList<>();

    public static boolean[] firstSignsTurn = new boolean[_PUZZLES.firstSignsLength];

    public static boolean firstElectricity = false;
    public static int[] firstPipesAngle = new int[_PUZZLES.firstPipesSequence.length];

    public static boolean firstLamps = false;

    public static boolean firstClosetCleaned = false;
    public static boolean firstClosetTookCard = false;

    public static boolean firstTableTookAnti = false;
    public static boolean firstTableMedicineDone = false;

    public static boolean firstLevelComplete = false;

    // <<< SECOND
    public static ArrayList<HolderInfo> holders2 = new ArrayList<>();
    public static ArrayList<ObjectInfo> objects2 = new ArrayList<>();
    public static ArrayList<PuzzleInfo> puzzles2 = new ArrayList<>();

    public static boolean secondSibasDone = false;

    public static boolean[] secondsPassed = new boolean[3];

    // <<< THIRD
    public static ArrayList<HolderInfo> holders3 = new ArrayList<>();
    public static ArrayList<ObjectInfo> objects3 = new ArrayList<>();
    public static ArrayList<PuzzleInfo> puzzles3 = new ArrayList<>();

    public static boolean thirdCupsDone = false;

    public static boolean[] thirdLampsState = new boolean[_PUZZLES.thirdAdjacentLength];

    public static boolean thirdAdjacentDone = false;

    public static boolean thirdTeethDone = false;

    public static boolean[] thirdMazeState = new boolean[_PUZZLES.thirdMazeCorrectState.length];
    public static boolean thirdMazeDone = false;

    public static int thirdEnding = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        thisContext = this;

        display = getWindowManager().getDefaultDisplay();

        dialog_player = new Dialog(this);
        dialog_lvl = new Dialog(this);
        dialog_tutorial = new Dialog(this);

        ImageButton bg = (ImageButton) findViewById(R.id.mainBG);
        bg.setEnabled(false);

        if (!loadInfo()) {
            try {
                openRegistration(findViewById(R.id.menuMain));
            } catch (IOException ignored) {}
        } else
            Toast.makeText(getApplicationContext(), "Welcome back, " + player.getName(), Toast.LENGTH_LONG).show();

        try {
            getItems();
        } catch (IOException ignored) {}

        Button play = (Button) findViewById(R.id.menuBtnPlay);
        play.setOnClickListener(view -> {
            Intent scene = new Intent(MainActivity.this, Scene.class);
            startActivity(scene);
        });

        Button progress = (Button) findViewById(R.id.menuBtnProgress);
        progress.setOnClickListener(view -> {
            Intent scene = new Intent(MainActivity.this, Progress.class);
            startActivity(scene);
        });

        Button level = (Button) findViewById(R.id.menuBtnLevel);
        level.setOnClickListener(view -> openLevels(findViewById(R.id.menuMain)));

        Button tutorial = (Button) findViewById(R.id.menuBtnTutorial);
        tutorial.setOnClickListener(view -> {
            dialog_tutorial.setContentView(R.layout.fragment_tutorial);

            Object back = (Object) dialog_tutorial.findViewById(R.id.tutorialBack);
            back.setOnClickListener(v -> dialog_tutorial.dismiss());

            dialog_tutorial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog_tutorial.show();
        });

        Button sure = (Button) findViewById(R.id.menuBtnAccept);

        Button clear = (Button) findViewById(R.id.menuBtnClear);
        clear.setVisibility(View.GONE);
        clear.setOnClickListener(view -> {
            restartAll();

            clear.setVisibility(View.GONE);
            sure.setVisibility(View.VISIBLE);
        });

        sure.setOnClickListener(view -> {
            sure.setVisibility(View.GONE);
            clear.setVisibility(View.VISIBLE);
        });

        wateredFlowers = flowers[0] + flowers[1] + flowers[2] + 1;

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

    private void openLevels(View view) {
        dialog_lvl.setContentView(R.layout.fragment_levels);

        ImageButton no = (ImageButton) dialog_lvl.findViewById(R.id.lvlNo);
        no.setOnClickListener(v -> dialog_lvl.dismiss());

        for (int index = 1; index <= 3; index++) {
            int resId = getResId("lvl_" + index, R.id.class);
            Object lvl = (Object) dialog_lvl.findViewById(resId);

            int finalIndex = index;
            lvl.setOnClickListener(v -> {
                setLevel(finalIndex);

                Intent scene = new Intent(MainActivity.thisContext, Scene.class);
                startActivity(scene);

                dialog_lvl.dismiss();
            });

            if (index > player.getLevel()) {
                lvl.setIcon("no");
                lvl.setEnabled(false);
            }
        }

        dialog_lvl.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_lvl.show();
    }

    private void openRegistration(View view) throws IOException {
        dialog_player.setContentView(R.layout.fragment_registration);


        Object captcha = (Object) dialog_player.findViewById(R.id.regCaptcha);

        int capAnswer = new Random().nextInt(3) + 1;

        captcha.setIcon("captcha_" + capAnswer);


        Button dialog_done = (Button) dialog_player.findViewById(R.id.regDone);
        dialog_done.setOnClickListener(v -> {

            EditText getName = dialog_player.findViewById(R.id.regName);
            String playerInfo = "";
            playerInfo += getName.getText().toString();

            EditText getCaptcha = dialog_player.findViewById(R.id.regCaptchaAnswer);

            if (getCaptcha.getText().toString().equals(""))
                Toast.makeText(getApplicationContext(), "Captcha is empty", Toast.LENGTH_SHORT).show();

            else if (playerInfo.equals(""))
                Toast.makeText(getApplicationContext(), "Name is empty", Toast.LENGTH_SHORT).show();

            else {
                if (Integer.parseInt( getCaptcha.getText().toString() ) != _PUZZLES.registrationAnswers[capAnswer-1]) {
                    Toast.makeText(getApplicationContext(), "Captcha is wrong", Toast.LENGTH_SHORT).show();
                    getCaptcha.setText("");

                } else {
                    try {
                        FileOutputStream outputStream = openFileOutput(saveFile, Context.MODE_PRIVATE);

                        player.setParam(playerInfo, 1 );

                        playerInfo = playerInfo + "&1&000" + '\n';
                        outputStream.write(playerInfo.getBytes());

                        outputStream.close();

                        dialog_player.dismiss();

                    } catch (IOException ignored) {}
                }
            }

        });

        dialog_player.setCanceledOnTouchOutside(false);
        dialog_player.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_player.show();
    }

    private boolean loadInfo() {

        try {

            InputStream inputS = null;
            inputS = new FileInputStream(getFilesDir() + "/" + saveFile);
            BufferedReader bufferReaderPlayer = new BufferedReader(new InputStreamReader(inputS));

            String[] information_Player = bufferReaderPlayer.readLine().split("&");

            player.setParam(information_Player[0], Integer.parseInt(information_Player[1]) );

            for (int index = 0; index < 3; index++)
                flowers[index] = Integer.parseInt("" + information_Player[2].charAt(index));

            inputS.close();

            return information_Player[0].length() != 0;

        } catch (Exception e) {
            new File(getFilesDir() + "/" + saveFile);

            return false;
        }
    }

    public static void setLevel(int level) {

        List<String> playerAchievements = new ArrayList<String>();

        try {

            InputStream inputS = null;
            inputS = new FileInputStream(thisContext.getFilesDir() + "/" + saveFile);
            BufferedReader bufferReaderPlayer = new BufferedReader(new InputStreamReader(inputS));

            List<String> information_Player = new ArrayList<String>();

            while (bufferReaderPlayer.ready()) { information_Player.add( bufferReaderPlayer.readLine() ); }

            if (information_Player.size() > 1)
                for (int i = 1; i < information_Player.size(); i++)
                    playerAchievements.add( information_Player.get(i) );

            inputS.close();

        } catch (Exception ignored) {}

        FileOutputStream outputStream = null;
        try {
            outputStream = thisContext.openFileOutput(saveFile, Context.MODE_PRIVATE);
        } catch (FileNotFoundException ignored) {}

        try {
            player.setParam(player.getName(), level);

            assert outputStream != null;
            String playerInfo = player.getName() + "&" + level + "&" + flowers[0] + flowers[1] + flowers[2] + '\n';
            outputStream.write(playerInfo.getBytes());

            for (String str : playerAchievements)
                outputStream.write((str + '\n').getBytes());

            outputStream.close();
        } catch (IOException ignored) {}

    }

    public static boolean getAchievement(int a) {

        String achieve = _PUZZLES.achievements[a];

        List<String> playerAchievements = new ArrayList<String>();

        try {

            InputStream inputS = null;
            inputS = new FileInputStream(thisContext.getFilesDir() + "/" + saveFile);
            BufferedReader bufferReaderPlayer = new BufferedReader(new InputStreamReader(inputS));

            List<String> information_Player = new ArrayList<String>();

            while (bufferReaderPlayer.ready()) { information_Player.add( bufferReaderPlayer.readLine() ); }

            if (information_Player.size() > 1)
                for (int i = 1; i < information_Player.size(); i++)
                    playerAchievements.add( information_Player.get(i) );

            inputS.close();

        } catch (Exception ignored) {}

        return playerAchievements.contains(achieve.trim());
    }

    public static void setAchievement(int a) {

        String achieve = _PUZZLES.achievements[a];

        List<String> playerAchievements = new ArrayList<String>();

        try {

            InputStream inputS = null;
            inputS = new FileInputStream(thisContext.getFilesDir() + "/" + saveFile);
            BufferedReader bufferReaderPlayer = new BufferedReader(new InputStreamReader(inputS));

            List<String> information_Player = new ArrayList<String>();

            while (bufferReaderPlayer.ready()) { information_Player.add( bufferReaderPlayer.readLine() ); }

            if (information_Player.size() > 1)
                for (int i = 1; i < information_Player.size(); i++)
                    playerAchievements.add( information_Player.get(i) );

            inputS.close();

        } catch (Exception ignored) {}

        if ( !playerAchievements.contains(achieve.trim()) ) {
            FileOutputStream outputStream = null;
            try {
                outputStream = thisContext.openFileOutput(saveFile, Context.MODE_APPEND);
            } catch (FileNotFoundException ignored) {}

            try {
                assert outputStream != null;

                Toast.makeText(thisContext.getApplicationContext(), "Achievement get: " + _PUZZLES.achievementsExplain[a], Toast.LENGTH_LONG).show();

                achieve += '\n';
                outputStream.write(achieve.getBytes());

                outputStream.close();

            } catch (IOException ignored) {}

        }
    }

    private void restartAll() {
        setLevel(1);

        loreSaw = new boolean[_PUZZLES.lore.length];
        wateredFlowers = 1;
        flowers = new int[3];
        canTook = false;

        firstSignsTurn = new boolean[_PUZZLES.firstSignsLength];
        firstElectricity = false;
        firstPipesAngle = new int[_PUZZLES.firstPipesSequence.length];
        firstLamps = false;
        firstClosetCleaned = false;
        firstClosetTookCard = false;
        firstTableTookAnti = false;
        firstTableMedicineDone = false;
        firstLevelComplete = false;

        secondSibasDone = false;
        secondsPassed = new boolean[3];

        thirdCupsDone = false;
        thirdLampsState = new boolean[_PUZZLES.thirdAdjacentLength];
        thirdAdjacentDone = false;
        thirdTeethDone = false;
        thirdMazeState = new boolean[_PUZZLES.thirdMazeCorrectState.length];
        thirdMazeDone = false;
        thirdEnding = -1;

        FileOutputStream outputStream = null;
        try {
            outputStream = thisContext.openFileOutput(saveFile, Context.MODE_PRIVATE);

            String playerInfo = player.getName() + "&1&000" + '\n';
            outputStream.write(playerInfo.getBytes());

            outputStream.close();

        } catch (IOException ignored) {}

        Toast.makeText(getApplicationContext(), "Your progress has been deleted", Toast.LENGTH_LONG).show();
    }

    public static void playAudio(String audio) {
        MediaPlayer music = MediaPlayer.create(thisContext, getResId(audio, R.raw.class));
        music.start();
    }
}