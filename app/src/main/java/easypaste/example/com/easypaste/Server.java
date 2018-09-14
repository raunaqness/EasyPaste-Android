package easypaste.example.com.easypaste;

import android.content.ClipData;
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
import static easypaste.example.com.easypaste.MainActivity.context;


public class Server extends NanoHTTPD {

//    private static Server server = null;

    ClipboardManager clipboard;
    Context c;

    Utils utils;



    public Server (Context c) throws IOException{
        super(8080);
        utils = new Utils();
        this.c = c;
        Log.e("context", c.toString());

    }

    private void setClipboard(Context context, String text) {

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);

    }

    public void CopyToClipboard(String clipText, Context conte){
        Log.e("context", conte.toString());
        clipboard = (ClipboardManager) conte.getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", clipText);
        clipboard.setPrimaryClip(clip);
    }


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

            // HandlePayload(payload_type, payload_data);

            setClipboard(c, payload_data);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newFixedLengthResponse(json);
    }

    void HandlePayload(String payload_type, String payload_data){
        switch (payload_type){
            case "Acknowledgement" :

                break;

            case "ClipText":
                break;

            case "Image":
                break;

            default:
                break;
        }
    }


//    public Server(Context c) throws IOException {
//        super(8080);
//        this.c = c;
//
//    }

//    // This static method let you access the unique instance of your server class
//    public static Server getServer(Context context) throws IOException{
//        if(server == null){
//            Log.e("nano", "server started");
//            server = new Server(context);
//        }
//        return server;
//
//    }




}
