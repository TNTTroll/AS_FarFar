package com.example.far_faraway.Puzzles;

import static com.example.far_faraway.Scene.current_Item;
import static com.example.far_faraway.Scene.inventory;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.example.far_faraway.Holder;
import com.example.far_faraway.MainActivity;
import com.example.far_faraway.Object;
import com.example.far_faraway.R;
import com.example.far_faraway.RoomOne;
import com.example.far_faraway.Scene;

import pl.droidsonroids.gif.GifImageView;

public class FirstCloset extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    ImageButton wall;
    Object back, card, door;
    Holder close;
    VideoView video;

    public FirstCloset() {
    }

    public static FirstCloset newInstance(String param1, String param2) {
        FirstCloset fragment = new FirstCloset();
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
            case (R.id.firstClosetBack):
                getParentFragmentManager().beginTransaction().replace(R.id.roomView, new RoomOne()).addToBackStack(null).commit();
                break;

            case (R.id.firstClosetClose):

                if (current_Item != -1)
                    if (close.setItem(inventory[current_Item])) {
                        inventory[current_Item] = null;
                        current_Item = -1;

                        close.setVisibility(View.GONE);

                        door.setEnabled(true);

                        MainActivity.firstClosetCleaned = true;
                    }

                break;

            case (R.id.firstCard):

                if (card.setToInventory()) {
                    card.setVisibility(View.GONE);
                    MainActivity.firstClosetTookCard = true;
                }

                break;

            case (R.id.firstClosetDoor):
                door.setVisibility(View.GONE);

                if (!MainActivity.firstClosetTookCard)
                    card.setVisibility(View.VISIBLE);

                if (MainActivity.firstElectricity) {
                    wall.setVisibility(View.GONE);
                    video.setVisibility(View.VISIBLE);
                    startVideo();
                }

                break;

        }

        Scene.reloadInventory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_first_closet, container, false);

        back = (Object) view.findViewById(R.id.firstClosetBack);
        back.setOnClickListener(this);

        wall = (ImageButton) view.findViewById(R.id.firstClosetWall);

        door = (Object) view.findViewById(R.id.firstClosetDoor);
        door.setOnClickListener(this);

        close = (Holder) view.findViewById(R.id.firstClosetClose);
        close.setParam("firstClosetClose", "firstAnti", "closet_rust");
        close.setOnClickListener(this);

        card = (Object) view.findViewById(R.id.firstCard);
        card.setParam("firstCard", "card");
        card.setOnClickListener(this);
        card.setVisibility(View.GONE);

        video = (VideoView) view.findViewById(R.id.firstClosetBG);
        video.setOnPreparedListener(mp -> mp.setLooping(true));

        if (MainActivity.firstClosetCleaned)
            close.setVisibility(View.GONE);
        else
            door.setEnabled(false);

        if (MainActivity.firstElectricity && MainActivity.firstClosetCleaned) {
            wall.setVisibility(View.GONE);
            startVideo();
        } else
            video.setVisibility(View.GONE);

        return view;
    }

    private void startVideo() {
        String uriPath2 = "android.resource://com.example.far_faraway/" + R.raw.closet_bg_video;
        Uri uri2 = Uri.parse(uriPath2);
        video.setVideoURI(uri2);
        video.requestFocus();
        video.start();
    }
}