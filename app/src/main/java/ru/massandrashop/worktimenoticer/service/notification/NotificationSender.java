package ru.massandrashop.worktimenoticer.service.notification;

import android.content.Context;
import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import ru.massandrashop.worktimenoticer.R;
import ru.massandrashop.worktimenoticer.model.Record;
import ru.massandrashop.worktimenoticer.service.PreferencesService;
import ru.massandrashop.worktimenoticer.service.db.RecordLocalDatabaseService;
import ru.massandrashop.worktimenoticer.service.ui.ToastDemonstrator;
import ru.massandrashop.worktimenoticer.service.AndroidDeviceService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class NotificationSender {

    private static final String TAG = "--------->";

    public static void sendNotification(String name, String macAddress, Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = PreferencesService.getUrlForPostRecord(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
        },
                error -> {
                    Log.e(TAG, "Volley got response with error: " + error.toString());
                    ToastDemonstrator.showToast(context, R.string.toast_record_post_error);
                }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                String requestBody = null;
                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("name", name);
                    jsonBody.put("deviceId", AndroidDeviceService.getAndroidDeviceId(context));
                    jsonBody.put("macAddress", macAddress);
                    requestBody = jsonBody.toString();
                    return requestBody.getBytes(StandardCharsets.UTF_8);
                } catch (JSONException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    ToastDemonstrator.showToast(context, R.string.toast_record_accepted);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        requestQueue.add(stringRequest);
        RecordLocalDatabaseService.addRecordToLocalDatabase(context, new Record(name, macAddress));
    }

}
