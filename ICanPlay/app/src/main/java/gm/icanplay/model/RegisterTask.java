package gm.icanplay.model;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import gm.icanplay.R;

/**
 * Created by Martin on 10/27/14.
 */
public class RegisterTask extends AsyncTask<String, Integer,String> {

    @Override
    protected String doInBackground(String... strings) {
        String name = strings[0];
        String group = strings[1];
        String phone = strings[2];
        String feed = strings[3];

        URL ws_url = null;
        try {

            ws_url = new URL(feed+"cmd=register&Groupid="+group+"&Name="+name+"&Telephone="+phone);
            URLConnection connection = ws_url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();

        } catch (MalformedURLException up) {
            up.printStackTrace();
        } catch (IOException up) {
            up.printStackTrace();
        }


        return null;
    }

}
