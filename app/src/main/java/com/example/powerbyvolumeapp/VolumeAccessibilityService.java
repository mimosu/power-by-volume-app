package com.example.powerbyvolumeapp;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.Context;
import android.app.admin.DevicePolicyManager;
import android.util.Log;
import android.view.KeyEvent;

public class VolumeAccessibilityService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        Log.d("VolumeService", "Accessibility Service connected");
    }

    @Override
    public boolean onKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN &&
                (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN || event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP)) {
            DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName admin = new ComponentName(this, MyDeviceAdminReceiver.class);
            if (dpm.isAdminActive(admin)) {
                dpm.lockNow();
                return true;
            }
        }
        return super.onKeyEvent(event);
    }

    @Override
    public void onInterrupt() {}
}
