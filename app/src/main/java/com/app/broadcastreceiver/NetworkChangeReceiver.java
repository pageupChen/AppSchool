package com.app.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/10/31.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    private Context mycontext;

    public NetworkChangeReceiver(Context mycontext){
        this.mycontext=mycontext;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectionManager= (ConnectivityManager) mycontext.getSystemService(mycontext.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectionManager.getActiveNetworkInfo();
        if(networkInfo==null){
            Toast.makeText(mycontext,"网络已断开",Toast.LENGTH_SHORT).show();
        }

    }
}
