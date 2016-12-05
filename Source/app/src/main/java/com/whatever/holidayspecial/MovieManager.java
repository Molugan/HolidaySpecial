package com.whatever.holidayspecial;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cergo on 04/12/16.
 */

public class MovieManager {

    protected static final int TASK_COMPLETE = 1;
    private Handler handler;

    /**
     * Create a new MovieManager initialised with a handler executing callbacks in the UI thread.
     */
    private MovieManager() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                ImageTask task = (ImageTask) inputMessage.obj;
                switch (inputMessage.what) {
                    case TASK_COMPLETE:
                        task.getImageView().setImageBitmap(task.getBitmap());
                    default:
                        break;
                }
            }
        };
    }

    /**
     * Send a TASK_COMPLETE message to the inner handler.
     */
    private void complete(ImageTask task) {
        Message message = handler.obtainMessage(TASK_COMPLETE, task);
        handler.sendMessage(message);
    }

    /**
     * Request for the manager to load an image into the selected ImageView.
     */
    public void loadImage(ImageView view, String url) {
        Log.d("MovieManager", "load image from " + url);
        ImageTask task = new ImageTask(this, view);
        task.execute(url);
    }

    /**
     * Make the MovieManager into a singleton instance.
     */
    private static MovieManager instance;
    public static MovieManager getInstance() {
        if (instance == null)
            instance = new MovieManager();
        return instance;
    }

    /**
     * Implemenent asynchronous tasks for loading images into an image view. The image is fetched
     * from an URL and decoded before being returned to the UI thread for insertion into the view.
     */
    private class ImageTask extends AsyncTask<String, Void, Long> {

        private ImageView view;
        private MovieManager manager;
        private Bitmap bitmap;

        public ImageTask(MovieManager manager, ImageView view) {
            this.manager = manager;
            this.view = view;
            this.bitmap = null;
        }

        public ImageView getImageView() {
            return view;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        protected Long doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                InputStream response = null;
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000 /* milliseconds */);
                connection.setConnectTimeout(15000 /* milliseconds */);
                response = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(response);
                manager.complete(this);
            } catch (MalformedURLException ex) {
                Log.e("ImageTask", "malformed URL [" + urls[0] + "]");
            } catch (IOException ex) {
                Log.e("ImageTask", "IO exception");
            }
            return new Long(0);
        }
    }
}
