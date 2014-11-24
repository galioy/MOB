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
    TextView axisX_field;
    TextView axisY_field;
    TextView axisZ_field;
    private SensorManager sensorManager;
    private Sensor sensor;
    private int sensorType;

    float rotation[] = null;
    float I[] = null;
    float accelaration[] = new float[3];
    float magnetics[] = new float[3];
    float values[] = new float[3];
    float azimuth, pitch, roll;

//    final float nanosecPerSecond = 1.0f / 1000000000.0f;
//    long lastTime = 0;
//    float[] rotationVector = new float[4];
//    float norm_of_angle = 0;
//
//    float degreesX=0;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_nivelir);
        axisX_field = (TextView) findViewById (R.id.T_x_axis);
        axisY_field = (TextView) findViewById (R.id.T_y_axis);
        axisZ_field = (TextView) findViewById (R.id.T_z_axis);

        SensorEventListener sensorListener = new SensorEventListener () {
            @Override
            public void onSensorChanged (SensorEvent sensorEvent) {
//                if (lastTime != 0) {
//                    final float dt = (sensorEvent.timestamp - lastTime) * nanosecPerSecond;
//                    rotationVector = sensorEvent.values.clone ();
//                    //normalize the values
//                    norm_of_angle = (float)Math.sqrt(rotationVector[0]* rotationVector[0] +
//                            rotationVector[1]* rotationVector[1] + rotationVector[2]* rotationVector[2]);
//                    rotationVector[0] /= norm_of_angle;
//                    rotationVector[1] /= norm_of_angle;
//                    rotationVector[2] /= norm_of_angle;
//
//                }
//                lastTime = sensorEvent.timestamp;
//                SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector);
//                degreeChangeX = Math.toDegrees (getInclination(rotationMatrix));
//
//                degreesX += degreeChangeX;

                switch (sensorEvent.sensor.getType ()) {
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        magnetics = sensorEvent.values.clone ();
                        break;
                    case Sensor.TYPE_ACCELEROMETER:
                        accelaration = sensorEvent.values.clone ();
                        break;
                }
                if (magnetics != null && accelaration != null) {
                    rotation = new float[9];
                    I = new float[9];
                    SensorManager.getRotationMatrix (rotation, I, accelaration, magnetics);

                    float[] outR = new float[9];
                    SensorManager.remapCoordinateSystem (rotation, SensorManager.AXIS_X, SensorManager.AXIS_Z, outR);
                    SensorManager.getOrientation (outR, values);

                    azimuth = Math.round (values[0] * 57.2957795f); //looks like we don't need this one
                    pitch = Math.round (values[1] * 57.2957795f);
                    roll = Math.round (values[2] * 57.2957795f);

                    axisX_field.setText ("X: " + Float.toString (roll));
                    axisY_field.setText ("Y: " + Float.toString (pitch));
                    axisZ_field.setText ("Z: " + Float.toString (azimuth));

                    magnetics = null; //retrigger the loop when things are repopulated
                    accelaration = null; ////retrigger the loop when things are repopulated
                }

//                axisX_field.setText ("X: " + mRotationMatrix.toString ());
//                axisY_field.setText ("Y: " + Double.toString (Math.round (Math.toDegrees (Math.acos (rotationVector[2])))));
//                axisZ_field.setText ("Z: " + Double.toString (Math.round(Math.toDegrees(Math.atan2(rotationVector[0], rotationVector[1])))));
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
