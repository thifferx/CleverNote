package com.example.tom_pc.clevernote;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

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
        gridView.setClickable(true);


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
            String path = cursor.getString(3);
            list.add(new MusicPost(name, category, path, id));
        }
        adapter.notifyDataSetChanged();
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                            Cursor c = AudioFragment.sqLiteHelper.getData("SELECT id FROM MUSIC");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            // show dialog update at here
                            showDialogUpdate(getActivity(), arrID.get(position));

                        } else {
                            // delete
                            Cursor c = AudioFragment.sqLiteHelper.getData("SELECT id FROM MUSIC");
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
    private void showDialogUpdate(Activity activity, final int position) {

        final Dialog dialog = new Dialog(activity);

        dialog.setContentView(R.layout.update_music_fragment);
        dialog.setTitle("Update");

        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
        final EditText edtCategory = (EditText) dialog.findViewById(R.id.edtCategory);

        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AudioFragment.sqLiteHelper.updateData(
                            edtName.getText().toString().trim(),
                            edtCategory.getText().toString().trim(),
                            "",position
                    );
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateMusicList();
            }
        });
    }

    private void showDialogDelete(final int idMusic){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    AudioFragment.sqLiteHelper.deleteData(idMusic);
                    Toast.makeText(getActivity(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateMusicList();
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

    private void updateMusicList(){
        // get all data from sqlite
        Cursor cursor = AudioFragment.sqLiteHelper.getData("SELECT * FROM MUSIC");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String category = cursor.getString(2);
            String path = cursor.getString(3);
            list.add(new MusicPost(name, category, path, id));
        }
        adapter.notifyDataSetChanged();
    }


}
