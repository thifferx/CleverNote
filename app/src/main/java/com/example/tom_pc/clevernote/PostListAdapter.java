package com.example.tom_pc.clevernote;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PostListAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private ArrayList<Post> postsList;

    public PostListAdapter(Context context, int layout, ArrayList<Post> postsList) {
        this.context = context;
        this.layout = layout;
        this.postsList = postsList;
    }

    @Override
    public int getCount() {
        return postsList.size();
    }

    @Override
    public Object getItem(int position) {
        return postsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtNamePost, messageID;
        TextView txtPost;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();
        Post mPost = postsList.get(position);

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.messageID= (TextView) row.findViewById(R.id.MessageID);
            holder.txtNamePost= (TextView) row.findViewById(R.id.txtNamePost);
            holder.txtPost = (TextView) row.findViewById(R.id.txtPost);
            holder.imageView = (ImageView) row.findViewById(R.id.imageView4);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }


        holder.txtNamePost.setText(mPost.getName());
        holder.txtPost.setText(mPost.getPost());
        holder.messageID.setText("Post no. " + Integer.toString(mPost.getId()));

        byte[] image = mPost.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imageView.setImageBitmap(bitmap);
        holder.txtPost.setEnabled(false);

        return row;
    }

}
