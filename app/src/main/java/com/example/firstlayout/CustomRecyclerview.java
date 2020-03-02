package com.example.firstlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.firstlayout.Adapters.CustomListAdapter;
import com.example.firstlayout.POJO.NewsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomRecyclerview extends AppCompatActivity {

    RecyclerView recyclerview;
    ArrayList<NewsModel> al=new ArrayList<>();
    CustomListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        recyclerview = findViewById(R.id.recyclerview);



        JSONArray jsonArray=new JSONArray();
        for (int i = 0; i <10 ; i++) {
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("title","Australia's coronavirus pandemic plan: mass vaccinations and stadium quarantine");
                jsonObject.put("description","Australia's coronavirus pandemic plan: mass vaccinations and stadium quarantine");
                jsonObject.put("publishedAt","20200227T02:41:00+00:00");
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject json= null;
                try {
                    json = jsonArray.getJSONObject(i);
                    String title=json.getString("title");
                    String description=json.getString("description");
                    String time1=json.getString("publishedAt");
                    if(i==0)
                    {
                        al.add(new NewsModel(title,description,time1,R.drawable.trump,0));

                    }
                    else if(i==1)
                    {
                        al.add(new NewsModel(title,description,time1,0,2));
                    }
                    else if(i%2==0)
                    al.add(new NewsModel(title,description,time1,R.drawable.food,1));

                    else
                        al.add(new NewsModel(title,description,time1,R.drawable.corona,1));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }



        //inserting data into main heading

        adapter=new CustomListAdapter(al,this);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }
}
