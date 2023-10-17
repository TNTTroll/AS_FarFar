package com.example.far_faraway.Puzzles;

import static com.example.far_faraway.MainActivity.objects3;
import static com.example.far_faraway.Scene.getResId;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.ObjectInfo;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomFinal;
import com.example.far_faraway.RoomThree;
import com.example.far_faraway.RoomTwo;

import java.util.ArrayList;

public class ThirdDoors extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object[] doors = new Object[3];

    int clicked = 0;

    int[] needDoors = _PUZZLES.thirdDoorsSequence;

    int[] iconDoors = {1, 2, 3,
                       2, 3, 1,
                       1, 3, 2,
                       2, 1, 3,
                       1, 2, 3};

    public ThirdDoors() {
    }

    public static ThirdDoors newInstance(String param1, String param2) {
        ThirdDoors fragment = new ThirdDoors();
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

        int usedDoor = 0;
        for (int index = 1; index <= doors.length; index++) {
            Object obj = (Object) view.findViewById(getResId("thirdCorridor_" + index, R.id.class));

            if (v.getId() == obj.getId()) {
                usedDoor = Integer.parseInt("" + obj.getName().charAt(obj.getName().length() - 1));

                clicked += 1;
            }
        }

        for (int index = 1; index <= doors.length; index++) {
            Object obj = (Object) view.findViewById(getResId("thirdCorridor_" + index, R.id.class));

            obj.setIcon("door_" + iconDoors[3*clicked + index - 1]);
        }

        if (checkDoors(usedDoor)) {
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree()).addToBackStack(null).commit();
        }

        if (clicked == needDoors.length - 1)
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomFinal()).addToBackStack(null).commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_third_doors, container, false);

        int doorCount = 0;
        for (ObjectInfo object : objects3) {
            try {
                int resID = getResId(object.getName(), R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.getName(), "door_" + iconDoors[doorCount]);
                obj.setOnClickListener(this);

                if (object.getName().trim().startsWith("thirdCorridor_")) {
                    doors[doorCount] = obj;
                    doorCount += 1;
                }

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }

    private boolean checkDoors(int usedDoor) {
        return usedDoor != needDoors[clicked];
    }
}