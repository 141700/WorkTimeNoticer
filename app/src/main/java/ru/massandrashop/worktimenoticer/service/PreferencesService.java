package ru.massandrashop.worktimenoticer.service;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.preference.PreferenceManager;
import ru.massandrashop.worktimenoticer.BuildConfig;
import ru.massandrashop.worktimenoticer.R;
import ru.massandrashop.worktimenoticer.model.IdType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PreferencesService {

    private static final String AUTH_TYPE_KEY = "AuthTypeKey";

    private static final String FIRST_RUN_KEY = "FirstRunKey";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setIdTypePreference(Context context, IdType type) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(AUTH_TYPE_KEY, type.name());
        editor.apply();
    }

    public static IdType getIdTypePreference(Context context) {
        return IdType.valueOf(getSharedPreferences(context).getString(AUTH_TYPE_KEY, IdType.UNDEFINED.name()));
    }

    public static String getEmployeeName(Context context) {
        return getSharedPreferences(context).getString(context.getString(R.string.employee_name_key), "");
    }

    public static boolean isFirstRun(Context context) {
        return getSharedPreferences(context).getBoolean(FIRST_RUN_KEY, true);
    }

    public static void setInitialPreferences(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(FIRST_RUN_KEY, false);
        editor.putInt(Manifest.permission.ACCESS_FINE_LOCATION, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            editor.putInt(Manifest.permission.BLUETOOTH_CONNECT, 0);
        }
        editor.putString(context.getString(R.string.app_version), BuildConfig.VERSION_NAME);
        editor.apply();
    }

    public static void setPermissionRequestQuantity(Context context, String permission, int quantity) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(permission, quantity);
        editor.apply();
    }

    public static int getPermissionRequestQuantity(Context context, String permission) {
        return getSharedPreferences(context).getInt(permission, 0);
    }

    public static String getUrlForGetAllPoints(Context context) {
        return getServerUrl(context) + context.getString(R.string.server_get_points);
    }

    public static String getUrlForPostRecord(Context context) {
        return getServerUrl(context) + context.getString(R.string.server_new_record);
    }

    private static String getServerUrl(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(context.getString(R.string.server_key), context.getString(R.string.server_url_default))
                + ":"
                + preferences.getString(context.getString(R.string.server_port_key), context.getString(R.string.server_port_default));
    }

    public static void setLocalBaseUpdateTimeStamp(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(context.getString(R.string.local_db_last_update), LocalDateTime.now().format(DATE_TIME_FORMATTER));
        editor.apply();
    }

}
