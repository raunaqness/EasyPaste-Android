package easypaste.example.com.easypaste;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundService extends IntentService{

    private ClipboardManager myClipboard;
    private ClipData myClip;


    String ipaddress;
    MessageSender messageSender;
    int startID;

    ClipboardManager.OnPrimaryClipChangedListener mOnPrimaryClipChangedListener;

    SharedPreferences sharedPreferences;


    public BackgroundService(){
        super("Background Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        this.startID = startID;
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        ipaddress = sharedPreferences.getString("ip_address", "0.0.0.0");
        Log.e("IP", ipaddress);

        Log.v("BG", "BG service has started");
        createNotificationChannel();
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

         mOnPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
                    @Override
                    public void onPrimaryClipChanged() {
                        Log.v("BG", "onPrimaryClipChanged");
                        ClipData abc = myClipboard.getPrimaryClip();
                        ClipData.Item item = abc.getItemAt(0);

                        String clipText = item.getText().toString();
                        Log.v("BG", clipText);

                        // Send to Socket
                        String hello[] = {clipText, ipaddress};

                        messageSender = new MessageSender();
                        messageSender.execute(hello);
                        Log.v("BG", clipText + " sent to : " + ipaddress);

                    }
                };

        myClipboard.addPrimaryClipChangedListener(mOnPrimaryClipChangedListener);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "hello1234")
                .setSmallIcon(R.drawable.ic_action_name)
                .setContentTitle("Hello Fraaand")
                .setContentText("Run hori hai service bois")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setOngoing(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(12345, mBuilder.build());

    }


    public void setIP(String ip){
        ipaddress = ip;

    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("hello1234", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.v("onDestroy()", "Andar aagye fraaaaaand");

        if(messageSender!=null) {
            messageSender.cancel(true);
        }

        if(myClipboard != null){
            myClipboard.removePrimaryClipChangedListener(mOnPrimaryClipChangedListener);
        }

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        try{
            notificationManager.cancel(12345);
        } catch (Exception e ){
            Log.v("onDestroy() - Service", e.getLocalizedMessage());
        }

        stopSelf(startID);
    }



    @Override
    protected void onHandleIntent(Intent intent){
        for(int i = 0; i < 15; i++){
            Log.v("BG", i + "");
        }

        try{
            Thread.sleep(1000);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
