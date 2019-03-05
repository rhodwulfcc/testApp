package com.rodolfo.testapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.sip.SipSession;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.net.wifi.WifiManager.SCAN_RESULTS_AVAILABLE_ACTION;

public class DatosWifiActivity extends AppCompatActivity {

    WifiManager mainWifiObj;
    BroadcastReceiver wifiReciever;
    ListView list;
    String wifis[];
    int pauseAndResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_wifi);
        pauseAndResume = 1;

        //list=getListView();
        mainWifiObj = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiReciever = new WifiScanReceiver();
        registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        list = (ListView)findViewById(R.id.listWifi);
        mainWifiObj.startScan();
        Button escanear = (Button)findViewById(R.id.iniciarScan);
        escanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mainWifiObj.is5GHzBandSupported()) {
//                    Toast.makeText(getApplicationContext(), "Soporta 5GHz", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "No soporta 5GHz", Toast.LENGTH_SHORT).show();
//                }
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
                Toast.makeText(getApplicationContext(), "inicia broadcast", Toast.LENGTH_SHORT).show();
                List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
                wifis = new String[wifiScanList.size()];
                for (int i = 0; i < wifiScanList.size(); i++) {
                    String info = ((wifiScanList.get(i)).toString());
                    wifis[i] = info;
                }

                final ArrayList<String> lista = new ArrayList<String>();
                for (int i = 0; i < wifis.length; ++i) {
                    lista.add(wifis[i]);
                }

                final StableArrayAdapter adapter = new StableArrayAdapter(getApplicationContext(),
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

