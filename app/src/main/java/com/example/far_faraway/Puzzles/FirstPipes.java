package com.example.far_faraway.Puzzles;

import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.setPuzzleUsed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomOne;

import java.util.Arrays;

public class FirstPipes extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;
    ImageView pipeImage;
    Object back;

    int pipeCount = _PUZZLES.firstPipesSequence.length;

    int[] pipeAngle = new int[pipeCount];
    int[] pipeAngleCorrect = _PUZZLES.firstPipesSequence;

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

        if (v.getId() == R.id.firstPipeBack) {
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

            for (int index = 1; index <= pipeCount; index++) {
                Object pipe = (Object) view.findViewById(getResId("firstPipe_" + index, R.id.class));

                pipe.setVisibility(View.GONE);
            }

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

        pipeImage = (ImageView) view.findViewById(R.id.firstPipeComplete);
        pipeImage.setVisibility(View.GONE);

        pipeAngle = MainActivity.firstPipesAngle;

        for (int index = 1; index <= pipeCount; index++) {
            Object pipe = (Object) view.findViewById(getResId("firstPipe_" + index, R.id.class));

            pipe.setParam("pipe_" + index, "pipe");
            pipe.setRotation((pipeAngle[index - 1]) * 90);
            pipe.setOnClickListener(this);
        }

        return view;
    }

    private boolean checkPipes() {
        for (int x = 0; x < pipeAngle.length; x++) {
            if (pipeAngle[x] != pipeAngleCorrect[x])
                return false;
        }

        return true;
    }
}