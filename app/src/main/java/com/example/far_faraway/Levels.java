package com.example.far_faraway;

import static com.example.far_faraway.MainActivity.setLevel;
import static com.example.far_faraway.Scene.getResId;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.far_faraway.Object;
import androidx.fragment.app.DialogFragment;

public class Levels extends DialogFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    public Levels() {
    }

    public static Levels newInstance(String param1, String param2) {
        Levels fragment = new Levels();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.lvl_1):
                setLevel(1);
                break;

            case (R.id.lvl_2):
                setLevel(2);
                break;

            case (R.id.lvl_3):
                setLevel(3);
                break;
        }

        dismiss();

        Intent scene = new Intent(MainActivity.thisContext, Scene.class);
        startActivity(scene);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_levels, container, false);

        ImageButton bg = (ImageButton) view.findViewById(R.id.lvlBG);
        bg.setEnabled(false);

        for (int index = 1; index <= 3; index++) {
            int resID = getResId("lvl_" + index, R.id.class);
            Object obj = (Object) view.findViewById(resID);

            obj.setOnClickListener(this);
        }

        return view;
    }
}