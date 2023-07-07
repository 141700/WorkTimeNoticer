package ru.massandrashop.worktimenoticer.service.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastDemonstrator {

    public static void showToast(Context context, int preferenceString) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable runToastShow = () -> Toast.makeText(context, context.getString(preferenceString), Toast.LENGTH_LONG).show();
        mainHandler.post(runToastShow);
    }
}
