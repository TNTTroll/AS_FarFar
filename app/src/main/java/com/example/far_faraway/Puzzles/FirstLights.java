package com.example.far_faraway.Puzzles;

import static com.example.far_faraway.Scene.setPuzzleUsed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomOne;

public class FirstLights extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;
    ImageView lamp;
    Object back, btn1, btn2, btn3;

    int round = 0;
    int[] sequence = _PUZZLES.firstLightsSequence;

    int btnClickTime = _PUZZLES.firstLightsButtonFlashTime;
    int time = _PUZZLES.firstLightsLampFlashTime;

    public FirstLights() {
    }

    public static FirstLights newInstance(String param1, String param2) {
        FirstLights fragment = new FirstLights();
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

        if (v.getId() == R.id.firstLightsBack)
            getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne()).addToBackStack(null).commit();

        if (MainActivity.firstElectricity) {
            switch (v.getId()) {
                case (R.id.firstLights1):
                    if (sequence[round] == 1)
                        round += 1;
                    else
                        round = 0;

                    btn1.setIcon("lamp_button_on");
                    handler.postDelayed(() -> {
                        btn1.setIcon("none");
                    }, btnClickTime);

                    break;

                case (R.id.firstLights2):
                    if (sequence[round] == 2)
                        round += 1;
                    else
                        round = 0;

                    btn2.setIcon("lamp_button_on");
                    handler.postDelayed(() -> {
                        btn2.setIcon("none");
                    }, btnClickTime);

                    break;

                case (R.id.firstLights3):
                    if (sequence[round] == 3)
                        round += 1;
                    else
                        round = 0;

                    btn3.setIcon("lamp_button_on");
                    handler.postDelayed(() -> {
                        btn3.setIcon("none");
                    }, btnClickTime);

                    break;
            }

            setButtons(false);

            checkLamps();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first_lights, container, false);

        back = (Object) view.findViewById(R.id.firstLightsBack);
        btn1 = (Object) view.findViewById(R.id.firstLights1);
        btn2 = (Object) view.findViewById(R.id.firstLights2);
        btn3 = (Object) view.findViewById(R.id.firstLights3);
        lamp = (ImageView) view.findViewById(R.id.firstLightsLamp);

        back.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        return view;
    }

    Handler handler = new Handler();
    private void checkLamps() {
        if (round == sequence.length) {
            setPuzzleUsed("FirstLights", 1);

            MainActivity.firstLamps = true;

            lamp.setImageResource(R.drawable.lamp_on);

        } else {
            if (sequence[round] == 1) {
                lamp.setImageResource(R.drawable.lamp_on);
                handler.postDelayed(() -> {
                    lamp.setImageResource(R.drawable.lamp_off);
                    setButtons(true);
                }, time);

            } else if (sequence[round] == 2) {
                lamp.setImageResource(R.drawable.lamp_on);
                handler.postDelayed(() -> {
                    lamp.setImageResource(R.drawable.lamp_off);
                    handler.postDelayed(() -> {
                        lamp.setImageResource(R.drawable.lamp_on);
                        handler.postDelayed(() -> {
                            lamp.setImageResource(R.drawable.lamp_off);
                            setButtons(true);
                        }, time);
                    }, time);
                }, time);

            } else {
                lamp.setImageResource(R.drawable.lamp_on);
                handler.postDelayed(() -> {
                    lamp.setImageResource(R.drawable.lamp_off);
                    handler.postDelayed(() -> {
                        lamp.setImageResource(R.drawable.lamp_on);
                        handler.postDelayed(() -> {
                            lamp.setImageResource(R.drawable.lamp_off);
                            handler.postDelayed(() -> {
                                lamp.setImageResource(R.drawable.lamp_on);
                                handler.postDelayed(() -> {
                                    lamp.setImageResource(R.drawable.lamp_off);
                                    setButtons(true);
                                }, time);
                            }, time);
                        }, time);
                    }, time);
                }, time);
            }

        }

    }

    private void setButtons(boolean setter) {
        if (setter) {
            btn1.setEnabled(true);
            btn2.setEnabled(true);
            btn3.setEnabled(true);
        } else {
            btn1.setEnabled(false);
            btn2.setEnabled(false);
            btn3.setEnabled(false);
        }
    }

}