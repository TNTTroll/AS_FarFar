package com.example.far_faraway;

import static com.example.far_faraway.MainActivity.holders3;
import static com.example.far_faraway.MainActivity.objects3;
import static com.example.far_faraway.MainActivity.puzzles3;
import static com.example.far_faraway.Scene.current_Item;
import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.inventory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.far_faraway.Puzzles.ThirdAdjacent;
import com.example.far_faraway.Puzzles.ThirdCups;
import com.example.far_faraway.Puzzles.ThirdDoors;
import com.example.far_faraway.Puzzles.ThirdMaze;
import com.example.far_faraway.Puzzles.ThirdTeeth;
import com.example.far_faraway.Puzzles._PUZZLES;

public class RoomThree extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object[] doors = new Object[3];
    Object pass1, pass2;

    int firstDoor = _PUZZLES.thirdDoorsSequence[0];

    public RoomThree() {
    }

    public static RoomThree newInstance(String param1, String param2) {
        RoomThree fragment = new RoomThree();
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

        // <<< Objects
        for (ObjectInfo object : objects3) {
            int resID = getResId(object.name, R.id.class);

            if (resID == v.getId()) {
                Object obj = (Object) view.findViewById(resID);

                int currentDoor = Integer.parseInt(obj.getName().split("_")[1]);

                if (currentDoor == firstDoor)
                    getParentFragmentManager().beginTransaction().replace(R.id.roomView, new ThirdDoors()).addToBackStack(null).commit();

                break;
            }
        }

        // <<< Puzzles
        for (PuzzleInfo puzzle : puzzles3) {
            int resID = getResId(puzzle.name, R.id.class);

            if (resID == v.getId()) {
                Puzzle puzz = (Puzzle) view.findViewById(resID);

                switch (puzz.scene) {
                    case "ThirdCups":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new ThirdCups()).addToBackStack(null).commit();
                        break;

                    case "ThirdАdjacent":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new ThirdAdjacent()).addToBackStack(null).commit();
                        break;

                    case "ThirdTeeth":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new ThirdTeeth()).addToBackStack(null).commit();
                        break;

                    case "ThirdMaze":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new ThirdMaze()).addToBackStack(null).commit();
                        break;
                }

                break;
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_three, container, false);

        ImageButton bg = (ImageButton) view.findViewById(R.id.thirdBG);
        bg.setEnabled(false);

        MainActivity.setLevel(3);

        Scene.showText(2);

        pass1 = (Object) view.findViewById(R.id.thirdPass_1);
        pass2 = (Object) view.findViewById(R.id.thirdPass_2);

        if (MainActivity.thirdMazeDone)
            pass1.setIcon("none");

        if (MainActivity.thirdTeethDone)
            pass2.setIcon("none");

        int doorCount = 0;
        for (ObjectInfo object : objects3) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam(object.name, object.icon);
                obj.setOnClickListener(this);

                if (object.name.trim().startsWith("thirdDoor_")) {
                    doors[doorCount] = obj;
                    doorCount += 1;
                }

            }
            catch(NullPointerException ignored) {}
        }

        for (HolderInfo holder : holders3) {
            try {
                int resID = getResId(holder.name, R.id.class);
                Holder hold = (Holder) view.findViewById(resID);

                hold.setParam(holder.name, holder.need, holder.icon);

            }
            catch(NullPointerException ignored) {}
        }

        for (PuzzleInfo puzzle : puzzles3) {
            try {
                int resID = getResId(puzzle.name, R.id.class);
                Puzzle puzz = (Puzzle) view.findViewById(resID);

                puzz.setParam(puzzle.name, puzzle.scene, puzzle.icon);

                if (!puzzle.used)
                    puzz.setOnClickListener(this);

            }
            catch(NullPointerException ignored) {}
        }

        if (!MainActivity.thirdMazeDone || !MainActivity.thirdTeethDone)
            for (Object door : doors)
                door.setVisibility(View.GONE);

        return view;
    }
}