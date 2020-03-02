package com.example.firstlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.firstlayout.Adapters.PaginationAdapter;

import java.util.ArrayList;

public class PaginationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PaginationAdapter paginationAdapter;
    ArrayList<String>al;
    int totalItemCount=0;
    int previousCount=0;
    boolean loading=true;
    int firstVisibleItem, visibleItemCount;
    private int visibleThreshold=5;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagination);
        recyclerView=findViewById(R.id.paginationRecycler);
        progressBar=findViewById(R.id.progressbar);
        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //inserting data
        al=new ArrayList<>();
        al.add("Bharat");
        al.add("Ujwal");
        al.add("Akaanksha");
        al.add("Priya");
        al.add("Astha");
        al.add("Ashutosh");
        al.add("Anupam");
        al.add("Astha");
        al.add("Ashutosh");
        al.add("Anupam");
        paginationAdapter=new PaginationAdapter(al,this);
        recyclerView.setAdapter(paginationAdapter);
      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
          @Override
          public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
              super.onScrollStateChanged(recyclerView, newState);
          }

          @Override
          public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
              super.onScrolled(recyclerView, dx, dy);
              visibleItemCount=recyclerView.getChildCount();
              totalItemCount=linearLayoutManager.getItemCount();
              firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

              if (loading) {
                  if (totalItemCount > previousCount) {
                      loading = false;
                      previousCount = totalItemCount;

                      //
                      progressBar.setVisibility(View.VISIBLE);
                      new Handler().postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              for (int i = 0; i < 5; i++) {
                                  al.add(al.get(i));
                                  paginationAdapter.notifyDataSetChanged();
                              }
                              progressBar.setVisibility(View.GONE);
                          }
                      },1000);
                      }
              }
              if (!loading && (totalItemCount - visibleItemCount)
                      <= (firstVisibleItem + visibleThreshold)) {
                  loading = true;
              }

          }
      });




    }
}
