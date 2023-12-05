package com.example.far_faraway.Puzzles;

import static com.example.far_faraway.Scene.getResId;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomTwo;

import java.util.ArrayList;

public class SecondDram extends Fragment implements  View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object back, image;

    int[] needDrams = _PUZZLES.secondDramsSequence;
    int clicked = 0;

    Object[] signs = new Object[8];
    ArrayList<Integer> usedDrams = new ArrayList<>();

    public SecondDram() {
    }

    public static SecondDram newInstance(String param1, String param2) {
        SecondDram fragment = new SecondDram();
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
        if (v.getId() == R.id.secondDramBack)
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo()).addToBackStack(null).commit();

        for (Object obj : signs)
            if (v.getId() == obj.getId()) {
                int currentSign = Integer.parseInt("" + obj.getName().charAt(obj.getName().length() - 1)) - 1;

                usedDrams.add(currentSign);

                break;
            }

        if (checkDrams()) {
            MainActivity.secondsPassed[2] = true;

            for (Object obj : signs)
                obj.setVisibility(View.GONE);

            image.setIcon("dram_open");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_second_dram, container, false);

        back = (Object) view.findViewById(R.id.secondDramBack);
        back.setOnClickListener(this);

        image = (Object) view.findViewById(R.id.secondDramImage);
        image.setEnabled(false);

        for (int index = 1; index <= 8; index++) {
            try {
                int resID = getResId("secondDramSign_" + index, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam("secondDramSign_" + index, "none");
                obj.setOnClickListener(this);

                signs[index - 1] = obj;
            }
            catch(NullPointerException ignored) {}
        }

        if (!MainActivity.secondsPassed[2])
            image.setIcon("dram_close");
        else {
            for (Object obj : signs)
                obj.setVisibility(View.GONE);
        }

        return view;
    }

    private boolean checkDrams() {
        clicked += 1;

        if (clicked < needDrams.length)
            return false;

        for (int x = 0; x < needDrams.length; x++)
            if (needDrams[x] != usedDrams.get(clicked - needDrams.length + x))
                return false;

        return true;
    }
}