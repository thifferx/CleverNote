package com.example.tom_pc.clevernote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AudioFragment extends Fragment {

    TextView message;
    EditText edtName, edtCategory;
    Button btnRecord, btnPlay, btnAdd, btnList;
    public static SQLiteHelper sqLiteHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        sqLiteHelper = new SQLiteHelper(getContext(), "MusicDB.sqlite", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS MUSIC(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, category VARCHAR)");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    sqLiteHelper.insertData(
                            edtName.getText().toString().trim(),
                            edtCategory.getText().toString().trim()
                    );
                    Toast.makeText(getActivity(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtCategory.setText("");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container,new MusicList());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void init(){
        edtName = (EditText) getView().findViewById(R.id.edtName);
        edtCategory = (EditText) getView().findViewById(R.id.edtCategory);
        btnRecord = (Button) getView().findViewById(R.id.btnRecord);
        btnPlay = (Button) getView().findViewById(R.id.btnPlay);
        btnAdd = (Button) getView().findViewById(R.id.btnAdd);
        btnList = (Button) getView().findViewById(R.id.btnList);
        message = (TextView) getView().findViewById(R.id.Message);
    }
}
