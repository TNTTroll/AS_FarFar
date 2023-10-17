package com.example.far_faraway.Puzzles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomTwo;

public class SecondNotes extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object back, image;

    public SecondNotes() {
    }

    public static SecondNotes newInstance(String param1, String param2) {
        SecondNotes fragment = new SecondNotes();
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
        if (v.getId() == R.id.secondNotesBack)
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo()).addToBackStack(null).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_second_notes, container, false);

        back = (Object) view.findViewById(R.id.secondNotesBack);
        back.setOnClickListener(this);

        image = (Object) view.findViewById(R.id.secondNotesImage);
        image.setEnabled(false);

        if (MainActivity.secondSibasDone)
            image.setIcon("notes_2");

        return view;
    }
}