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
import android.widget.ImageView;

import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomOne;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FirstPipes extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;
    ImageButton pipeImage;
    Object back;

    int pipeCount = _PUZZLES.firstPipesSequence.length;

    int[] pipeAngle = new int[pipeCount];
    int[] pipeAngleCorrect = _PUZZLES.firstPipesSequence;

    int[] pipesTypes = {0, 1, 0, 2, 1, 1,
                        0, 2, 0, 2, 0, 2,
                        1, 1, 0, 1, 0, 2,
                        2, 1, 2, 0, 1, 0 };

    public FirstPipes() {
    }

    public static FirstPipes newInstance(String param1, String param2) {
        FirstPipes fragment = new FirstPipes();
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

        Arrays.fill(pipeAngle, 0);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.firstPipeBack || v.getId() == R.id.firstPipeBGCompleted) {
            MainActivity.firstPipesAngle = pipeAngle;
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne()).addToBackStack(null).commit();
        }

        for (int index = 1; index <= pipeCount; index++) {
            int resID = getResId("firstPipe_" + index, R.id.class);

            if (resID == v.getId()) {
                Object pipe = (Object) view.findViewById(resID);

                pipe.setRotation((pipeAngle[index - 1] + 1) * 90);
                pipeAngle[index - 1] = (pipeAngle[index - 1] + 1) % 4;

                break;
            }
        }

        if (checkPipes()) {
            pipeImage.setVisibility(View.VISIBLE);

            setPuzzleUsed("FirstPipes", 1);

            MainActivity.firstElectricity = true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first_pipes, container, false);

        back = (Object) view.findViewById(R.id.firstPipeBack);
        back.setOnClickListener(this);

        pipeImage = (ImageButton) view.findViewById(R.id.firstPipeBGCompleted);
        pipeImage.setZ(10);
        pipeImage.setOnClickListener(this);
        pipeImage.setVisibility(View.GONE);

        pipeAngle = MainActivity.firstPipesAngle;

        boolean flag = true;
        for (int a : pipeAngle)
            if (a != 0) {
                flag = false;
                break;
            }

        if (flag)
            generatePipes();

        for (int index = 1; index <= pipeCount; index++) {
            Object pipe = (Object) view.findViewById(getResId("firstPipe_" + index, R.id.class));

            if (pipesTypes[index - 1] == 0)
                pipe.setParam("pipe_" + index, "pipe_straight");
            else if (pipesTypes[index - 1] == 1)
                pipe.setParam("pipe_" + index, "pipe_corner_right");
            else
                pipe.setParam("pipe_" + index, "pipe_corner_left");

            setPosition(pipe);
            pipe.setRotation((pipeAngle[index - 1]) * 90);
            pipe.setOnClickListener(this);
        }

        return view;
    }

    private void generatePipes() {
        Random random = new Random();
        for (int index = 0; index < pipeCount; index++)
            pipeAngle[index] = random.nextInt(3);

    }

    private boolean checkPipes() {
        for (int x = 0; x < pipeAngle.length; x++) {
            if (pipeAngle[x] != pipeAngleCorrect[x] && pipeAngleCorrect[x] != -1)
                return false;
        }

        return true;
    }

    private void setPosition(Object obj) {

        Point size = new Point();
        MainActivity.display.getSize(size);

        double width = size.x * 0.198;
        double height = size.y * 0.13;

        obj.setX( (int) width );
        obj.setY( (int) height );
    }
}