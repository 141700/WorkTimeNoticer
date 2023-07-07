package ru.massandrashop.worktimenoticer.ui.preferences;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import ru.massandrashop.worktimenoticer.R;
import ru.massandrashop.worktimenoticer.utils.TextValidator;

public class PreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        Context context = this.getActivity().getApplicationContext();

        EditTextPreference editTextPreference = findPreference(context.getString(R.string.employee_name_key));
        editTextPreference.setOnBindEditTextListener((editText) -> {
            editText.addTextChangedListener(new TextValidator(editText) {
                @Override
                public void validate(TextView textView, String text) {
                    boolean isTextValid = TextValidator.validateEmployeeName(context, text);
                    String message = isTextValid ? null : context.getString(R.string.wrong_name_warning) ;
                    editText.setError(message);
                }
            });
        });

        editTextPreference.setOnPreferenceChangeListener((preference, newValue)
                -> TextValidator.validateEmployeeName(context, newValue.toString()));
    }
}