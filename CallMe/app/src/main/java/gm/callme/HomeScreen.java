package gm.callme;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.ParseException;


public class HomeScreen extends Activity {

    public SipManager sipManager = null;
    public SipProfile sipProfile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        if (sipManager == null) {
            sipManager = SipManager.newInstance(this);
        }
        try {
            SipProfile.Builder sipBuilder = new SipProfile.Builder("6005", "10.152.128.145");
            sipBuilder.setPassword("unsecurepassword");
            sipBuilder.build();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.setAction("android.SipDemo.INCOMING_CALL");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, Intent.FILL_IN_DATA);
        try {
            sipManager.open(sipProfile, pendingIntent, null);

            sipManager.setRegistrationListener(sipProfile.getUriString(), new SipRegistrationListener() {
                @Override
                public void onRegistering(String s) {
                    System.out.print("Registering with SIP server...");
                }

                @Override
                public void onRegistrationDone(String s, long l) {
                    System.out.print("Done registering.");
                }

                @Override
                public void onRegistrationFailed(String s, int i, String s2) {
                    System.out.print("Registration failed...");
                }
            });
        } catch (SipException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
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

    /**
     * Handles the process that happens upon click of the
     * "So, call me maybe" button on the home screen.
     * @param view
     */
    public void callMe(TextView view) {

    }

    public void unregister(TextView view) {
        if (sipManager == null) {
            return;
        }

        try {
            if (sipManager != null) {
                sipManager.close(sipProfile.getUriString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Failed to close local profile.");
        }
    }
}
