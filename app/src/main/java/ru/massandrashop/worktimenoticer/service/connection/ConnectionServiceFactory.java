package ru.massandrashop.worktimenoticer.service.connection;

import android.content.Context;
import ru.massandrashop.worktimenoticer.service.PreferencesService;

import java.util.Optional;

public class ConnectionServiceFactory {

    public static Optional<ConnectionService> getConnectionService(Context context) {
        switch (PreferencesService.getIdTypePreference(context)) {
            case BLUETOOTH:
                return Optional.of(new BluetoothService(context));
            case WIFI:
                return Optional.of(new WifiService(context));
            default:
                return Optional.empty();
        }
    }
}
