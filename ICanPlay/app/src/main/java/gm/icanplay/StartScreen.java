package gm.icanplay;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import org.apache.http.*;
import org.apache.http.client.HttpClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import gm.icanplay.model.Kid;


public class StartScreen extends Activity {
    private String settings_filename = getString(R.string.settings_filename);
    EditText name = (EditText) findViewById(R.id.T_name);
    EditText phone = (EditText) findViewById(R.id.T_phone);
    EditText groupid = (EditText) findViewById(R.id.T_group_id);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if registered -> get child data, display kids list
//        else go to start screen


        setContentView(R.layout.activity_start_screen);
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

    public void register(View view) {
//        Локално съхранение на примитивни променливи.
//        http://developer.android.com/guide/topics/data/data-storage.html

        SharedPreferences settings = getSharedPreferences(settings_filename,0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isRegistered",true);
        editor.putString("registeredName", name.getText().toString());
        editor.putString("registeredPhone", phone.getText().toString());
        editor.putString("registeredSchool", groupid.getText().toString());
        editor.commit();

//        RestClient client = new RestClient();
    }
}
