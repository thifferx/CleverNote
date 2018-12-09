package com.example.tom_pc.clevernote;

import android.content.Context;
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


public class SettingsFragment extends Fragment {


    private ImageView image1;
    private ImageView image2;
    private LinearLayout lv;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(count != 0){
            SharedPreferences prefs = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
            lv = (LinearLayout) getActivity().findViewById(R.id.back);
            int loaded = prefs.getInt("b1",R.color.colorPrimary);
            lv.setBackgroundColor(loaded);
        }
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        image1 = (ImageView) getView().findViewById(R.id.imageView2);
        image2 = (ImageView) getView().findViewById(R.id.imageView3);
        lv = (LinearLayout) getActivity().findViewById(R.id.back);
        preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = preferences.edit();
        image1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        int background = ContextCompat.getColor(getActivity(),R.color.colorAccent);
                        lv.setBackgroundColor(background);
                        editor.putInt("bg1",background);
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
                        lv.setBackgroundColor(backgroundColor);
                        editor.putInt("bg1",backgroundColor);
                        break;
                }
                return false;
            }
        });
        editor.apply();
        count++;
    }


}
