package ru.massandrashop.worktimenoticer.service.connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;
import ru.massandrashop.worktimenoticer.model.Point;
import ru.massandrashop.worktimenoticer.service.db.PointLocalDatabaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class WifiService implements ConnectionService {

    public static final String SERVICE_NAME = "Wi-Fi";

    private static WifiManager wifiManager;

    public WifiService(Context context) {
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public Optional<Point> getAnySuitablePoint(Context context) {
        HashMap<String, Point> points = PointLocalDatabaseService.getPointsFromLocalDatabase(context);
        return getWifiDevices().stream()
                .filter(device -> points.containsKey(device.BSSID.toUpperCase()))
                .map(device -> points.get(device.BSSID.toUpperCase()))
                .findAny();
    }

    @Override
    public boolean isConnectionTurnedOn() {
        return wifiManager.isWifiEnabled();
    }

    @Override
    public String getActionSettings() {
        return Settings.ACTION_WIFI_SETTINGS;
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @SuppressLint("MissingPermission")
    private static List<ScanResult> getWifiDevices() {
        return wifiManager.getScanResults();
    }

}