package gm.icanplay;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class StartScreen extends Activity {
    private String preferences_name;
    private String feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        preferences_name = getString(R.string.settings_filename);
        feed = getString(R.string.feed);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void register(View view) throws IOException {
//        Локално съхранение на примитивни променливи.
//        http://developer.android.com/guide/topics/data/data-storage.html

        EditText nameField = (EditText) findViewById(R.id.T_name);
        EditText phoneField = (EditText) findViewById(R.id.T_phone);
        EditText groupidField = (EditText) findViewById(R.id.T_group_id);

        String name = nameField.getText().toString();
        String phone = phoneField.getText().toString();
        String groupid = groupidField.getText().toString();

//        SharedPreferences settings = getSharedPreferences(preferences_name,0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putBoolean("isRegistered",true);
//        editor.putString("registeredName", name);
//        editor.putString("registeredPhone", phone);
//        editor.putString("registeredSchool", groupid);
//        editor.commit();

        try
        {
            URL ws_url = new URL(feed+"cmd=register&Groupid="+groupid+"&Name="+name+"&Telephone="+phone);
            URLConnection connection = ws_url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
        }
        catch (MalformedURLException up)
        {
//            Log.d(TAG, "Malformed URL Exception. ahihihi");
            throw up;
        }
        catch (IOException up)
        {
            throw up;
        }
    }
}
