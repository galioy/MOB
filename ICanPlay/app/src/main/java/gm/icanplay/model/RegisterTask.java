package gm.icanplay.model;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import gm.icanplay.StartScreen;

/**
 * Created by Martin on 10/27/14.
 */
public class RegisterTask extends AsyncTask<String, String, String> {

    private ProgressDialog dialog;
    private StartScreen activity;

    public RegisterTask(StartScreen _activity) {
        this.activity = _activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String name = strings[0];
        String group = strings[1];
        String phone = strings[2];
        String feed = strings[3];
        URL ws_url = null;

        try {
            name = URLEncoder.encode(name, "utf-8");
            group = URLEncoder.encode(group, "utf-8");
            phone = URLEncoder.encode(phone, "utf-8");

            ws_url = new URL(feed + "cmd=register&Groupid=" + group + "&Name=" + name + "&Telephone=" + phone);
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

    public void RecordAndAlignTask(StartScreen activity) {
        dialog = new ProgressDialog(activity);
    }

    protected void onPostExecute(Void result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
