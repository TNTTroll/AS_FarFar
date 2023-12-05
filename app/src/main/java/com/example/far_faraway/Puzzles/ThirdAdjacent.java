package com.example.far_faraway.Puzzles;

import static com.example.far_faraway.Scene.getResId;

import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomThree;

public class ThirdAdjacent extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object back, bg;

    int lampsCount = _PUZZLES.thirdAdjacentLength;
    boolean[] lampsState = new boolean[lampsCount];

    public ThirdAdjacent() {
    }

    public static ThirdAdjacent newInstance(String param1, String param2) {
        ThirdAdjacent fragment = new ThirdAdjacent();
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
        if (v.getId() == R.id.thirdAdjacentBack) {
            MainActivity.thirdLampsState = lampsState;
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree()).addToBackStack(null).commit();
        }

        for (int index = 1; index <= lampsCount; index++) {
            int resID = getResId("thirdAdjacent_" + index, R.id.class);

            if (resID == v.getId()) {
                changeLamps(index - 1);

                break;
            }
        }

        if (checkLamps()) {
            MainActivity.thirdAdjacentDone = true;

            for (int index = 1; index <= lampsCount; index++) {
                Object lamp = (Object) view.findViewById(getResId("thirdAdjacent_" + index, R.id.class));
                lamp.setEnabled(false);
                lamp.setIcon("none");
            }

            bg.setIcon("bg_adjacent_2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_third_adjacent, container, false);

        back = (Object) view.findViewById(R.id.thirdAdjacentBack);
        back.setOnClickListener(this);

        bg = (Object) view.findViewById(R.id.thirdAdjacentBG);
        bg.setEnabled(false);

        if (MainActivity.thirdAdjacentDone)
            bg.setIcon("bg_adjacent_2");

        lampsState = MainActivity.thirdLampsState;

        for (int index = 1; index <= lampsCount; index++) {
            Object lamp = (Object) view.findViewById(getResId("thirdAdjacent_" + index, R.id.class));

            if (!lampsState[index - 1])
                lamp.setParam("thirdAdjacent_" + index, "none");
            else
                lamp.setParam("thirdAdjacent_" + index, "button_universal");

            lamp.setOnClickListener(this);

            if (MainActivity.thirdAdjacentDone) {
                lamp.setEnabled(false);
                lamp.setIcon("none");
            }

            setPosition(lamp);
        }

        return view;
    }

    private boolean checkLamps() {
        for (boolean state : lampsState)
            if (!state)
                return false;

        return true;
    }

    private void setPosition(Object obj) {

        Point size = new Point();
        MainActivity.display.getSize(size);

        double width = size.x * 0.21;
        double height = size.y * 0.05;

        obj.setX( (int) width );
        obj.setY( (int) height );
    }

    private void changeLamps(int index) {
        lampsState[index] = !lampsState[index];

        try {
            if (index % 4 != 3)
                lampsState[index + 1] = !lampsState[index + 1];
        } catch (Exception ignored) {}

        try {
            if (index % 4 != 0)
                lampsState[index - 1] = !lampsState[index - 1];
        } catch (Exception ignored) {}

        try {
            lampsState[index + 4] = !lampsState[index + 4];
        } catch (Exception ignored) {}

        try {
            lampsState[index - 4] = !lampsState[index - 4];
        } catch (Exception ignored) {}


        for (int x = 1; x <= lampsCount; x++) {
            Object lamp = (Object) view.findViewById(getResId("thirdAdjacent_" + x, R.id.class));

            if (!lampsState[x - 1])
                lamp.setIcon("none");
            else
                lamp.setIcon("button_universal");
        }

    }
}