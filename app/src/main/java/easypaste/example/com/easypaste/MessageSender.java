package easypaste.example.com.easypaste;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... voids) {

        String message = voids[0];
        String ip = voids[1];

        Log.v("messenger-1", message);
//        Log.v("messenger", ipaddress);

        Socket s;
        DataOutputStream dos;
        PrintWriter pw;

        try{
            s = new Socket(ip, 1234);
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
