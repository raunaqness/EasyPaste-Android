package easypaste.example.com.easypaste;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundService extends IntentService{

    private ClipboardManager myClipboard;
    private ClipData myClip;


    public BackgroundService(){
        super("Background Service");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.v("BG", "BG service has started");
        createNotificationChannel();
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);


         ClipboardManager.OnPrimaryClipChangedListener mOnPrimaryClipChangedListener =
                new ClipboardManager.OnPrimaryClipChangedListener() {
                    @Override
                    public void onPrimaryClipChanged() {
                        Log.v("BG", "onPrimaryClipChanged");
                        ClipData abc = myClipboard.getPrimaryClip();
                        ClipData.Item item = abc.getItemAt(0);

                        String clipText = item.getText().toString();
                        Log.v("BG", clipText);
                    }
                };

         myClipboard.addPrimaryClipChangedListener(mOnPrimaryClipChangedListener);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "hello1234")
                .setSmallIcon(R.drawable.ic_action_name)
                .setContentTitle("Hello Fraaand")
                .setContentText("Run hori hai service bois")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(12345, mBuilder.build());


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("hello1234", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
