package com.example.far_faraway;

import static com.example.far_faraway.MainActivity.holders3;
import static com.example.far_faraway.MainActivity.objects3;
import static com.example.far_faraway.MainActivity.puzzles3;
import static com.example.far_faraway.MainActivity.setAchievement;
import static com.example.far_faraway.Scene.current_Item;
import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.inventory;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.far_faraway.Puzzles.ThirdAir;
import com.example.far_faraway.Puzzles._PUZZLES;

public class RoomFinal extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object door;

    public RoomFinal() {
    }

    public static RoomFinal newInstance(String param1, String param2) {
        RoomFinal fragment = new RoomFinal();
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

        if (v.getId() == R.id.thirdExit) {
            if (MainActivity.thirdEnding == 1)
                Toast.makeText(getActivity(), "BAD ENDING!", Toast.LENGTH_LONG).show();
            else if (MainActivity.thirdEnding == 2)
                Toast.makeText(getActivity(), "NEUTRAL ENDING!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getActivity(), "GOOD ENDING!", Toast.LENGTH_LONG).show();

            if (!MainActivity.getAchievement(MainActivity.thirdEnding - 1))
                MainActivity.setAchievement(MainActivity.thirdEnding - 1);

            startActivity(new Intent(getActivity(), Ending.class));
        }

        // << Holders
        for (HolderInfo holder : holders3) {
            int resID = getResId(holder.name, R.id.class);

            if (resID == v.getId()) {
                Holder hold = (Holder) view.findViewById(resID);

                if (current_Item != -1 && inventory[current_Item] != null) {
                    boolean taken = hold.setItem(inventory[current_Item]);

                    if (taken) {

                        if ("thirdFlower".equals(hold.name)) {
                            current_Item = -1;

                            MainActivity.flowers[2] = 1;
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
        for (PuzzleInfo puzzle : puzzles3) {
            int resID = getResId(puzzle.name, R.id.class);

            if (resID == v.getId()) {
                Puzzle puzz = (Puzzle) view.findViewById(resID);

                if ("ThirdAir".equals(puzz.scene))
                    getParentFragmentManager().beginTransaction().replace(R.id.roomView, new ThirdAir()).addToBackStack(null).commit();

                break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_room_final, container, false);

        for (ObjectInfo object : objects3) {
            try {
                int resID = getResId(object.name, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                if (!object.used) {
                    obj.setParam(object.name, object.icon);
                    obj.setOnClickListener(this);
                } else
                    obj.setVisibility(View.GONE);

                if (object.name.trim().equals("thirdExit")) {
                    door = obj;

                    if (MainActivity.thirdEnding == -1)
                        door.setEnabled(false);
                }

            }
            catch(NullPointerException ignored) {}
        }

        for (HolderInfo holder : holders3) {
            try {
                int resID = getResId(holder.name, R.id.class);
                Holder hold = (Holder) view.findViewById(resID);

                hold.setParam(holder.name, holder.need, holder.icon);

                if (holder.used)
                    hold.setVisibility(View.GONE);

                if (holder.name.trim().equals("thirdFlower"))
                    hold.setIcon("flower_" + MainActivity.wateredFlowers);

                hold.setOnClickListener(this);
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

        return view;
    }
}