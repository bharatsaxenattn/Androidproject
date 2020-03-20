package com.example.webservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    public static ArrayList<ListItems> arrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView=findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        GetData getData=new GetData();
        getData.execute();
        Log.v("list",arrayList+" ");




    }
    public class GetData extends AsyncTask<Void,Void,Void> {
        String data="",dataparsed="",singleparse="",finaldata="";
        String json_string="";
        ArrayList<ListItems> arrayList=new ArrayList<>();
        public static final String URL="https://storage.googleapis.com/network-security-conf-codelab.appspot.com/v2/posts.json";
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                java.net.URL url=new URL(URL);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String line="";
                while (line!=null){
                    line=bufferedReader.readLine();
                    data=data+line;
                }
                JSONObject jsonObject=new JSONObject(data);
                JSONArray jsonArray=jsonObject.getJSONArray("posts");
                for (int i = 0; i <jsonArray.length() ; i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String name = jsonObject1.getString("name");
                    String message = jsonObject1.getString("message");
                    String profileImage = jsonObject1.getString("profileImage");
                    arrayList.add(new ListItems(name,message,profileImage));

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            myAdapter=new MyAdapter(arrayList,ListActivity.this);
            recyclerView.setAdapter(myAdapter);
        }
    }
}
