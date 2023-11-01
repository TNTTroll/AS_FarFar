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
import com.example.far_faraway.RoomOne;

public class FirstSigns extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object back;
    ImageButton locker;
    String[] signsImage = {"candle", "flower", "alcohol", "shirt",
                           "apple", "fish", "knife", "teapot",
                           "cigar", "cup", "juice", "ring",
                           "wood", "hat", "eye", "letter" };

    int signsCount = _PUZZLES.firstSignsLength;

    boolean[] signsTurn = new boolean[signsCount];

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

        } else if (v.getId() == R.id.firstSignsBGClose) {
            locker.setVisibility(View.GONE);

            for (int index = 1; index <= signsCount; index++) {
                Object sign = (Object) view.findViewById(getResId("firstSigns_" + index, R.id.class));

                sign.setVisibility(View.VISIBLE);

                setPosition(sign);
            }

        }

        if (MainActivity.firstElectricity)
            for (int index = 1; index <= signsCount; index++) {
                int resID = getResId("firstSigns_" + index, R.id.class);

                if (resID == v.getId()) {
                    Object sign = (Object) view.findViewById(resID);

                    if (signsTurn[index - 1])
                        sign.setIcon("symbol_" + signsImage[index - 1] + "_1");
                    else
                        sign.setIcon("symbol_" + signsImage[index - 1] + "_2");

                    signsTurn[index - 1] = !signsTurn[index - 1];

                    break;
                }
            }

        if (!MainActivity.getAchievement(4)) {
            boolean flag = true;

            boolean[] edible = {false, false, false, false,
                                true, true, false, false,
                                false, false, true, false,
                                false, false, false, false };

            for (int index = 0; index < signsCount; index++)
                if (signsTurn[index] != edible[index]) {
                    flag = false;
                    break;
                }

            if (flag)
                MainActivity.setAchievement(4);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first_signs, container, false);

        back = (Object) view.findViewById(R.id.firstSignsBack);
        back.setOnClickListener(this);

        locker = (ImageButton) view.findViewById(R.id.firstSignsBGClose);
        locker.setOnClickListener(this);

        signsTurn = MainActivity.firstSignsTurn;

        for (int index = 1; index <= signsCount; index++) {
            Object sign = (Object) view.findViewById(getResId("firstSigns_" + index, R.id.class));

            if (!signsTurn[index - 1])
                sign.setParam("firstSigns_" + index, "symbol_" + signsImage[index - 1] + "_1");
            else
                sign.setParam("firstSigns_" + index, "symbol_" + signsImage[index - 1] + "_2");

            sign.setVisibility(View.GONE);
            sign.setOnClickListener(this);
        }

        return view;
    }

    private void setPosition(Object obj) {

        Point size = new Point();
        MainActivity.display.getSize(size);

        double width = size.x * 0.433;
        double height = size.y * 0.2;

        obj.setX( (int) width );
        obj.setY( (int) height );
    }
}