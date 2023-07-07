package ru.massandrashop.worktimenoticer.service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import ru.massandrashop.worktimenoticer.R;
import ru.massandrashop.worktimenoticer.service.ui.IdTypeRadioGroupService;
import ru.massandrashop.worktimenoticer.ui.PopupDemonstrater;

public class PermissionService {

    private static final int ACCESS_FINE_LOCATION_PERMISSION_CODE = 110;

    private static final int BLUETOOTH_PERMISSION_CODE = 120;

    private static final int BLUETOOTH_CONNECT_PERMISSION_CODE = 122;

    private static final int MAX_PERMISSION_ATTEMPTS = 2;


    public static boolean isPermissionGranted(Context context, String permission) {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Context context, String permission) {
        Activity activity = (Activity) context;
        int permissionRequestQuantity = PreferencesService.getPermissionRequestQuantity(context, permission);
        if (permissionRequestQuantity < MAX_PERMISSION_ATTEMPTS) {
            activity.requestPermissions(new String[]{permission}, getPermissionCode(permission));
        } else {
            PopupDemonstrater.showPopupWindow(context, R.layout.popup_permission_declined);
        }
    }

    public static void processPermissionRequest(Context context, String permission, int result) {
        int permissionRequestQuantity = PreferencesService.getPermissionRequestQuantity(context, permission);
        if (result != PackageManager.PERMISSION_GRANTED) {
            permissionRequestQuantity++;
        } else {
            permissionRequestQuantity = 0;
            IdTypeRadioGroupService.setButton(context, IdTypeService.getButtonByPermission(permission));
        }
        PreferencesService.setPermissionRequestQuantity(context, permission, permissionRequestQuantity);
    }

    private static int getPermissionCode(String permission) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                return ACCESS_FINE_LOCATION_PERMISSION_CODE;
            case Manifest.permission.BLUETOOTH:
                return BLUETOOTH_PERMISSION_CODE;
            case Manifest.permission.BLUETOOTH_CONNECT:
                return BLUETOOTH_CONNECT_PERMISSION_CODE;
            default:
                return 100;
        }
    }
}
