package com.tennissetapp.message;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.tennissetapp.Constants;
import com.tennissetapp.form.UpdateTokenForm;
import com.tennissetapp.rest.Client;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    private static final String TAG = GcmBroadcastReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {

        //by adding <action android:name="com.google.android.c2dm.intent.REGISTRATION" />
        //to the manifest, the registration ID will become directly in the intent's extras
        //registration_id attribute
        if(intent.getExtras() != null){
            String regId = (String)intent.getExtras().get(Constants.GCM.REGISTRATION_ID);
            Log.d(TAG, getClass().getSimpleName()+": **********onReceive0 " + regId);
            if(regId != null){
                final SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(
                        Constants.AttributeKeys.GCM_REGISTRATION,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(Constants.GCM.REGISTRATION_ID, regId);
                editor.commit();

                Client.getInstance().updateToken(new UpdateTokenForm("GCM",regId));
            }
        }
        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
