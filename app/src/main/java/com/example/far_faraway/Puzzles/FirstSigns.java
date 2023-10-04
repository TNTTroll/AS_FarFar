package com.example.far_faraway.Puzzles;

import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.setPuzzleUsed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomOne;

public class FirstSigns extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object back;

    int signsCount = _PUZZLES.firstSignsSequence.length;

    boolean[] signsTurn = new boolean[signsCount];
    boolean[] signsTurnCorrect = _PUZZLES.firstSignsSequence;

    public FirstSigns() {
    }

    public static FirstSigns newInstance(String param1, String param2) {
        FirstSigns fragment = new FirstSigns();
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
        if (v.getId() == R.id.firstSignsBack) {
            MainActivity.firstSignsTurn = signsTurn;
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne()).addToBackStack(null).commit();
        }

        for (int index = 1; index <= signsCount; index++) {
            int resID = getResId("firstSigns_" + index, R.id.class);

            if (resID == v.getId()) {
                Object sign = (Object) view.findViewById(resID);

                if (signsTurn[index - 1])
                    sign.setIcon("sign_1");
                else
                    sign.setIcon("sign_2");

                signsTurn[index - 1] = !signsTurn[index - 1];

                break;
            }
        }

        if (checkSigns()) {

            // TODO: Win conclusion
            //setPuzzleUsed("FirstSigns", 1);

            Log.d("SIGNS", "DONE");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first_signs, container, false);

        back = (Object) view.findViewById(R.id.firstSignsBack);
        back.setOnClickListener(this);

        signsTurn = MainActivity.firstSignsTurn;

        for (int index = 1; index <= signsCount; index++) {
            Object sign = (Object) view.findViewById(getResId("firstSigns_" + index, R.id.class));

            if (!signsTurn[index - 1])
                sign.setParam("sign_" + index, "sign_1");
            else
                sign.setParam("sign_" + index, "sign_2");

            sign.setOnClickListener(this);
        }

        return view;
    }

    private boolean checkSigns() {
        for (int x = 0; x < signsTurn.length; x++) {
            if (signsTurn[x] != signsTurnCorrect[x])
                return false;
        }

        return true;
    }
}