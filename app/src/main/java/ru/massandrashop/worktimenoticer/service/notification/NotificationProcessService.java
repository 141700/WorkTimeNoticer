package ru.massandrashop.worktimenoticer.service.notification;

import android.content.Context;
import android.content.Intent;
import ru.massandrashop.worktimenoticer.R;
import ru.massandrashop.worktimenoticer.model.Point;
import ru.massandrashop.worktimenoticer.service.PreferencesService;
import ru.massandrashop.worktimenoticer.service.connection.ConnectionService;
import ru.massandrashop.worktimenoticer.service.connection.ConnectionServiceFactory;
import ru.massandrashop.worktimenoticer.utils.TextValidator;

import java.util.Optional;

public class NotificationProcessService {

    public static String processNotification(Context context) {
        String name = PreferencesService.getEmployeeName(context);
        if (!TextValidator.validateEmployeeName(context, name)) {
            return context.getString(R.string.wrong_name);
        }
        Optional<ConnectionService> optionalConnectionService = ConnectionServiceFactory.getConnectionService(context);
        if (!optionalConnectionService.isPresent()) {
            return context.getString(R.string.idservice_not_set);
        }
        ConnectionService connectionService = optionalConnectionService.get();
        if (!connectionService.isConnectionTurnedOn()) {
            context.startActivity(new Intent(connectionService.getActionSettings()));
            return String.format(context.getString(R.string.service_is_off), connectionService.getServiceName());
        }
        Optional<Point> optionalPoint = connectionService.getAnySuitablePoint(context);
        if (!optionalPoint.isPresent()) {
            return context.getString(R.string.no_known_device_found);
        }
        Point point = optionalPoint.get();
        NotificationSender.sendNotification(name, point.getMacAddress(), context);
        return String.format(context.getString(R.string.idservice_complete), name, point.getOfficeTitle());
    }

}
