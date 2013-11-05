package com.eficid.cordova.plugin;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by avila on 28/10/13.
 */
public class RfidScanner extends CordovaPlugin {

    public static final int REQUEST_CODE = 0x0ba7c0de;
    private static final String SCAN_INTENT = "android.intent.action.RFID";
    private static final String CANCELLED = "cancelled";
    private static final String LOG_TAG = "RfidScanner";

    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if (action.equals("scan")) {
            scan();
        } else {
            return false;
        }
        return true;
    }

    public void scan() {
        Intent intentScan = new Intent();
        intentScan.setAction(SCAN_INTENT);
        intentScan.addCategory(Intent.CATEGORY_DEFAULT);

        this.cordova.startActivityForResult(this, intentScan, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                JSONObject obj = new JSONObject();
                try {
                    String result = intent.getStringExtra("result");
                    JSONArray jsArray = new JSONArray();
                    for (String tag : result.split(";")) {
                        jsArray.put(tag);
                    }
                    obj.put(CANCELLED, false);
                    obj.put("tags", jsArray);
                } catch (JSONException e) {
                    Log.d(LOG_TAG, "This should never happen");
                }

                this.callbackContext.success(obj);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put(CANCELLED, true);
                    obj.put("tags", "");
                } catch (JSONException e) {
                    Log.d(LOG_TAG, "This should never happen");
                }
                this.callbackContext.success(obj);
            } else {
                this.callbackContext.error("Unexpected error");
            }
        }
    }
}
