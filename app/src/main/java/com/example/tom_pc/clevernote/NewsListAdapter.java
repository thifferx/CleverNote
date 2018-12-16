package com.example.tom_pc.clevernote;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class NewsListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<NewsPost> newsList = new ArrayList<>();

    public NewsListAdapter(Context context, int layout, ArrayList<NewsPost> newsList) {
        this.context = context;
        this.layout = layout;
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setGridData(ArrayList<NewsPost> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }
    private class ViewHolder{
        public TextView txtArticle, txtTitle, txtDescription;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();
        NewsPost post = newsList.get(position);

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.txtArticle = (TextView) row.findViewById(R.id.articleID);
            holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
            holder.txtDescription= (TextView) row.findViewById(R.id.txtDescription);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        holder.txtTitle.setText(post.getTitle());
        holder.txtDescription.setText(post.getDescription());
        holder.txtArticle.setText("Article no. " + Integer.toString(post.getId()));


        return row;
    }
}
