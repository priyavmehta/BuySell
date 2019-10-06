package com.example.buysell;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

public class Messaging {
    int SEND_SMS_PERMISSION_REQUEST_CODE=1;
    private Context context;

    public Messaging(Context context) {
        this.context = context;
    }

    public Messaging(){

    }

    public void message(String contact, String message_upload){
        if (checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(contact,null,message_upload,null,null);
            Toast.makeText(context,"Message sent",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(context,"come soon",Toast.LENGTH_LONG).show();
        }
    }

    public boolean checkPermission(String permission){

        int check= ContextCompat.checkSelfPermission(context,permission);
        return (check== PackageManager.PERMISSION_GRANTED);
    }
}
