package com.example.far_faraway;

import static com.example.far_faraway.Scene.getResId;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.far_faraway.Puzzles._PUZZLES;

public class Achievements extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    public Achievements() {
    }

    public static Achievements newInstance(String param1, String param2) {
        Achievements fragment = new Achievements();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_achievements, container, false);

        for (String achievement : _PUZZLES.achievements) {
            Object obj = (Object) view.findViewById(getResId("ach_" + achievement, R.id.class));

            obj.setParam(achievement, "ach_" + achievement);

            if (!MainActivity.getAchievement(obj.getName()))
                obj.setIcon("pnc");

        }

        return view;
    }
}