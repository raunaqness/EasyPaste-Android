package easypaste.example.com.easypaste;

import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

import static android.content.Context.CLIPBOARD_SERVICE;


public class Server extends NanoHTTPD {

    private static Server server = null;


    @Override
    public Response serve(IHTTPSession session) {

        final HashMap<String, String> map = new HashMap<>();
        try {
            session.parseBody(map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseException e) {
            e.printStackTrace();
        }

        final String json = map.get("postData");
        Log.e("map", map.toString());
        Log.e("nano", json);

        try {
            String s = map.toString();
            String payload_type =  s.substring(s.indexOf("payload_type=") + "payload_type=".length(), s.indexOf("&"));
            String payload_data =  s.substring(s.indexOf("payload_data=") + "payload_data=".length(), s.indexOf("}"));

            Log.e("payload_type", payload_type);
            Log.e("payload_data", payload_data);

            Utils.CopyToClipboard(payload_data);



        } catch (Exception e) {
            e.printStackTrace();
        }





        return newFixedLengthResponse(json);
    }


    private Server() throws IOException {
        super(8080);

    }

    // This static method let you access the unique instance of your server  class
    public static Server  getServer() throws IOException{
        if(server == null){
            Log.e("nano", "server started");
            server = new Server();
        }
        return server;

    }




}
