package com.example.tom_pc.clevernote;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PostList extends Fragment {
    ListView lst2;
    ArrayList<Post> list2;
    PostListAdapter adapterM = null;

    public PostList() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_txt_list, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lst2 = (ListView) getView().findViewById(R.id.ListView2);
        lst2.setClickable(true);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list2 = new ArrayList<>();
        adapterM = new PostListAdapter(getContext(), R.layout.text_items, list2);
        lst2.setAdapter(adapterM);

        // get all data from sqlite
        Cursor cursor = PostFragment.sqLiteHelper1.getData("SELECT * FROM POST");
        list2.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String post = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            list2.add(new Post(id, name, post, image));
        }
        adapterM.notifyDataSetChanged();
        lst2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            Cursor c = PostFragment.sqLiteHelper1.getData("SELECT id FROM POST");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));

                        }
                    }
                });
                dialog.show();
                return true;
            }
        });

    }
    private void showDialogDelete(final int idPost){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    PostFragment.sqLiteHelper1.deleteData(idPost);
                    Toast.makeText(getActivity(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updatePostList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updatePostList(){
        // get all data from sqlite
        Cursor cursor = PostFragment.sqLiteHelper1.getData("SELECT * FROM POST");
        list2.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String post = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            list2.add(new Post(id, name, post, image));
        }
        adapterM.notifyDataSetChanged();
    }



}
