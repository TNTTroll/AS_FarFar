package com.example.far_faraway;

import static com.example.far_faraway.MainActivity.player;
import static com.example.far_faraway.MainActivity.puzzles1;
import static com.example.far_faraway.MainActivity.puzzles2;
import static com.example.far_faraway.MainActivity.puzzles3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.media.ImageWriter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.far_faraway.Puzzles.FirstPipes;

import java.lang.reflect.Field;

public class Scene extends AppCompatActivity implements View.OnClickListener {

    public View roomView;

    @SuppressLint("StaticFieldLeak")
    public static ImageButton[] btn_invs = new ImageButton[4];
    public static int current_Item = -1;

    public static Object[] inventory = new Object[4];

    public static void reloadInventory() {
        for (int x = 0; x < btn_invs.length; x++) {
            btn_invs[x].setImageResource(R.drawable.black);

            if (inventory[x] != null)
                btn_invs[x].setImageDrawable(inventory[x].icon);
        }

        active_item = null;
        selectButton();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);

        roomView = (View) findViewById(R.id.roomView);

        switch (player.getLevel()) {
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne()).commit();
                break;

            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo()).commit();
                break;

            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree()).commit();
                break;
        }


        for (int x = 0; x < btn_invs.length; x++) {
            int resID = getResId("inv_" + (x+1), R.id.class);
            ImageButton btn = (ImageButton) findViewById(resID);
            btn.setBackground(null);
            btn.setOnClickListener(this);

            btn_invs[x] = btn;
        }

    }

    private static ImageButton active_item;
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        for (int x = 0; x < btn_invs.length; x++) {
            ImageButton btn = btn_invs[x];

            if (btn.getId() == v.getId()) {
                if (active_item == btn) {
                    active_item = null;
                    current_Item = -1;
                } else {
                    active_item = btn;
                    current_Item = x;
                }
            }
        }

        selectButton();
    }

    private static void selectButton() {
        for (ImageButton btn : btn_invs)
            btn.setBackground(null);

        if (active_item != null)
            active_item.setBackgroundColor(Color.BLACK);
    }

    // ----- Helpers
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            return -1;
        }
    }

    public static void setPuzzleUsed(String puzzleScene, int room) {
        if (room == 1) {
            for (PuzzleInfo puzzle1 : puzzles1)
                if (puzzle1.scene.trim().equals(puzzleScene))
                    puzzle1.used = true;

        } else if (room == 2) {
            for (PuzzleInfo puzzle2 : puzzles2)
                if (puzzle2.scene.trim().equals(puzzleScene))
                    puzzle2.used = true;
        } else {
            for (PuzzleInfo puzzle3 : puzzles3)
                if (puzzle3.scene.trim().equals(puzzleScene))
                    puzzle3.used = true;
        }

    }
}