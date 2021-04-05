package org.apache.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;
import com.zendrive.sdk.*;
import android.widget.Toast;



public class FairMaticPlugin extends CordovaPlugin {

    
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
       

        try {

        } catch (Exception e) {
          
        }
    }

    @Override
    public boolean execute(
            String action,
            JSONArray args,
            CallbackContext callbackContext
    ) throws JSONException {
        if(action.equals("nativeToast")){
            nativeToast();
        }
        if(action.equals("setupZendrive")){
            zenDriveSetup();
            Log.d("TAG", String.format("ZendriveSDK is Setup = %s", Zendrive.isSDKSetup(getApplicationContext()) ));
            ZendriveInsurance.stopPeriod(getApplicationContext(), insuranceCallback);
            Log.d("tag", getZenDriveConfig().toJson().toString());
        }
        return false;
    }
    public void nativeToast(){
        Toast.makeText(
                webView.getContext(),
                "Hello World Cordova Plugin",
                Toast.LENGTH_SHORT)
                .show();
    }

    public ZendriveConfiguration getZenDriveConfig(){
        final ZendriveConfiguration zendriveConfiguration =
                new ZendriveConfiguration("qIT2nWj1hLNwoa4AcnfQrKNOJ8fS0xz3", "D004", ZendriveDriveDetectionMode.INSURANCE);
        Log.d("zenDriveConfig", zendriveConfiguration.toString());
        return zendriveConfiguration;
    }


    public boolean zenDriveSetup(){
        Zendrive.setup(
                this.getApplicationContext(),
                this.getZenDriveConfig(),
                MyZendriveBroadcastReceiver.class, //rename to your custom class
                MyZendriveNotificationProvider.class, //rename to your custom class
                new ZendriveOperationCallback() {
                    @Override
                    public void onCompletion(ZendriveOperationResult result) {
                        if (result.isSuccess()) {
                            Log.d("TAG", "ZendriveSDK setup success");
                        } else {
                            Log.d("TAG", String.format("ZendriveSDK setup failed %s", result.getErrorCode().toString()));
                        }
                    }
                }
        );

        return true;


    }

    ZendriveOperationCallback insuranceCallback = new ZendriveOperationCallback() {
        @Override
        public void onCompletion(ZendriveOperationResult zendriveOperationResult) {
            if (!zendriveOperationResult.isSuccess()) {
                Log.d("ZendriveSDKDebug", "Insurance period switch failed, error: " + zendriveOperationResult.getErrorCode().name());
            }
            else   {
                Log.d("tag", "Zendrive Insurace Callback Success");
            }
        }
    };

}
