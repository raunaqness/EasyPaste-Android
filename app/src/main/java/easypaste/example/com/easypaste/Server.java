package easypaste.example.com.easypaste;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;


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

        Log.e("nano", json);


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
