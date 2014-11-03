package gm.readnfcmsg;

import android.app.Activity;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.Charset;


public class ReadMsg extends Activity {
    private TextView T_nfc_read;
    private EditText T_nfc_write;
    private Tag detectedTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_msg);

        T_nfc_read = (TextView) findViewById(R.id.T_nfc_read);
        T_nfc_write = (EditText) findViewById(R.id.T_nfc_write);
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
        discoverNfc();
    }

    protected void discoverNfc() {
        NfcAdapter mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Intent intent = getIntent();
        boolean tagIntentAvailable = false;
        if (intent != null) {
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
                T_nfc_read.append("ACTION_TAG_DISCOVERED\n");
                T_nfc_read.append("-----------------\n");
                tagIntentAvailable = true;
            } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
                T_nfc_read.append("ACTION_TECH_DISCOVERED\n");
                T_nfc_read.append("-----------------\n");
                tagIntentAvailable = true;
            } else if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
                T_nfc_read.append("ACTION_NDEF_DISCOVERED\n");
                T_nfc_read.append("-----------------\n");
                tagIntentAvailable = true;
            } else {
                T_nfc_read.append("Unknown tag format (" + intent.getAction() + ")\n");
            }
        } else {
            T_nfc_read.append("no tag");
        }
        if (tagIntentAvailable) {
            detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        } else {
            detectedTag = null;
        }
    }

    public void readNfc(View view) {
        if (detectedTag != null) {
            Ndef ndef = Ndef.get(detectedTag);
            if (ndef != null) {
                try {
                    ndef.connect();
                    NdefMessage ndefMessage = ndef.getNdefMessage();
                    if (ndefMessage != null) {
                        NdefRecord[] records = ndefMessage.getRecords();
                        if (records != null) {
                            for (int i = 0; i < records.length; i++)
                            {
                                NdefRecord current = records[i];
                                byte[] payLoad = current.getPayload();
                                if (payLoad != null)
                                {
                                    for (int j = 0; j < payLoad.length; j++)
                                    {
                                        T_nfc_read.append("" + (char)payLoad[j]);
                                    }
                                }
                                else
                                {
                                    T_nfc_read.append("There is no message on the tag...\n");
                                }
                            }
                        } else {
                            T_nfc_read.append("No NDEF records\n");
                        }
                    } else {
                        T_nfc_read.append("No NDEF messages on tag\n");
                    }
                } catch (IOException e) {
                    T_nfc_read.append("Unable to connect to tag\n");
                } catch (FormatException e) {
                    T_nfc_read.append("Unknown tag message format\n");
                } finally {
                    try {
                        ndef.close();
                    } catch (IOException e) {
                        T_nfc_read.append("Unable to close tag connection\n");
                    }
                }
            } else {
                T_nfc_read.append("Tag not formatted?\n");
            }
        }
        else
        {
            T_nfc_read.append("No tag detected\n");
        }
    }

    public void writeNfc(View view) {
        if (detectedTag != null) {
            String inputMsg = T_nfc_write.getText().toString();
            NdefMessage message = getTagAsNdef(inputMsg);
            Ndef ndef = Ndef.get(detectedTag);
            if (ndef != null) {
                try {
                    ndef.connect();
                    if (ndef.isWritable()) {
                        int size = message.toByteArray().length;
                        if (ndef.getMaxSize() > size) {
                            try {
                                ndef.writeNdefMessage(message);
                                T_nfc_read.append("\n");
                                Toast.makeText(this, "Writing succeeded", Toast.LENGTH_LONG).show();
                            } catch (FormatException e) {
                                Toast.makeText(this, "Failed to write (FormatException)", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            String mess = "Tag capacity is " + ndef.getMaxSize() + " bytes, message is " + size
                                    + " bytes.\n";
                            Toast.makeText(this, mess, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        T_nfc_read.append("Tag does not seem to be writable\n");
                    }

                } catch (IOException e) {
                    T_nfc_read.append("Cannot connect to tag during write\n");
                } finally {
                    try {
                        ndef.close();
                    } catch (IOException e) {
                        T_nfc_read.append("Cannot close tag during write\n");
                    }
                }
            } else {
                T_nfc_read.append("Cannot write to tag - needs formatting?\n");
            }
        } else {
            T_nfc_read.append("No tag detected\n");
        }
    }

    protected NdefMessage getTagAsNdef(String inputMsg) {
        String uniqueId = inputMsg;
        byte[] uriField = uniqueId.getBytes(Charset.forName("US-ASCII"));
        byte[] payload = new byte[uriField.length + 1];              //add 1 for the URI Prefix
        payload[0] = 0x01;                                        //prefixes http://www. to the URI

        System.arraycopy(uriField, 0, payload, 1, uriField.length);  //appends URI to payload
        NdefRecord rtdUriRecord = new NdefRecord(
                NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_URI, new byte[0], payload);

        return new NdefMessage(new NdefRecord[]{
                rtdUriRecord});
    }
}
