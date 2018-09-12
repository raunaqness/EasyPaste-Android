package easypaste.example.com.easypaste;

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

public class Utils {

    static RequestQueue queue = Volley.newRequestQueue(MainActivity.getContext());



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

    public static void volleyPostRequest(final String message, String ipaddress, String dataType){

        String port = "1234";
        String postURL = "http://" + ipaddress + ":" + port + "/" + dataType;
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
                myData.put("message", message);
                myData.put("timestamp", getTimestamp());
                Log.d("VolleyPost_Message", message);

                return myData;
            }
        };

        queue.add(stringRequest);
    }
}
