package com.example.far_faraway.Puzzles;

import static com.example.far_faraway.Scene.getResId;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomThree;

public class ThirdCups extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    ImageButton image;
    Object back, check;

    String[] needLever = _PUZZLES.thirdCupsSequence;
    Object[] levers = new Object[3];

    public ThirdCups() {
    }

    public static ThirdCups newInstance(String param1, String param2) {
        ThirdCups fragment = new ThirdCups();
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

        switch (v.getId()) {
            case (R.id.thirdCupsBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomThree()).addToBackStack(null).commit();
                break;

            case (R.id.thirdCupsCheck):
                if (!MainActivity.thirdCupsDone)
                    if (checkLevers()) {
                        MainActivity.thirdCupsDone = true;

                        check.setVisibility(View.GONE);

                        for (Object lever : levers)
                            lever.setVisibility(View.GONE);

                        image.setVisibility(View.VISIBLE);
                    }

                break;
        }

        if (!MainActivity.thirdCupsDone)
            for (Object lever : levers) {
                int resID = getResId(lever.getName(), R.id.class);

                if (resID == v.getId()) {
                    Object obj = (Object) view.findViewById(resID);

                    int currentCup = Integer.parseInt(obj.getIcon().split("_")[1]);

                    if (currentCup % 3 == 0)
                        obj.setIcon("lever_" + (currentCup - 2));
                    else
                        obj.setIcon("lever_" + (currentCup + 1));

                    break;
                }
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_third_cups, container, false);

        back = (Object) view.findViewById(R.id.thirdCupsBack);
        back.setOnClickListener(this);

        image = (ImageButton) view.findViewById(R.id.thirdCupsImage);
        image.setEnabled(false);

        if (!MainActivity.thirdCupsDone)
            image.setVisibility(View.GONE);

        check = (Object) view.findViewById(R.id.thirdCupsCheck);
        check.setOnClickListener(this);

        if (MainActivity.thirdCupsDone)
            check.setVisibility(View.GONE);

        int leverCount = 0;
        for (int index = 1; index <= 3; index++) {
            try {
                int resID = getResId("thirdCupsLever_" + index, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam("thirdCupsLever_" + index, "lever_1");
                obj.setOnClickListener(this);

                if (MainActivity.thirdCupsDone)
                    obj.setVisibility(View.GONE);

                levers[leverCount] = obj;

                leverCount += 1;

            }
            catch(NullPointerException ignored) {}
        }

        return view;
    }

    private boolean checkLevers() {
        for (int index = 0; index < needLever.length; index++) {

            if ( !levers[index].getIcon().trim().equals( needLever[index] ))
                return false;
        }

        return true;
    }
}