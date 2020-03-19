package com.example.backgroundtasks;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button, serviceBtn, managerBtn;
    ImageView imageView;
    ProgressBar progressBar;
    TextView textView;
    public static String ImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSszVHjMFLeqdbp1HCAPm2fe7PeV8EDMkuhjVY-10b-_1ZE88Vw";
    int totalLength;
    int total;
    IntentFilter filter;
    public static final int REQUEST_CODE = 102;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(MyIntentService.FILEPATH);
                Bitmap bitmap = BitmapFactory.decodeFile(string);
                imageView.setImageBitmap(bitmap);
                Log.v("path==", string + " ");
                int resultCode = bundle.getInt(MyIntentService.RESULT);
                if (resultCode == RESULT_OK) {
                    Toast.makeText(MainActivity.this,
                            "Download complete. Download URI: " + string,
                            Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(MainActivity.this, "Download failed",
                            Toast.LENGTH_LONG).show();

                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcast();
    }

    //registering the broadcast
    private void registerBroadcast() {

        //broadcast name will be in MyintentService
        filter = new IntentFilter(MyIntentService.NOTIFICATION);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.image);
        managerBtn = findViewById(R.id.managerDownload);
        progressBar = findViewById(R.id.progresbar);
        textView = findViewById(R.id.progressText);
        serviceBtn = findViewById(R.id.serviceDownload);

        button.setOnClickListener(this);
        serviceBtn.setOnClickListener(this);
        managerBtn.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            total = 0;
            new DownloadImage().execute(10);
        }
        if (v.getId() == R.id.serviceDownload) {
            getUserPermission();
            Intent intent = new Intent(MainActivity.this, MyIntentService.class);
            startService(intent);


        }
        if (v.getId() == R.id.managerDownload) {
            getUserPermission();
            Intent intent = new Intent(MainActivity.this, MyService.class);
            startService(intent);


        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getUserPermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission
                    .READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

    }


    public class DownloadImage extends AsyncTask {


        @Override
        protected Object doInBackground(Object[] objects) {

            if (Utils.checkNetworkStatus(MainActivity.this)) {

            }
            int count = (int) objects[0];

            while (count > 0) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        total = total + 10;

                        publishProgress("" + (int) ((total * 100) / totalLength));
                        //  textView.setText("Downloading image "+total+" %");


                    }
                }, 1000);
                count--;


            }
            Bitmap bitmap = downloadBitmap();
            return bitmap;
        }

        private Bitmap downloadBitmap() {
            Bitmap bm = null;
            try {
                URL url = new URL(ImageUrl);
                URLConnection conn = url.openConnection();
                totalLength = conn.getContentLength();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e("exception", "Error getting the image from server : " + e.getMessage().toString());
            }
            return bm;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            textView.setText("Downloading Image");


        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressBar.setProgress(100);
            Bitmap bitmap = (Bitmap) o;
            imageView.setImageBitmap(bitmap);
            textView.setText("Image Downloaded");
            progressBar.setVisibility(View.GONE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Read write permission granted by user", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this, "Read write permission  not granted by user", Toast.LENGTH_LONG).show();

    }
}
