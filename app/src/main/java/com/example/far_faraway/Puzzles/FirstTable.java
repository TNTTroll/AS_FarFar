package com.example.far_faraway.Puzzles;

import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.setPuzzleUsed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.far_faraway.Holder;
import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomOne;
import com.example.far_faraway.Scene;

import java.util.Arrays;

public class FirstTable extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    int height = _PUZZLES.firstTableHeight;
    Object[] flasks = new Object[3];
    Object active_flask;

    int[] needMix = _PUZZLES.firstTableSequence;
    int[] useMix = new int[needMix.length];
    int usedFlask = 0;

    Object back, clear, anti;
    Holder mixer;

    public FirstTable() {
    }

    public static FirstTable newInstance(String param1, String param2) {
        FirstTable fragment = new FirstTable();
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

        for (Object flask : flasks) {
            int resID = getResId(flask.getName(), R.id.class);
            Object fls = (Object) view.findViewById(resID);

            fls.setY( height );

            if (resID == v.getId())
                active_flask = fls;
        }

        if (active_flask != null)
            active_flask.setY( height - 50 );

        switch (v.getId()) {
            case (R.id.firstTableBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne()).addToBackStack(null).commit();
                break;

            case (R.id.firstTableMixer):

                if (active_flask != null) {
                    int flaskIndex = Integer.parseInt("" + active_flask.getName().charAt(active_flask.getName().length() - 1));

                    if (usedFlask < needMix.length) {
                        useMix[usedFlask] = flaskIndex;
                        usedFlask += 1;
                    }

                    boolean flag = true;
                    for (int x = 0; x < useMix.length; x++)
                        if (useMix[x] != needMix[x]) {
                            flag = false;
                            break;
                        }

                    if (flag)
                        MainActivity.firstTableMedicineDone = true;

                    if (MainActivity.firstTableMedicineDone) {
                        mixer.setVisibility(View.GONE);

                        anti.setVisibility(View.VISIBLE);
                        anti.setParam("firstAnti", "iron");
                        anti.setOnClickListener(this);

                        active_flask = null;
                    }
                }

                break;

            case (R.id.firstTableClear):
                active_flask = null;

                usedFlask = 0;
                Arrays.fill(useMix, 0);

                break;

            case (R.id.firstTableAnti):
                if (MainActivity.firstTableMedicineDone) {
                    Object obj = (Object) view.findViewById(R.id.firstTableAnti);
                    boolean taken = obj.setToInventory();

                    if (taken) {
                        obj.setVisibility(View.GONE);
                        MainActivity.firstTableTookAnti = true;

                        setPuzzleUsed("FirstTable", 1);
                    }
                }

                break;
        }

        changeMixerFace();

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first_table, container, false);

        back = (Object) view.findViewById(R.id.firstTableBack);
        back.setOnClickListener(this);

        for (int x = 0; x < flasks.length; x++) {
            int resID = getResId("firstTableFlask_" + (x+1), R.id.class);
            Object flask = (Object) view.findViewById(resID);
            flask.setParam("firstTableFlask_" + (x+1), "flask");
            flask.setOnClickListener(this);

            flasks[x] = flask;
        }

        mixer = (Holder) view.findViewById(R.id.firstTableMixer);
        if (!MainActivity.firstTableMedicineDone) {
            mixer.setParam("firstTableMixer","firstTableFlask" + needMix[0], "can_0");
            mixer.setOnClickListener(this);
        } else
            mixer.setVisibility(View.GONE);

        clear = (Object) view.findViewById(R.id.firstTableClear);
        clear.setOnClickListener(this);

        anti = (Object) view.findViewById(R.id.firstTableAnti);
        if (!MainActivity.firstTableTookAnti && MainActivity.firstTableMedicineDone) {
            anti.setParam("firstAnti", "iron");
            anti.setOnClickListener(this);
        } else
            anti.setVisibility(View.GONE);

        return view;
    }

    private void changeMixerFace() {
        mixer.setIcon("can_" + usedFlask);
    }
}