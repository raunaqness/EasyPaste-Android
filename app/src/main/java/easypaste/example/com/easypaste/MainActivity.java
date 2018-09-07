package easypaste.example.com.easypaste;



import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button exitButton;
    Button currentTimeButton;
    TextView currentTime;

    Button btnTakePicture, btnScanBarcode;

    static Context context;

    private Button connect;
    private TextView connectText;
    private boolean isConected = false;
    Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, BackgroundService.class);
        startService(intent);

        initViews();

        context = getApplicationContext();

    }

    public static Context getContext(){
        return context;
    }

    private void initViews() {

        connect = findViewById(R.id.CONNECT);
        connectText = findViewById(R.id.text_connect);

        try {
            server = Server.getServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Server Stuff


        currentTime = findViewById(R.id.currentTime);
        exitButton = findViewById(R.id.exitButton);
        btnTakePicture = findViewById(R.id.btnTakePicture);
//        btnScanBarcode = findViewById(R.id.btnScanBarcode);
        currentTimeButton = findViewById(R.id.currentTimeButton);

        currentTimeButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
        btnTakePicture.setOnClickListener(this);
        connect.setOnClickListener(this);
//        btnScanBarcode.setOnClickListener(this);

        // Hide Temporarily

//        btnScanBarcode.setVisibility(View.INVISIBLE);
    }

    public void HttpButtonClick(){
        if (isConected) {
            server.stop();
            connectText.setText("Disconnected");
        } else {
            try {
                server.start();
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

                int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
                final String formatedIpAddress = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                        (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));

                connectText.setText("Connected : " + "Please access! http://" + formatedIpAddress + ":" + server.getListeningPort() +" From a web browser");


            } catch (IOException e) {

                connectText.setText("Connection failed");


            }

        }
        isConected = !isConected;
    }


    public void setCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = mdformat.format(calendar.getTime());
        currentTime.setText(strDate);

    }

    public void starthttp(){
        Intent httpserver = new Intent(this, HttpServer.class);
        startActivity(httpserver);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnTakePicture:
                startActivity(new Intent(MainActivity.this, PictureBarcodeActivity.class));
                break;
//            case R.id.btnScanBarcode:
//                startActivity(new Intent(MainActivity.this, ScannedBarcodeActivity.class));
//                break;

            case R.id.exitButton:
                Log.v("Button", "service stopped");
                stopService( new Intent(this, BackgroundService.class));
//                disableReciever(getApplicationContext());
                break;

            case R.id.currentTimeButton:
//                setCurrentTime();
//                volleyGetRequest();
//                volleyPostRequest("Message", "http://192.168.0.101/android");
                starthttp();
                break;

            case R.id.CONNECT:
                HttpButtonClick();
                break;

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Make sure  to close connection when destroying the activity
        server.stop();
    }
}
