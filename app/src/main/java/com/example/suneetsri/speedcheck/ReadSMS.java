package com.example.suneetsri.speedcheck;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by MARK_42 on 07-09-2017.
 */

public class ReadSMS extends BroadcastReceiver {
    private static final String SMS_SENT_INTENT_FILTER = "";
    private static final String SMS_DELIVERED_INTENT_FILTER="";

    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber;
        String senderNum="";
        boolean autoreply=true;

        //Reading the incoming message START
        final Bundle bundle = intent.getExtras();
        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
        //Reading incoming SMS END
        //Auto-sending SMS to the Sender START
        if(autoreply){
            String message = "hey, bro wassup";

            String phnNo = senderNum; //preferable use complete international number

            PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(
                    SMS_SENT_INTENT_FILTER), 0);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(
                    SMS_DELIVERED_INTENT_FILTER), 0);

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phnNo, null, message, sentPI, deliveredPI);
            Log.d("Receiver : ","Messege Sent");
        }
        //Auto-reply END
    }
}
