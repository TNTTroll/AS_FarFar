package com.example.far_faraway;

import static com.example.far_faraway.MainActivity.holders2;
import static com.example.far_faraway.MainActivity.objects2;
import static com.example.far_faraway.MainActivity.puzzles2;
import static com.example.far_faraway.Scene.current_Item;
import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.inventory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.far_faraway.Puzzles.SecondDram;
import com.example.far_faraway.Puzzles.SecondMushrooms;
import com.example.far_faraway.Puzzles.SecondNotes;
import com.example.far_faraway.Puzzles.SecondSibas;
import com.example.far_faraway.Puzzles.SecondWires;

public class RoomTwo extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object door;

    public RoomTwo() {
    }

    public static RoomTwo newInstance(String param1, String param2) {
        RoomTwo fragment = new RoomTwo();
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

        if (v.getId() == R.id.secondDoor)
            new Acception().show(getParentFragmentManager(), "acceptation");

        // << Holders
        for (HolderInfo holder : holders2) {
            int resID = getResId(holder.name, R.id.class);

            if (resID == v.getId()) {
                Holder hold = (Holder) view.findViewById(resID);

                if (current_Item != -1 && inventory[current_Item] != null) {
                    boolean taken = hold.setItem(inventory[current_Item]);

                    if (taken) {

                        if ("secondFlower".equals(hold.name.trim())) {
                            current_Item = -1;

                            MainActivity.flowers[1] = 1;
                            MainActivity.wateredFlowers += 1;

                            hold.setIcon("flower_" + MainActivity.wateredFlowers);
                        }

                    } else {
                        // Wrong item. Hint
                        Toast.makeText(getActivity(), "Try something else", Toast.LENGTH_LONG).show();
                    }

                } else {
                    // No item. Hint
                    Toast.makeText(getActivity(), "I need to put something here", Toast.LENGTH_LONG).show();
                }

                break;
            }
        }

        // <<< Puzzles
        for (PuzzleInfo puzzle : puzzles2) {
            int resID = getResId(puzzle.name, R.id.class);

            if (resID == v.getId()) {
                Puzzle puzz = (Puzzle) view.findViewById(resID);

                switch (puzz.scene) {
                    case "SecondMushrooms":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new SecondMushrooms()).addToBackStack(null).commit();
                        break;

                    case "SecondSibas":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new SecondSibas()).addToBackStack(null).commit();
                        break;

                    case "SecondWires":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new SecondWires()).addToBackStack(null).commit();
                        break;

                    case "SecondNotes":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new SecondNotes()).addToBackStack(null).commit();
                        break;

                    case "SecondDram":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new SecondDram()).addToBackStack(null).commit();
                        break;
                }

                break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_room_two, container, false);

        MainActivity.setLevel(2);

        Scene.showText(1);

        for (ObjectInfo object : objects2) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                if (!object.used) {
                    obj.setParam(object.name, object.icon);
                    obj.setOnClickListener(this);
                } else
                    obj.setVisibility(View.GONE);

                if (object.name.trim().equals("secondDoor")) {
                    door = obj;
                    door.setIcon("door_open");
                }

                else if (object.name.trim().startsWith("secondPassed_")) {
                    if ( MainActivity.secondsPassed[ Integer.parseInt("" + obj.getName().charAt(obj.getName().length() - 1)) ] )
                        obj.setIcon("pass_1");
                    else
                        obj.setIcon("none");
                }

            }
            catch(NullPointerException ignored) {}
        }

        for (HolderInfo holder : holders2) {
            try {
                int resID = getResId(holder.name, R.id.class);
                Holder hold = (Holder) view.findViewById(resID);

                hold.setParam(holder.name, holder.need, holder.icon);
                hold.setOnClickListener(this);

                if (holder.name.trim().equals("secondFlower"))
                    hold.setIcon("flower_" + MainActivity.wateredFlowers);

            }
            catch(NullPointerException ignored) {}
        }

        for (PuzzleInfo puzzle : puzzles2) {
            try {
                int resID = getResId(puzzle.name, R.id.class);
                Puzzle puzz = (Puzzle) view.findViewById(resID);

                puzz.setParam(puzzle.name, puzzle.scene, puzzle.icon);

                if (!puzzle.used)
                    puzz.setOnClickListener(this);

            }
            catch(NullPointerException ignored) {}
        }

        if (!MainActivity.secondsPassed[0] || !MainActivity.secondsPassed[1] || !MainActivity.secondsPassed[2])
            door.setVisibility(View.GONE);

        return view;
    }
}