package com.whatever.holidayspecial.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cergo on 05/12/16.
 */

public class ImageTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView view;

    public ImageTask(ImageView view) {
        this.view = view;
    }

    /**
     * Load and decode an image from the provided URL.
     * @param urls array of source URLs
     */
    public Bitmap doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            InputStream response = null;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            response = connection.getInputStream();
            return BitmapFactory.decodeStream(response);
        } catch (MalformedURLException ex) {
            Log.e("ImageTask", "Malformed URL [" + urls[0] + "]");
        } catch (IOException ex) {
            Log.e("ImageTask", "IO exception");
        }
        return null;
    }

    public void onPostExecute(Bitmap bitmap) {
        Log.d("ImageTask", "inserting bitmap");
        view.setImageBitmap(bitmap);
    }
}
