package com.example.far_faraway;

import static com.example.far_faraway.Scene.current_Item;
import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.inventory;
import static com.example.far_faraway.MainActivity.holders1;
import static com.example.far_faraway.MainActivity.objects1;
import static com.example.far_faraway.MainActivity.puzzles1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.far_faraway.Puzzles.FirstLights;
import com.example.far_faraway.Puzzles.FirstPipes;
import com.example.far_faraway.Puzzles.FirstSigns;
import com.example.far_faraway.Puzzles.FirstTable;

public class RoomOne extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

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

                if (obj.setToInventory()) {
                    obj.setVisibility(View.GONE);
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

                if (current_Item != -1) {
                    boolean taken = hold.setItem(inventory[current_Item]);

                    if (taken) {

                        // TODO: Add all holders
                        switch (hold.name) {
                            case "firstCloset":
                                inventory[current_Item] = null;
                                current_Item = -1;

                                holder.used = true;

                                hold.setVisibility(View.GONE);

                                break;

                            case "firstReader":
                                if (MainActivity.firstElectricity) {
                                    inventory[current_Item] = null;
                                    current_Item = -1;

                                    Toast.makeText(getActivity(), "Level 1: COMPLETED!", Toast.LENGTH_LONG).show();
                                }

                                break;

                            case "firstFlower":
                                current_Item = -1;

                                hold.setIcon("flower_2");

                                MainActivity.flowers[0] = true;

                                break;
                        }

                    } else {
                        // TODO: Wrong item. Hint?
                        Log.d("HOLDER", "Item was not placed");
                    }

                } else {
                    // TODO: No item. Hint?
                    Log.d("HOLDER", "Choose an item");
                }

                break;
            }
        }

        // <<< Puzzles
        for (PuzzleInfo puzzle : puzzles1) {
            int resID = getResId(puzzle.name, R.id.class);

            if (resID == v.getId()) {
                Puzzle puzz = (Puzzle) view.findViewById(resID);

                // TODO: Add new puzzles
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

        for (ObjectInfo object : objects1) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                if (!object.used) {
                    obj.setParam(object.name, object.icon);
                    obj.setOnClickListener(this);
                } else
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

                if (holder.name.trim().equals("firstFlower") && MainActivity.flowers[0])
                    hold.setIcon("flower_2");

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

        return view;
    }

}