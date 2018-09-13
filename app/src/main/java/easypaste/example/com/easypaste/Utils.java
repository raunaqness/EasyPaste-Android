package easypaste.example.com.easypaste;

import android.app.Application;
import android.app.IntentService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.CLIPBOARD_SERVICE;

public class Utils  {

    static RequestQueue queue = Volley.newRequestQueue(MainActivity.getContext());

    public static void CopyToClipboard(String ClipText){

        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", ClipText);
        clipboard.setPrimaryClip(clip);

    }

    private static Object gg(String clipboardService) {
        return(MainActivity.getContext());
    }


    public static String getTimestamp(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    public static void volleyGetRequest(){
        String url = "";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("gg", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        queue.add(stringRequest);
    }

    public static void volleyPostRequest(final String message, String ipaddress, final String payload_type){

        String port = "1234";
        String postURL = "http://" + ipaddress + ":" + port + "/payload_from_android";
        Log.d("Utils", postURL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("VolleyPost", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyPost", error.toString());

            }
        }) {
            protected Map<String, String> getParams(){
                Map<String, String> myData = new HashMap<String, String>();
                myData.put("payload_type", payload_type);
                myData.put("data", message);
                myData.put("timestamp", getTimestamp());
                Log.d("VolleyPost_Message", message);

                return myData;
            }
        };

        queue.add(stringRequest);
    }

    public static Context getActivity() {
        return MainActivity.getContext();
    }
}
