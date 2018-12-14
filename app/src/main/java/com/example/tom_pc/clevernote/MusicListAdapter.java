package com.example.tom_pc.clevernote;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MusicListAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private ArrayList<MusicPost> musicList;
    private String pathS;
    MediaPlayer mediaPlayer;

    public MusicListAdapter(Context context, int layout, ArrayList<MusicPost> musicList) {
        this.context = context;
        this.layout = layout;
        this.musicList = musicList;
    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Object getItem(int position) {
        return musicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        public TextView txtName, txtCategory;
        public Button btnPlay1, btnStop1;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.txtName = (TextView) row.findViewById(R.id.txtName);
            holder.txtCategory = (TextView) row.findViewById(R.id.txtCategory);
            holder.btnPlay1 = (Button) row.findViewById(R.id.btnPlay1);
            holder.btnStop1 = (Button) row.findViewById(R.id.btnStop1);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        MusicPost post = musicList.get(position);

        holder.txtName.setText(post.getName());
        holder.txtCategory.setText(post.getCategory());
        pathS = post.getPath();

        holder.btnPlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.btnStop1.setEnabled(true);
                mediaPlayer = new MediaPlayer();
                try{
                    mediaPlayer.setDataSource(pathS);
                    mediaPlayer.prepare();
                } catch (IOException e){
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        });

        holder.btnStop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.btnStop1btnStop.setEnabled(false);
                //holder.btnPlay1.setEnabled(true);
                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }
        });
        return row;
    }
}
