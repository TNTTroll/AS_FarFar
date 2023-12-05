package com.example.far_faraway;

import static com.example.far_faraway.Scene.current_Item;
import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.inventory;
import static com.example.far_faraway.MainActivity.holders1;
import static com.example.far_faraway.MainActivity.objects1;
import static com.example.far_faraway.MainActivity.puzzles1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.far_faraway.Puzzles.FirstCloset;
import com.example.far_faraway.Puzzles.FirstLights;
import com.example.far_faraway.Puzzles.FirstPipes;
import com.example.far_faraway.Puzzles.FirstSigns;
import com.example.far_faraway.Puzzles.FirstTable;
import com.example.far_faraway.Puzzles._PUZZLES;

public class RoomOne extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object door;

    public RoomOne() {
    }

    public static RoomOne newInstance(String param1, String param2) {
        RoomOne fragment = new RoomOne();
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
        for (ObjectInfo object : objects1) {
            int resID = getResId(object.name, R.id.class);

            if (resID == v.getId()) {
                Object obj = (Object) view.findViewById(resID);

                if (obj.name.trim().equals("firstDoor")) {
                    new Acception().show(getParentFragmentManager(), "acceptation");
                    break;
                }

                if (obj.setToInventory()) {
                    obj.setVisibility(View.GONE);

                    if (obj.name.trim().equals("watering"))
                        MainActivity.canTook = true;
                    else
                        object.used = true;
                }

                break;
            }
        }

        // << Holders
        for (HolderInfo holder : holders1) {
            int resID = getResId(holder.name, R.id.class);

            if (resID == v.getId()) {
                Holder hold = (Holder) view.findViewById(resID);

                if (current_Item != -1 && inventory[current_Item] != null) {
                    boolean taken = hold.setItem(inventory[current_Item]);

                    if (taken) {

                        switch (hold.name) {
                            case "firstCloset":
                                inventory[current_Item] = null;
                                current_Item = -1;

                                holder.used = true;

                                hold.setVisibility(View.GONE);

                                break;

                            case "firstReader":
                                if (MainActivity.firstLamps) {
                                    inventory[current_Item] = null;
                                    current_Item = -1;

                                    MainActivity.firstLevelComplete = true;

                                    door.setVisibility(View.VISIBLE);
                                }

                                break;

                            case "firstFlower":
                                current_Item = -1;

                                MainActivity.flowers[0] = 1;
                                MainActivity.wateredFlowers += 1;

                                hold.setIcon("flower_" + MainActivity.wateredFlowers);

                                break;

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
        for (PuzzleInfo puzzle : puzzles1) {
            int resID = getResId(puzzle.name, R.id.class);

            if (resID == v.getId()) {
                Puzzle puzz = (Puzzle) view.findViewById(resID);

                switch (puzz.scene) {
                    case "FirstPipes":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new FirstPipes()).addToBackStack(null).commit();
                        break;

                    case "FirstLights":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new FirstLights()).addToBackStack(null).commit();
                        break;

                    case "FirstTable":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new FirstTable()).addToBackStack(null).commit();
                        break;

                    case "FirstSigns":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new FirstSigns()).addToBackStack(null).commit();
                        break;

                    case "FirstCloset":
                        getParentFragmentManager().beginTransaction().replace(R.id.roomView, new FirstCloset()).addToBackStack(null).commit();
                        break;
                }

                break;
            }
        }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_one, container, false);

        MainActivity.setLevel(1);

        Scene.showText(0);

        for (ObjectInfo object : objects1) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                if (!object.used) {
                    obj.setParam(object.name, object.icon);
                    obj.setOnClickListener(this);
                } else
                    obj.setVisibility(View.GONE);

                if (object.name.trim().equals("firstDoor")) {
                    door = obj;
                    door.setIcon("door_open");
                }

                else if (object.name.trim().equals("watering") && MainActivity.canTook)
                    obj.setVisibility(View.GONE);

            }
            catch(NullPointerException ignored) {}
        }

        for (HolderInfo holder : holders1) {
            try {
                int resID = getResId(holder.name, R.id.class);
                Holder hold = (Holder) view.findViewById(resID);

                hold.setParam(holder.name, holder.need, holder.icon);

                if (holder.used)
                    hold.setVisibility(View.GONE);

                if (holder.name.trim().equals("firstFlower"))
                    hold.setIcon("flower_" + MainActivity.wateredFlowers);

                hold.setOnClickListener(this);
            }
            catch(NullPointerException ignored) {}
        }

        for (PuzzleInfo puzzle : puzzles1) {
            try {
                int resID = getResId(puzzle.name, R.id.class);
                Puzzle puzz = (Puzzle) view.findViewById(resID);

                puzz.setParam(puzzle.name, puzzle.scene, puzzle.icon);

                if (!puzzle.used)
                    puzz.setOnClickListener(this);

            }
            catch(NullPointerException ignored) {}
        }

        if (!MainActivity.firstLevelComplete)
           door.setVisibility(View.GONE);

        return view;
    }
}