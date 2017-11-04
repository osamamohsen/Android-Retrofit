package com.example.osama.retrofit.Common.Permission;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by osama on 11/4/2017.
 */

public class PermissionStorage {
    public static int KEY_STORAGE = 10001;
    public static void EnableStorage(final Activity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String message = "Do you want open Storage setting?";

        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.READ_EXTERNAL_STORAGE)){
                builder.setMessage(message)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface d, int id) {
                                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, KEY_STORAGE);
                                        d.dismiss();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface d, int id) {
                                        d.cancel();
                                    }
                                });
                builder.create().show();
            }//end check condition Write and Read External Storage
        }//Check is valid Permission
    }//end function open
    public static boolean IsEnable(final Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }//end function is open
}
