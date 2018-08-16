package easypaste.example.com.easypaste;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender extends AsyncTask<String, String, Void> {

    @Override
    protected Void doInBackground(String... strings) {

        String message = strings[0];
        String ipaddress = strings[1];

        Log.v("messenger", message);
        Log.v("messenger", ipaddress);

        Socket s;
        DataOutputStream dos;
        PrintWriter pw;

        try{
            s = new Socket("192.168.0.102", 7800);
            pw = new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();
            s.close();

        }catch (IOException e){

            e.printStackTrace();

        }



        return null;
    }


}
