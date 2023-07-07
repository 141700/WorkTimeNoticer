package ru.massandrashop.worktimenoticer.model;

import android.Manifest;
import android.os.Build;
import ru.massandrashop.worktimenoticer.R;

public enum IdType {

    UNDEFINED(null, 0),
    BLUETOOTH(Build.VERSION.SDK_INT < Build.VERSION_CODES.S
            ? Manifest.permission.BLUETOOTH
            : Manifest.permission.BLUETOOTH_CONNECT,
            R.id.radio_bluetooth),
    WIFI(Manifest.permission.ACCESS_FINE_LOCATION, R.id.radio_wifi);

    public final String permission;

    public final int button;

    IdType(String permission, int button) {
        this.permission = permission;
        this.button = button;
    }

}
