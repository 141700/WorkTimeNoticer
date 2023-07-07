package ru.massandrashop.worktimenoticer.ui.preferences;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import ru.massandrashop.worktimenoticer.service.db.PointUpdater;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PointUpdater.updateLocalPointDatabase(this.getApplication().getApplicationContext());
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferencesFragment())
                .commit();
    }

}