package com.example.tom_pc.clevernote;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MusicList extends Fragment {

    GridView gridView;
    ArrayList<MusicPost> list;
    MusicListAdapter adapter = null;

    public MusicList() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_list, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = (GridView) getView().findViewById(R.id.gridView);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list = new ArrayList<>();
        adapter = new MusicListAdapter(getContext(), R.layout.music_items, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
        Cursor cursor = AudioFragment.sqLiteHelper.getData("SELECT * FROM MUSIC");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String category = cursor.getString(2);
            list.add(new MusicPost(name, category, id));
        }
        adapter.notifyDataSetChanged();


    }
}
