package gm.BluetoothAssignment;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainBluetooth extends Activity {
    private BluetoothAdapter btAdapter;
    private BroadcastReceiver mReceiver;
    private BluetoothDevice mDevice;

    @Override
    public View findViewById(int id) {
        return super.findViewById(id);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (btAdapter.isDiscovering()) {
            btAdapter.cancelDiscovery();
        }
        this.unregisterReceiver(mReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bluetooth);
        Button button = (Button) findViewById(R.id.button1);

        OnClickListener mCorkyListener = new OnClickListener() {
            public void onClick(View v) {
                System.out.println("enableBluetooth");
                enableBT();
            }
        };
        button.setOnClickListener(mCorkyListener);
    }

    private void enableBT() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter.isEnabled() == false) {
            btAdapter.enable();
        }
        Set<BluetoothDevice> myDevice = btAdapter.getBondedDevices();

        mDevice = btAdapter.getRemoteDevice("00:12:6F:27:A0:6C");

		System.out.println("Try to get a socket");

        BluetoothSocket socket;

        try {
            socket = mDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));

            System.out.println("Got a socket");
            Thread thr = new ConnectThread(socket);
            btAdapter.cancelDiscovery();
            thr.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_bluetooth, menu);
        return true;
    }

    private static class ConnectThread extends Thread {
        private BluetoothSocket socket;

        public ConnectThread(BluetoothSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connecting..");
            try {

                socket.connect();
                System.out.println("connection established");
                try {
                    OutputStream output = socket.getOutputStream();
                    output.write('X');
                    output.flush();
                    Thread.sleep(5000);
                    socket.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("DESTROYED");

            } catch (IOException e1) {
                System.out.println(e1.getMessage());
            }
        }
    }
}
