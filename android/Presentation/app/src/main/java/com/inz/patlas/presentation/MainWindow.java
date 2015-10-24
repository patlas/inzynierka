package com.inz.patlas.presentation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainWindow extends AppCompatActivity {

    public TextView         addr_tv = null;
    public TextView         mask_tv = null;
    public TextView         net_tv = null;
    public Button           open_btn = null;
    public ProgressDialog   progress_dialog = null;
    public String           fName = null;

    private boolean IS_CONNECTED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        //TextView tv = (TextView) findViewById(R.id.wifiInfo);

        addr_tv = (TextView) findViewById(R.id.addr_tv);
        mask_tv = (TextView) findViewById(R.id.mask_tv);
        net_tv = (TextView) findViewById(R.id.net_tv);
        open_btn = (Button) findViewById(R.id.open_btn);

        open_btn.getBackground().setAlpha(150);

        if(checkConnectionStatus(this) != 0)
        {
            Toast wifi_info = Toast.makeText(this, R.string.toast_no_wifi, Toast.LENGTH_LONG);
            wifi_info.setGravity(Gravity.BOTTOM, 0, 0);
            wifi_info.show();
            IS_CONNECTED = false;
        }
        else
        {
            IS_CONNECTED = true;
        }

        updateInfo();


        open_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IS_CONNECTED == false){
                   // Toast wifi_info = Toast.makeText(this, R.string.toast_no_wifi, Toast.LENGTH_LONG);

                }
                else
                    openFileClicked(v);
            }
        });

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        if(checkConnectionStatus(this) != 0)
        {
            Toast wifi_info = Toast.makeText(this, R.string.toast_no_wifi, Toast.LENGTH_LONG);
            wifi_info.setGravity(Gravity.BOTTOM, 0, 0);
            wifi_info.show();
            IS_CONNECTED = false;
        }
        else
        {
            IS_CONNECTED = true;
        }
        updateInfo();

    }


    @Override
    protected void  onStop()
    {
        super.onStop();

    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        disconnectWifi(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                fName = data.getStringExtra("file_path");
                Toast.makeText(this, "File name is: " + fName , Toast.LENGTH_LONG).show();
            }
        }
    }


    private void updateInfo()
    {
        String[] info = getConnectionInfo(this);

        addr_tv.setText(info[0]);
        mask_tv.setText(info[1]);
        net_tv.setText(info[2]);
    }


    private String[] getConnectionInfo(Context context)
    {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        DhcpInfo dhcp = wifiManager.getDhcpInfo();

        if(checkConnectionStatus(this)!=0)
        {
            String[] ret = {"NOT CONNECTED","NOT CONNECTED","NOT CONNECTED"};
            return ret;
        }

        IS_CONNECTED = true;

        String hostIpAddr;
        String networkMask;
        String serverAddr;

        int hostIp = dhcp.ipAddress;
        hostIpAddr = intIPtoString(hostIp);

        int netMask = dhcp.netmask;
        networkMask = intIPtoString(netMask);

        int servIp = hostIp & netMask;
        serverAddr = intIPtoString(servIp);

        String[] ret = {hostIpAddr,networkMask,serverAddr};

        return ret;
    }


    public int checkConnectionStatus(Context context)
    {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);

        if(wifiManager.isWifiEnabled() != true)
            return -1;

        if(wifiManager.getConnectionInfo().getSupplicantState() != SupplicantState.COMPLETED)
            return -2;

        return 0;
    }

    public static String intIPtoString(int ip)
    {
        return String.format("%d.%d.%d.%d",(ip & 0xff),(ip >> 8 & 0xff),(ip >> 16 & 0xff), ((ip >> 24) & 0xff));
    }


    public void screenTapper(View view)
    {
        if(IS_CONNECTED == false)
        {
            startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
        }
    }

    public boolean disconnectWifi(Context context)
    {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        /*return */wifiManager.disconnect();
        return wifiManager.setWifiEnabled(false);
    }

    public void refreshWifiTapper(View v)
    {
        if(checkConnectionStatus(this) != 0)
            screenTapper(v);
        else
            updateInfo();
    }

    private void openFileClicked(View v)
    {
        Intent myIntent = new Intent(v.getContext(), ListFileActivity.class);
        startActivityForResult(myIntent,1);
       // progress_dialog
    }

}
