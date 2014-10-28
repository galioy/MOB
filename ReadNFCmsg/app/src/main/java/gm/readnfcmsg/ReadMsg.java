package gm.readnfcmsg;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ReadMsg extends Activity {
    TextView NFCMessageLabel = (TextView) findViewById(R.id.Text_NFCMessage);
    PendingIntent nfcPendingIntent;
    IntentFilter[] intentFiltersArray;
    String[][] techListsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_msg);

        int requestCode = 0;
        int flags = 0;

        Intent nfcIntent = new Intent(this, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        nfcPendingIntent = PendingIntent.getActivity(this, requestCode, nfcIntent, flags);

        //Create an  Intent Filter limited to the URI or MIME type to intercept TAG scans from.
        IntentFilter tagIntentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        tagIntentFilter.addDataScheme("http");
        tagIntentFilter.addDataAuthority("blog.radioactiveyak.com", null);
        intentFiltersArray = new IntentFilter[]{tagIntentFilter};

        //Create an array of technologies to handizzle.
        techListsArray = new String[][]{
                new String[]{
                        NfcF.class.getName()
                }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.read_msg, menu);
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

    @Override
    protected void onResume() {
        super.onResume();
        NfcAdapter.getDefaultAdapter(this).enableForegroundDispatch(this, nfcPendingIntent, intentFiltersArray, techListsArray);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String action = getIntent().getAction();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] messages = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            for (int i = 0; i < messages.length; i++) {
                NdefMessage message = (NdefMessage) messages[i];
                NdefRecord[] records = message.getRecords();

                for (int j = 0; j < records.length; j++) {
                    NdefRecord record = records[j];
                    NFCMessageLabel.setText(record.toString() + "\n\n");
                }
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            NFCMessageLabel.setText("NFC Chip discovered, but contains no data!");
        }
        else if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action))
        {
            NFCMessageLabel.setText("NFC Detected, unknown technologizzle!");
        }
    }
}
