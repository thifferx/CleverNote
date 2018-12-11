package com.example.tom_pc.clevernote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends Fragment {

    private ImageView image1;
    private ImageView image2;
    public LinearLayout lv;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private int loaded;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        image1 = (ImageView) getView().findViewById(R.id.imageView2);
        image2 = (ImageView) getView().findViewById(R.id.imageView3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lv = (LinearLayout) getActivity().findViewById(R.id.back);
        preferences = getActivity().getSharedPreferences("pref",MODE_PRIVATE);
        int loaded = preferences.getInt("bg1",R.color.colorPrimary);
        editor = preferences.edit();
        lv.setBackgroundColor(loaded);
        image1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        int background = ContextCompat.getColor(getActivity(),R.color.colorAccent);
                        editor.putInt("bg1",background);
                        editor.apply();
                        lv.setBackgroundColor(background);
                        break;
                }
                return false;
            }
        });
        image2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        int backgroundColor = ContextCompat.getColor(getActivity(),R.color.colorPrimary);
                        editor.putInt("bg1",backgroundColor);
                        editor.apply();
                        lv.setBackgroundColor(backgroundColor);
                        break;
                }
                return false;
            }
        });
    }
}
