package com.rodolfo.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WifiActualActivity extends AppCompatActivity {

    WifiManager mainWifiObj;
    BroadcastReceiver wifiReciever;
    ListView list;
    String wifis[];
    int pauseAndResume;
    //TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_actual);
        pauseAndResume = 1;

        //list=getListView();
        mainWifiObj = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiReciever = new WifiActualActivity.WifiScanReceiver();
        registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        list = (ListView)findViewById(R.id.wifiActualList);
        //text = (TextView)findViewById(R.id.textView2);
        View reload = (View)findViewById(R.id.reloadButton);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Iniciando Scan...", Toast.LENGTH_SHORT).show();
                mainWifiObj.startScan();
                pauseAndResume = 1;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        pauseAndResume = 0;
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        pauseAndResume = 1;
    }

    private class WifiScanReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
            if (pauseAndResume == 1) {
                Toast.makeText(getApplicationContext(), "actualizando", Toast.LENGTH_SHORT).show();
                WifiInfo wifiScan = mainWifiObj.getConnectionInfo();
                wifis = new String[9];
                String BSSID = wifiScan.getBSSID();
                //text.setText(BSSID);
                wifis[0] = BSSID;
                String freq = String.valueOf(wifiScan.getFrequency());
                wifis[1] = freq;
                String ipAddress = Formatter.formatIpAddress(wifiScan.getIpAddress());
                wifis[2] = ipAddress;
                String linkSpeed = String.valueOf(wifiScan.getLinkSpeed());
                wifis[3] = linkSpeed;
                String MACAddress = wifiScan.getMacAddress();
                wifis[4] = MACAddress;
                String netID = String.valueOf(wifiScan.getNetworkId());
                wifis[5] = netID;
                String RSSI = String.valueOf(wifiScan.getRssi());
                wifis[6] = RSSI;
                String SSID = wifiScan.getSSID();
                wifis[7] = SSID;
                String string = wifiScan.toString();
                wifis[8] = string;

                final ArrayList<String> lista = new ArrayList<String>();
                for (int i = 0; i < wifis.length; ++i) {
                    lista.add(wifis[i]);
                }

                final WifiActualActivity.StableArrayAdapter adapter = new WifiActualActivity.StableArrayAdapter(getApplicationContext(),
                        R.layout.my_item, lista);
                list.setAdapter(adapter);
                pauseAndResume = 0;
            }
        }
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
