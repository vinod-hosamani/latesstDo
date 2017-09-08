package com.example.client1.vndtodo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by client1 on 9/8/2017.
 */

public class Connectivity
{
    public static boolean isNetworkConnected(Context context)
    {
        ConnectivityManager conn = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
