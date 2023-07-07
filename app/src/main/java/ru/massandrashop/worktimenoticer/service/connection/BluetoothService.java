package ru.massandrashop.worktimenoticer.service.connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.provider.Settings;
import ru.massandrashop.worktimenoticer.model.Point;
import ru.massandrashop.worktimenoticer.service.db.PointLocalDatabaseService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

public class BluetoothService implements ConnectionService {

    public static final String SERVICE_NAME = "Bluetooth";

    private static BluetoothAdapter bluetoothAdapter;

    public BluetoothService(Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
    }

    @Override
    public Optional<Point> getAnySuitablePoint(Context context) {
        HashMap<String, Point> points = PointLocalDatabaseService.getPointsFromLocalDatabase(context);
        return getPairedDevices().stream()
                .filter(device -> points.containsKey(device.getAddress().toUpperCase()))
                .filter(BluetoothService::isConnected)
                .map(device -> points.get(device.getAddress().toUpperCase()))
                .findAny();
    }

    @Override
    public boolean isConnectionTurnedOn() {
        return bluetoothAdapter.isEnabled() && bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON;
    }

    @Override
    public String getActionSettings() {
        return Settings.ACTION_BLUETOOTH_SETTINGS;
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    private static boolean isConnected(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("isConnected", (Class[]) null);
            return (boolean) m.invoke(device, (Object[]) null);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static Set<BluetoothDevice> getPairedDevices() {
        return bluetoothAdapter.getBondedDevices();
    }

}
