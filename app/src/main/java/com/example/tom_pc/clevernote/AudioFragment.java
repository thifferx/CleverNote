package com.example.tom_pc.clevernote;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;


public class AudioFragment extends Fragment {

    TextView message;
    EditText edtName, edtCategory;
    Button btnRecord, btnPlay, btnAdd, btnStopRecord, btnList, btnStop;
    public static SQLiteHelper sqLiteHelper;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    private String pathSave;
    final int REQUST_PERMISSION_CODE = 1000;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mic, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        if(checkPermissionFromDevice()){
            btnRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pathSave = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+UUID.randomUUID().toString()+"_audio_record.3gp";
                    setupMediaRecorder();
                    try{
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    btnPlay.setEnabled(false);
                    btnStop.setEnabled(false);
                    Toast.makeText(getActivity(),"Recording...", Toast.LENGTH_SHORT).show();
                }
            });
            btnStopRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaRecorder.stop();
                    btnStopRecord.setEnabled(false);
                    btnPlay.setEnabled(true);
                    btnRecord.setEnabled(true);
                    btnStop.setEnabled(false);
                }
            });

            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnStop.setEnabled(true);
                    btnStopRecord.setEnabled(false);
                    btnRecord.setEnabled(false);
                    mediaPlayer = new MediaPlayer();
                    try{
                        mediaPlayer.setDataSource(pathSave);
                        mediaPlayer.prepare();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                    Toast.makeText(getActivity(), "Playing...", Toast.LENGTH_SHORT).show();
                }
            });

            btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnStopRecord.setEnabled(true);
                    btnRecord.setEnabled(true);
                    btnStop.setEnabled(false);
                    btnPlay.setEnabled(true);
                    if(mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        setupMediaRecorder();
                    }
                }
            });

        }
        else{
            requestPermission();
        }


        sqLiteHelper = new SQLiteHelper(getContext(), "MusicDB.sqlite", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS MUSIC(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, category VARCHAR, path VARCHAR)");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    sqLiteHelper.insertData(
                            edtName.getText().toString().trim(),
                            edtCategory.getText().toString().trim(),
                            pathSave
                    );
                    Toast.makeText(getActivity(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtCategory.setText("");
                    //pathSave = "";
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

    private void setupMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);


    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO}, REQUST_PERMISSION_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUST_PERMISSION_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(getActivity(),"Permission Granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(),"Permission Denied", Toast.LENGTH_SHORT).show();

            }
            break;
        }
    }

    private void init(){
        edtName = (EditText) getView().findViewById(R.id.edtName);
        edtCategory = (EditText) getView().findViewById(R.id.edtCategory);
        btnRecord = (Button) getView().findViewById(R.id.btnRecord);
        btnPlay = (Button) getView().findViewById(R.id.btnPlay);
        btnStopRecord = (Button) getView().findViewById(R.id.stop);
        btnStop = (Button) getView().findViewById(R.id.btnStop);
        btnAdd = (Button) getView().findViewById(R.id.btnAdd);
        btnList = (Button) getView().findViewById(R.id.btnList);
        message = (TextView) getView().findViewById(R.id.Message);
    }

    private boolean checkPermissionFromDevice(){
        int write_external_storage_result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED && record_audio_result == PackageManager.PERMISSION_GRANTED;
    }
}
