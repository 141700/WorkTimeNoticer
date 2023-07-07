package ru.massandrashop.worktimenoticer.service.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import ru.massandrashop.worktimenoticer.R;
import ru.massandrashop.worktimenoticer.model.IdType;
import ru.massandrashop.worktimenoticer.service.IdTypeService;
import ru.massandrashop.worktimenoticer.service.PermissionService;
import ru.massandrashop.worktimenoticer.service.PreferencesService;

public class IdTypeRadioGroupService {

    public static void setButtonsInitialViewSettings(View view) {
        Context context = view.getContext();
        IdType type = PreferencesService.getIdTypePreference(context);
        if (type != IdType.UNDEFINED) {
            RadioButton button = view.findViewById(type.button);
            button.setChecked(context.checkSelfPermission(type.permission) == PackageManager.PERMISSION_GRANTED);
        }
    }

    public static void setOnCheckedChangeListener(View view) {
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Context context = group.getContext();
            setButton(context, checkedId);
            RadioButton buttonWifi = view.findViewById(R.id.radio_wifi);
            RadioButton buttonBluetooth = view.findViewById(R.id.radio_bluetooth);
            if (!buttonBluetooth.isChecked() && !buttonWifi.isChecked()) {
                PreferencesService.setIdTypePreference(context, IdType.UNDEFINED);
            }
        });
    }

    public static void setButton(Context context, int checkedId) {
        Activity activity = (Activity) context;
        IdType type = IdTypeService.getTypeByButton(checkedId);
        String permission = type.permission;
        boolean isPermissionGranted = PermissionService.isPermissionGranted(context, permission);
        if (isPermissionGranted) {
            PreferencesService.setIdTypePreference(context, type);
        } else {
            PermissionService.requestPermission(context, permission);
        }
        RadioButton button = activity.findViewById(checkedId);
        button.setChecked(isPermissionGranted);
    }

}
