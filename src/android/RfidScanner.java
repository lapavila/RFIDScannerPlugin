package com.eficid.cordova.plugin;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by avila on 28/10/13.
 */
public class RfidScanner extends CordovaPlugin {
    public static final int SCAN_CODE = 0;
    public static final int RADAR_CODE = 1;
    private static final String SCAN_INTENT = "eficid.intent.action.RFID_SCAN";
    private static final String RADAR_INTENT = "eficid.intent.action.RFID_RADAR";
    private static final String CANCELLED = "cancelled";
    private static final String LOG_TAG = "RfidScanner";

    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if (action.equals("scan")) {
            scan();
        } else if (action.equals("radar")) {
        	radar(args);
        } else {
            return false;
        }
        return true;
    }

    public void scan() {
        Intent intentScan = new Intent();
        intentScan.setAction(SCAN_INTENT);
        this.cordova.startActivityForResult(this, intentScan, SCAN_CODE);
    }

    public void radar(JSONArray args) {
        Intent intentScan = new Intent();
        intentScan.setAction(RADAR_INTENT);
        ArrayList<String> tagList = new ArrayList<String>();
        tagList.clear();
        try {
            for (int i = 0; i < args.length(); i++) {
                tagList.add(args.get(i).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        intentScan.putStringArrayListExtra("EPC_LIST", tagList);
        this.cordova.startActivityForResult(this, intentScan, RADAR_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	switch(requestCode) {
       		case SCAN_CODE :
	            if (resultCode == Activity.RESULT_OK) {
	                JSONObject obj = new JSONObject();
	                try {
	                    ArrayList<String> result = intent.getStringArrayListExtra("RESULT");
	                    JSONArray jsArray = new JSONArray(result);
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
	            break;
		    case RADAR_CODE :
		    	if (resultCode == Activity.RESULT_CANCELED) {
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
	            break;
	    }
    }
}
