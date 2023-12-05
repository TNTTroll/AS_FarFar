package com.example.far_faraway;

import static com.example.far_faraway.MainActivity.player;
import static com.example.far_faraway.Scene.indicator;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class Acception extends DialogFragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    public Acception() {
    }

    public static Acception newInstance(String param1, String param2) {
        Acception fragment = new Acception();
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
        if (v.getId() == R.id.acceptBtnYes) {
            Toast.makeText(getActivity(), "Level " + player.getLevel() + ": COMPLETED!", Toast.LENGTH_LONG).show();

            switch (player.getLevel()) {
                case 1:
                    getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo()).commit();
                    indicator.setIcon("bg_indicator_2");
                    break;

                case 2:
                    getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree()).commit();
                    indicator.setIcon("bg_indicator_3");
                    break;
            }

            dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_acception, container, false);

        ImageButton bg = (ImageButton) view.findViewById(R.id.acceptBG);
        bg.setEnabled(false);

        ImageButton yes = (ImageButton) view.findViewById(R.id.acceptBtnYes);
        yes.setOnClickListener(this);

        ImageButton no = (ImageButton) view.findViewById(R.id.acceptBtnNo);
        no.setOnClickListener(v -> dismiss());

        return view;
    }
}