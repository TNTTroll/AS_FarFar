package com.example.far_faraway.Puzzles;

import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.setPuzzleUsed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomTwo;

import java.util.ArrayList;
import java.util.Random;

public class SecondSibas extends Fragment implements  View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object back;

    Object[] plates = new Object[4];

    int progress = 1;
    int sequenceLength = _PUZZLES.secondSibasLength;

    ArrayList<Integer> needPlates = new ArrayList<>();
    ArrayList<Integer> usedPlates = new ArrayList<>();

    public SecondSibas() {
    }

    public static SecondSibas newInstance(String param1, String param2) {
        SecondSibas fragment = new SecondSibas();
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
        if (v.getId() == R.id.secondSibasBack)
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo()).addToBackStack(null).commit();

        int touched = 0;
        for (Object obj : plates)
            if (v.getId() == obj.getId()) {
                int currentPlate = Integer.parseInt("" + obj.getName().charAt(obj.getName().length() - 1)) - 1;

                usedPlates.add(currentPlate);

                touched = currentPlate;

                break;
            }

        boolean flag = true;
        for (int index = 0; index < usedPlates.size(); index++)
            if (usedPlates.get(index) != needPlates.get(index))
                flag = false;

        if (flag) {
            flashMe(plates[touched], 100);

            if (usedPlates.size() == needPlates.size()) {
                progress += 1;

                usedPlates.clear();

                if (needPlates.size() == sequenceLength) {
                    MainActivity.secondsPassed[1] = true;
                    MainActivity.secondSibasDone = true;

                    handler.postDelayed(() -> {
                        for (Object obj : plates) {
                            obj.setEnabled(false);
                            obj.setIcon("sibas_color_" + ("" + obj.getName().charAt(obj.getName().length() - 1)));
                        }
                    }, 150);

                    setPuzzleUsed("SecondSibas", 2);

                } else
                    generateNewPlate();

                if (!MainActivity.secondsPassed[1])
                    handler.postDelayed(this::showPlates, 500);
            }

        } else {
            progress = 1;

            usedPlates.clear();

            needPlates.clear();
            generateNewPlate();

            for (Object obj : plates)
                obj.setIcon("sibas_color_3");

            handler.postDelayed(() -> {
                for (Object obj : plates)
                    obj.setIcon("sibas_color_0");
            }, 500);

            handler.postDelayed(this::showPlates, 1000);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_second_sibas, container, false);

        back = (Object) view.findViewById(R.id.secondSibasBack);
        back.setOnClickListener(this);

        for (int index = 1; index <= 4; index++) {
            try {
                int resID = getResId("secondSibasBtn_" + index, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam("secondSibasBtn_" + index, "sibas_color_0" );
                obj.setOnClickListener(this);

                plates[index - 1] = obj;
            }
            catch(NullPointerException ignored) {}
        }

        generateNewPlate();

        showPlates();

        return view;
    }

    Handler handler = new Handler();
    private void flashMe(Object obj, int wait) {

        obj.setIcon( "sibas_color_" + ("" + obj.getName().charAt(obj.getName().length() - 1)));

        handler.postDelayed(() -> obj.setIcon( "sibas_color_0" ), wait);
    }

    private void showPlates() {
        for (Object obj : plates)
            obj.setEnabled(false);

        for (int index = 0; index < progress; index++) {
            Object obj = plates[ needPlates.get(index) ];

            handler.postDelayed(() -> obj.setIcon( "sibas_color_" + ("" + obj.getName().charAt(obj.getName().length() - 1)) ), (index * 2L + 1) * 1000);
            handler.postDelayed(() -> obj.setIcon( "sibas_color_0" ), (index * 2L + 2) * 1000);
        }

        handler.postDelayed(() -> {
            for (Object obj : plates)
                obj.setEnabled(true);
        }, (long) progress * 1000 * 2);

    }

    private void generateNewPlate() {
        needPlates.add( new Random().nextInt(4) );
    }
}