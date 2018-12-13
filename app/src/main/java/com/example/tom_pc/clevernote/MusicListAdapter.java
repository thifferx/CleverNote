package com.example.tom_pc.clevernote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MusicListAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private ArrayList<MusicPost> musicList;

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
        TextView txtName, txtCategory;
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
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        MusicPost post = musicList.get(position);

        holder.txtName.setText(post.getName());
        holder.txtCategory.setText(post.getCategory());

        return row;
    }
}
