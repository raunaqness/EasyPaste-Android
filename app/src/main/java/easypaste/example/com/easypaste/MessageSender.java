package easypaste.example.com.easypaste;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        JSONObject post_dict = new JSONObject();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String timestamp = mdformat.format(calendar.getTime());


        try{
            post_dict.put("timestamp", timestamp);
            post_dict.put("message", message);


        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            s = new Socket(ip, 1234);
            pw = new PrintWriter(s.getOutputStream());
            pw.write(String.valueOf(post_dict));
            pw.flush();
            pw.close();
            s.close();

        }catch (IOException e){

            e.printStackTrace();

        }

        return null;
    }


}
