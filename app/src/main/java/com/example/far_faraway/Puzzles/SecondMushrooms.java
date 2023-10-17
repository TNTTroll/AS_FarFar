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
import com.example.far_faraway.RoomTwo;

public class SecondMushrooms extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    Object back;

    int activeShroom = -1;

    int[] posX = _PUZZLES.secondMushroomsPosX;
    int posY = _PUZZLES.secondMushroomsPosY;

    int[] needShroom = _PUZZLES.secondMushroomsSequence;
    int[] usedShroom = new int[needShroom.length];
    Object[] shrooms = new Object[needShroom.length];

    public SecondMushrooms() {
    }

    public static SecondMushrooms newInstance(String param1, String param2) {
        SecondMushrooms fragment = new SecondMushrooms();
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
        if (v.getId() == R.id.secondMushroomsBack)
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomTwo()).addToBackStack(null).commit();

        for (Object shroom : shrooms) {
            if ( shroom.getId() == v.getId() ) {

                int currentShroom = Integer.parseInt( "" + shroom.getName().charAt(shroom.getName().length()-1) ) - 1;

                if (activeShroom == -1)
                    activeShroom = currentShroom;

                else {
                    Object swap = shrooms[currentShroom];
                    shrooms[currentShroom] = shrooms[activeShroom];
                    shrooms[activeShroom] = swap;

                    shrooms[activeShroom].setName("secondMushroomsMorel_" + (activeShroom + 1));
                    shrooms[currentShroom].setName("secondMushroomsMorel_" + (currentShroom + 1));

                    int swapInt = usedShroom[currentShroom];
                    usedShroom[currentShroom] = usedShroom[activeShroom];
                    usedShroom[activeShroom] = swapInt;

                    activeShroom = -1;

                    redrawShrooms();
                }

                break;
            }
        }

        if (checkShrooms()) {
            MainActivity.secondsPassed[0] = true;

            for (Object shroom : shrooms)
                shroom.setEnabled(false);

            setPuzzleUsed("SecondMushrooms", 2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_second_mushrooms, container, false);

        back = (Object) view.findViewById(R.id.secondMushroomsBack);
        back.setOnClickListener(this);

        int shroomPos = 0;
        for (int index = 1; index <= 4; index++) {
            try {
                int resID = getResId("secondMushroomsMorel_" + index, R.id.class);
                Object obj = (Object) view.findViewById(resID);

                obj.setParam("secondMushroomsMorel_" + index, "mushroom_" + (index-1));
                obj.setOnClickListener(this);

                shrooms[shroomPos] = obj;

                usedShroom[shroomPos] = shroomPos + 1;

                shroomPos += 1;

            }
            catch(NullPointerException ignored) {}
        }

        redrawShrooms();

        return view;
    }

    private void redrawShrooms() {
        for (int x = 0; x < shrooms.length; x++) {
            shrooms[x].setY( posY );
            shrooms[x].setX( posX[0] + posX[1] * x  );
        }
    }

    private boolean checkShrooms() {
        for (int x = 0; x < usedShroom.length; x++)
            if (usedShroom[x] != needShroom[x])
                return false;

        return true;
    }
}