package ru.massandrashop.worktimenoticer.service.connection;

import android.content.Context;
import ru.massandrashop.worktimenoticer.model.Point;

import java.util.Optional;

public interface ConnectionService {

    String getActionSettings();

    String getServiceName();

    Optional<Point> getAnySuitablePoint(Context context);

    boolean isConnectionTurnedOn();

}
