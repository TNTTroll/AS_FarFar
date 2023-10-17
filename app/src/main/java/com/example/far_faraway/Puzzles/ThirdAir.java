package com.example.far_faraway.Puzzles;

import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.setPuzzleUsed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomFinal;

public class ThirdAir extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object back, check;

    Object[] airs = new Object[3];
    Object[] flasks = new Object[3];

    String[] endingBad = _PUZZLES.thirdEndingBad;
    String[] endingNeutral = _PUZZLES.thirdEndingNeutral;
    String[] endingGood = _PUZZLES.thirdEndingGood;

    public ThirdAir() {
    }

    public static ThirdAir newInstance(String param1, String param2) {
        ThirdAir fragment = new ThirdAir();
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
        if (v.getId() == R.id.thirdAirBack)
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomFinal()).addToBackStack(null).commit();

        else if (v.getId() == R.id.thirdAirCheck) {
            if (checkAirs(endingBad)) {
                MainActivity.thirdEnding = 1;
                stopClicking();
            }

            else if (checkAirs(endingNeutral)) {
                MainActivity.thirdEnding = 2;
                stopClicking();
            }

            else if (checkAirs(endingGood)) {
                MainActivity.thirdEnding = 3;
                stopClicking();
            }
        }

        for (Object air : airs) {
            int resID = getResId(air.getName(), R.id.class);

            if (resID == v.getId()) {
                Object obj = (Object) view.findViewById(resID);

                int currentAir = Integer.parseInt(obj.getName().split("_")[1]);
                int currentIcon = Integer.parseInt(obj.getIcon().split("_")[1]);

                if (currentIcon % 3 == 0) {
                    obj.setIcon("plate_" + (currentIcon - 2));
                    flasks[currentAir - 1].setIcon("table_flask_" + (currentIcon - 2));
                } else {
                    obj.setIcon("plate_" + (currentIcon + 1));
                    flasks[currentAir - 1].setIcon("table_flask_" + (currentIcon + 1));
                }

                break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_third_air, container, false);

        back = (Object) view.findViewById(R.id.thirdAirBack);
        back.setOnClickListener(this);

        check = (Object) view.findViewById(R.id.thirdAirCheck);
        check.setOnClickListener(this);

        int airCount = 0;
        for (int index = 1; index <= 3; index++) {
            try {
                int resID = getResId("thirdAir_" + index, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam("thirdAir_" + index, "plate_1");
                obj.setOnClickListener(this);

                airs[airCount] = obj;

                airCount += 1;

            }
            catch(NullPointerException ignored) {}
        }

        airCount = 0;
        for (int index = 1; index <= 3; index++) {
            try {
                int resID = getResId("thirdAirFlask_" + index, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam("thirdAirFlask_" + index, "table_flask_1");
                obj.setEnabled(false);

                flasks[airCount] = obj;

                airCount += 1;

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }

    private boolean checkAirs(String[] array) {
        for (int index = 0; index < array.length; index++) {

            if ( !airs[index].getIcon().trim().equals( array[index] ))
                return false;
        }

        return true;
    }

    private void stopClicking() {
        for (Object obj : airs)
            obj.setEnabled(false);

        check.setEnabled(false);

        setPuzzleUsed("ThirdAir", 3);
    }
}