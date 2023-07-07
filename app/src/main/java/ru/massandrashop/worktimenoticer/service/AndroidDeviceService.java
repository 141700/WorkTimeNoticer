package ru.massandrashop.worktimenoticer.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

public class AndroidDeviceService {

    @SuppressLint("HardwareIds")
    public static String getAndroidDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

}
