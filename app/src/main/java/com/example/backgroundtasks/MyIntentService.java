package com.example.backgroundtasks;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    public static final String NOTIFICATION ="com.example.backgroundtasks.MyIntentService" ;
    public static final String FILEPATH ="filepath" ;
    public static final String RESULT = "result";
    public MyIntentService()
    {
        super("MyIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        File file=new File(Environment.getExternalStorageDirectory(),"food.jpg");

        boolean success = file.mkdir();
        if (!success)
        {
            Log.v("directory not created", "directory not created");
        }
            Bitmap bitmap=null;
        try
        {
            URL url = new URL(MainActivity.ImageUrl);
            URLConnection conn = url.openConnection();

            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
//
            //saving the file
            File createFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"test");
            createFolder.mkdir();
            File saveImage = new File(createFolder,"food.jpg");
            try {
                OutputStream outputStream = new FileOutputStream(saveImage);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

         //   Toast.makeText(MyIntentService.this, "Downloading Completed", Toast.LENGTH_SHORT).show();
            broadCastResult(saveImage.getAbsolutePath(),-1);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    //broadcasting the image path and result
    private void broadCastResult(String outputPath, int result) {
        Log.v("path=",outputPath);
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(FILEPATH, outputPath);
        intent.putExtra(RESULT, result);
        getApplicationContext().sendBroadcast(intent);
    }



    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
