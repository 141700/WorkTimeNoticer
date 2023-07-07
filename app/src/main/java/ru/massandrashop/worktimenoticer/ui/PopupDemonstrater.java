package ru.massandrashop.worktimenoticer.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import ru.massandrashop.worktimenoticer.R;
import ru.massandrashop.worktimenoticer.service.FirstRunService;

public class PopupDemonstrater {

    private static final int WIDTH_PADDING_PERCENT = 10;
    private static final int HEIGHT_PADDING_PERCENT = 30;

    @SuppressLint("ClickableViewAccessibility")
    public static void showFirstRunPopupWindow(Context context) {
        Activity activity = (Activity) context;
        View view = activity.getWindow().getDecorView().getRootView();
        PopupWindow popupWindow = createPopupTextWindow(context, R.layout.popup_first_run);
        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable runPopupWindow = () -> {
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, -(popupWindow.getHeight() * HEIGHT_PADDING_PERCENT / 200));
            popupWindow.getContentView().setOnTouchListener((v, event) -> {
                popupWindow.dismiss();
                FirstRunService.processFirstRun(context);
                return true;
            });
        };
        mainHandler.postDelayed(runPopupWindow, 200L);
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void showPopupWindow(Context context, int layout) {
        Activity activity = (Activity) context;
        View view = activity.getWindow().getDecorView().getRootView();
        PopupWindow popupWindow = createPopupTextWindow(context, layout);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, -(popupWindow.getHeight() * HEIGHT_PADDING_PERCENT / 200));
        popupWindow.getContentView().setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
    }

    private static PopupWindow createPopupTextWindow(Context context, int layout) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(layout, null);
        Activity activity = (Activity) context;
        Rect windowSize = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(windowSize);
        int width = windowSize.width() * (100 - WIDTH_PADDING_PERCENT) / 100;
        int height = windowSize.height() * (100 - HEIGHT_PADDING_PERCENT) / 100;
        boolean focusable = true;
        return new PopupWindow(popupView, width, height, focusable);
    }

}
