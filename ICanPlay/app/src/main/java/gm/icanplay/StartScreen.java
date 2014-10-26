package gm.icanplay;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import gm.icanplay.model.Kid;


public class StartScreen extends Activity {
    private String settings_filename = getString(R.string.settings_filename);

    List<Kid> kids = new ArrayList<Kid>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

//        SharedPreferences settings = getSharedPreferences(settings_filename,0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putBoolean("isRegistered",true);
//        editor.putString("registeredName", T_name.getText().toString());

    }
}
