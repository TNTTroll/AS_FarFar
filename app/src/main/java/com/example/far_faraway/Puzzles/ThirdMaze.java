package com.example.far_faraway.Puzzles;

import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.setPuzzleUsed;

import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomOne;
import com.example.far_faraway.RoomThree;

public class ThirdMaze extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object back;

    boolean[] platesCorrect = _PUZZLES.thirdMazeCorrectState;
    int platesCount = platesCorrect.length;

    boolean[] platesState = new boolean[platesCount];

    public ThirdMaze() {
    }

    public static ThirdMaze newInstance(String param1, String param2) {
        ThirdMaze fragment = new ThirdMaze();
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
        if (v.getId() == R.id.thirdMazeBack) {
            MainActivity.thirdMazeState = platesState;
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree()).addToBackStack(null).commit();
        }

        for (int index = 1; index <= platesCount; index++) {
            int resID = getResId("thirdMaze_" + index, R.id.class);

            if (resID == v.getId()) {
                Object plate = (Object) view.findViewById(resID);

                if (platesState[index - 1])
                    plate.setIcon("plate_1");
                else
                    plate.setIcon("plate_2");

                platesState[index - 1] = !platesState[index - 1];

                break;
            }
        }

        if (checkMaze()) {
            MainActivity.thirdMazeDone = true;

            for (int index = 1; index <= platesCount; index++) {
                Object plate = (Object) view.findViewById(getResId("thirdMaze_" + index, R.id.class));
                plate.setEnabled(false);
            }

            setPuzzleUsed("ThirdMaze", 3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_third_maze, container, false);

        back = (Object) view.findViewById(R.id.thirdMazeBack);
        back.setOnClickListener(this);

        platesState = MainActivity.thirdMazeState;

        for (int index = 1; index <= platesCount; index++) {
            Object plate = (Object) view.findViewById(getResId("thirdMaze_" + index, R.id.class));

            if (!platesState[index - 1])
                plate.setParam("thirdMaze_" + index, "plate_1");
            else
                plate.setParam("thirdMaze_" + index, "plate_2");

            plate.setOnClickListener(this);

            setPosition(plate);
        }

        return view;
    }

    private void setPosition(Object obj) {

        Point size = new Point();
        MainActivity.display.getSize(size);

        double width = size.x * 0.333;
        double height = size.y * 0.18;

        obj.setX( (int) width );
        obj.setY( (int) height );
    }

    private boolean checkMaze() {
        for (int index = 0; index < platesCount; index++)
            if (platesState[index] != platesCorrect[index])
                return false;

        return true;
    }
}