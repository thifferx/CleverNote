package com.example.tom_pc.clevernote;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class NewsList extends Fragment {

    ListView gridView1;
    ArrayList<NewsPost> mGridData;
    NewsListAdapter mGridAdapter = null;
    Button btnDownload;

    public NewsList() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView1 = (ListView) getView().findViewById(R.id.gridView1);
        btnDownload = (Button) getView().findViewById(R.id.btnDownload);
        btnDownload.setEnabled(true);
        //gridView1.setClickable(true);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGridData = new ArrayList<>();
        mGridAdapter = new NewsListAdapter(getActivity(), R.layout.news_items, mGridData);
        gridView1.setAdapter(mGridAdapter);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncHttpTask().execute();

            }
        });
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = mGridData.get(position).getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });
    }
    public class AsyncHttpTask extends AsyncTask<Void, Void, Void> {
        String data = "";
        @Override
        protected Void doInBackground(Void... voids) {
            String line = "";
            HttpsURLConnection httpURLConnection = null;

            try {
                URL url = new URL("https://api.myjson.com/bins/s6dsc"); // https://newsapi.org/s/ign-api
                httpURLConnection = (HttpsURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while(line != null){
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                parseResult(data);

            } catch (MalformedURLException ex) {
                ex.printStackTrace();


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void voids) {
            // Download complete. Let us update UI
                mGridAdapter.setGridData(mGridData);
                btnDownload.setEnabled(false);
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray articles = response.optJSONArray("articles");
            NewsPost item;
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.optJSONObject(i);
                String title = article.optString("title");
                String desc = article.optString("description");
                String urlArticle = article.optString("url");
                
                item = new NewsPost();
                item.setId(i+1);
                item.setTitle(title);
                item.setDescription(desc);
                item.setUrl(urlArticle);
                mGridData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
