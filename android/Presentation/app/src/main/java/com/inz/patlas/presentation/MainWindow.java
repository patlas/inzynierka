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

import com.inz.patlas.presentation.stream.ControllCommands;
import com.inz.patlas.presentation.stream.FileStreamer;
import com.inz.patlas.presentation.stream.Messanger;
import com.inz.patlas.presentation.stream.QueueStruct;

import java.io.File;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class MainWindow extends AppCompatActivity {

    public TextView         addr_tv = null;
    public TextView         mask_tv = null;
    public TextView         net_tv = null;
    public Button           open_btn = null;
    public ProgressDialog   progress_dialog = null;
    public String           fName = null;
    public static Messanger        messanger = null;

    public LinkedBlockingQueue recQueue = new LinkedBlockingQueue<>();
    public LinkedBlockingQueue<QueueStruct>  transQueue = new LinkedBlockingQueue<>();

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
                if(!IS_CONNECTED){
                    Toast.makeText(MainWindow.this, R.string.btn_not_connected, Toast.LENGTH_LONG).show();

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

                //TODO - start stream file
                File fd = new File(fName);
                sendFile(messanger, fd);

                progress_dialog = ProgressDialog.show(this, "Streaming...", "File +name+ is streaming.", true);

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
        messanger = new Messanger(null, recQueue, transQueue);
        (new Thread(messanger)).start();

        Intent myIntent = new Intent(v.getContext(), ListFileActivity.class);
        startActivityForResult(myIntent, 1);


//        Intent myIntent = new Intent(v.getContext(), PPTControll.class);
//        startActivityForResult(myIntent, 1);

    }




    private /*boolean*/void sendFile(Messanger m, File fd){
        m.sendCommand(ControllCommands.F_STREAM);
        if(m.recvCommand().equalsIgnoreCase(ControllCommands.GET_SIZE)){
            System.out.println("Ask for size");
            m.sendCommand(Integer.toString((int)fd.length()));

        }

        if(m.recvCommand().equalsIgnoreCase(ControllCommands.GET_HASH)){
            System.out.println("Ask for hash");
            m.sendCommand(FileStreamer.getHash(fd.getAbsolutePath()));
        }

        if(m.recvCommand().equalsIgnoreCase(ControllCommands.GET_TYPE)){
            System.out.println("Ask for type");
            String[] ext = fd.getAbsolutePath().toLowerCase().split("\\.");
            String extention = ext[ext.length-1];
            m.sendCommand(extention);

        }

        m.streamFile(fd);
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(messanger.streamDone != true){};
                messanger.streamDone = false;
                messanger.sendCommand(ControllCommands.F_DONE);
                String stramSucces = messanger.recvCommand();
                System.out.println("Stream done with: " + stramSucces);

                if(stramSucces.equalsIgnoreCase(ControllCommands.F_RECEIVED))
                {
                    //return true;
                }
                else if(stramSucces.equalsIgnoreCase(ControllCommands.F_ERROR))
                {
                    //return false;
                }
                else
                {
                    //return false;
                }

                progress_dialog.dismiss();
                Intent ppt_control_intent = new Intent(MainWindow.this, PPTControll.class);
                startActivity(ppt_control_intent);
                //ppt_control_intent.putExtra("Messanger_obj", messanger);


            }
        }).start();

    }

}
