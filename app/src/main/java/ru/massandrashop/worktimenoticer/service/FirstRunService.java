package ru.massandrashop.worktimenoticer.service;

import android.content.Context;
import android.content.Intent;
import ru.massandrashop.worktimenoticer.ui.preferences.PreferencesActivity;

public class FirstRunService {

    public static void processFirstRun(Context context) {
        Intent intent = new Intent(context, PreferencesActivity.class);
        context.startActivity(intent);
        PreferencesService.setInitialPreferences(context);
    }

}
