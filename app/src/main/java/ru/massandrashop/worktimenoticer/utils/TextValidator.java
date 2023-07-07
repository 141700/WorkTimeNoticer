package ru.massandrashop.worktimenoticer.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import ru.massandrashop.worktimenoticer.R;

public abstract class TextValidator implements TextWatcher {
    private final TextView textView;

    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { }

    public static boolean validateEmployeeName(Context context, String name) {
        int nameLength = name.length();
        int minLength = Integer.parseInt(context.getString(R.string.min_name_length));
        int maxLength = Integer.parseInt(context.getString(R.string.max_name_length));
        return nameLength >= minLength && nameLength <= maxLength;
    }

}
