package gm.icanplay;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import gm.icanplay.model.ICanPlayController;

public class StartScreen extends Activity {
    private String preferences_name;
    private String feed;
    private ICanPlayController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if registered -> get child data, display kids list
//        else go to start screen
        setContentView(R.layout.activity_start_screen);


        //assign values to global variables
        preferences_name = getString(R.string.settings_filename);
        controller = new ICanPlayController();
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

        controller.registerKid(name, groupid, phone, feed, this);
    }

    public void canPlay(View view) throws IOException {
        EditText nameField = (EditText) findViewById(R.id.T_name);
        EditText groupidField = (EditText) findViewById(R.id.T_group_id);

        String name = nameField.getText().toString();
        String groupid = groupidField.getText().toString();

        controller.kidCanPlay(name, groupid, feed, this);
    }
}
