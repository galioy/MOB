package gm.nivelir;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Nivelir extends Activity {

    final float nanosecPerSecond = 1.0f / 1000000000.0f;
    private long lastTime = 0;
    final float[] angle = new float[3];
    TextView axisX_field;
    TextView axisY_field;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_nivelir);

        axisX_field = (TextView)findViewById (R.id.T_x_axis);
        axisY_field = (TextView)findViewById (R.id.T_y_axis);

        SensorEventListener gyroListener = new SensorEventListener () {
            @Override
            public void onSensorChanged (SensorEvent sensorEvent) {

            }

            @Override
            public void onAccuracyChanged (Sensor sensor, int i) {

            }
        };

        SensorManager sensorManager = (SensorManager) getSystemService (Context.SENSOR_SERVICE);
        int sensorType = Sensor.TYPE_GYROSCOPE;
        sensorManager.registerListener (gyroListener,
                sensorManager.getDefaultSensor (sensorType),
                SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.menu_nivelir, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected (item);
    }
}
