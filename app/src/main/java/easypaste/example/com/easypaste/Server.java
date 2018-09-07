package easypaste.example.com.easypaste;

import android.util.Log;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;


public class Server extends NanoHTTPD {

    private static Server server = null;

    @Override
    public Response serve(IHTTPSession session){
        String msg = "My Server in Android \n";
        msg += "Hi , this is a response from your android server ! ";

        Log.e("NanoHTTPD", "Chalgya");

        return newFixedLengthResponse(msg + "</body></html>\n");
    }

    private Server() throws IOException {
        super(8080);

    }

    // This static method let you access the unique instance of your server  class
    public static Server  getServer() throws IOException{
        if(server == null){

            server = new Server();
        }
        return server;

    }




}
