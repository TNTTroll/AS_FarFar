package com.example.far_faraway.Puzzles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomTwo;

public class SecondWires extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object back, btn1, btn2, btn3, image;

    public SecondWires() {
    }

    public static SecondWires newInstance(String param1, String param2) {
        SecondWires fragment = new SecondWires();
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
            case (R.id.secondWiresBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo()).addToBackStack(null).commit();
                break;

            case (R.id.secondWiresBtn1):
                image.setIcon("wires_1");
                break;

            case (R.id.secondWiresBtn2):
                image.setIcon("wires_2");
                break;

            case (R.id.secondWiresBtn3):
                image.setIcon("wires_3");
                break;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_second_wires, container, false);

        back = (Object) view.findViewById(R.id.secondWiresBack);
        back.setOnClickListener(this);

        btn1 = (Object) view.findViewById(R.id.secondWiresBtn1);
        btn1.setOnClickListener(this);
        btn1.setIcon("none");

        btn2 = (Object) view.findViewById(R.id.secondWiresBtn2);
        btn2.setOnClickListener(this);
        btn2.setIcon("none");

        btn3 = (Object) view.findViewById(R.id.secondWiresBtn3);
        btn3.setOnClickListener(this);
        btn3.setIcon("none");

        image = (Object) view.findViewById(R.id.secondWiresImage);
        image.setEnabled(false);

        return view;
    }
}