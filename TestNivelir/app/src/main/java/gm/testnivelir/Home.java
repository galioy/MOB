package gm.testnivelir;

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


public class Home extends Activity {
    TextView axisX_field;
    TextView axisY_field;
    TextView axisZ_field;
    private int sensorType;

    float rotation[] = null;
    float tilt[] = null;
    float accelarationStuff[] = new float[3];
    float magneticStuff[] = new float[3];
    double azimuth, pitch, roll;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);
        axisX_field = (TextView) findViewById (R.id.T_x_axis);
        axisY_field = (TextView) findViewById (R.id.T_y_axis);
        axisZ_field = (TextView) findViewById (R.id.T_z_axis);

        SensorEventListener sensorListener = new SensorEventListener () {

            @Override
            public void onSensorChanged (SensorEvent event) {
                if (event.sensor.getType () == Sensor.TYPE_ACCELEROMETER)
                    accelarationStuff = event.values.clone ();
                if (event.sensor.getType () == Sensor.TYPE_MAGNETIC_FIELD)
                    magneticStuff = event.values.clone ();
                if (accelarationStuff != null && magneticStuff != null) {
                    rotation = new float[9];
                    tilt = new float[9];
                    boolean success = SensorManager.getRotationMatrix (rotation, tilt, accelarationStuff, magneticStuff);
                    if (success) {
                        float orientation[] = new float[3];
                        SensorManager.getOrientation (rotation, orientation);
                        SensorManager.remapCoordinateSystem (rotation, SensorManager.AXIS_X, SensorManager.AXIS_Y, orientation);

//                        azimuth = Math.round (Math.toDegrees (orientation[0])); // orientation contains: azimuth, pitch and roll
                        pitch = Math.round (Math.toDegrees (orientation[1]));
                        roll = Math.round (Math.toDegrees (orientation[2]));
                    }
                    // clean the data holders
                    rotation = null;
                    tilt = null;
                }

                axisX_field.setText ("X: " + Double.toString (roll));
                axisY_field.setText ("X: " + Double.toString (pitch));
//                axisZ_field.setText ("Z: " + Double.toString (azimuth));
            }

            @Override
            public void onAccuracyChanged (Sensor sensor, int i) {

            }
        };

        SensorManager sensorManager = (SensorManager) getSystemService (Context.SENSOR_SERVICE);
        sensorType = Sensor.TYPE_ACCELEROMETER;
        sensorManager.registerListener (sensorListener,
                sensorManager.getDefaultSensor (sensorType),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorType = Sensor.TYPE_MAGNETIC_FIELD;
        sensorManager.registerListener (sensorListener,
                sensorManager.getDefaultSensor (sensorType),
                SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.menu_home, menu);
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
