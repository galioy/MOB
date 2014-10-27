package gm.icanplay.model;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import gm.icanplay.StartScreen;

/**
 * Created by milkncookiez on 10/27/14.
 */
public class CanPlayTask extends AsyncTask<String, String, String> {

    private StartScreen activity;

    public CanPlayTask(StartScreen _activity) {
        this.activity = _activity;
    }

    @Override
    protected String doInBackground(String... params) {
        String name = params[0];
        String group = params[1];
        String feed = params[2];
        URL ws_url = null;

        try {
            name = URLEncoder.encode(name, "utf-8");
            group = URLEncoder.encode(group, "utf-8");

            ws_url = new URL(feed + "cmd=canplay&Name=" + name + "&Groupid=" + group);
            URLConnection connection = ws_url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
