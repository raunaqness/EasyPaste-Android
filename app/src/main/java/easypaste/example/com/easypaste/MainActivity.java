package easypaste.example.com.easypaste;



import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText e1;
    Button sendButton;
    Button exitButton;

    Button btnTakePicture, btnScanBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, BackgroundService.class);
        startService(intent);

        initViews();

    }

    private void initViews() {
        e1 = findViewById(R.id.editText);
        sendButton = findViewById(R.id.sendButton);
        exitButton = findViewById(R.id.exitButton);

        btnTakePicture = findViewById(R.id.btnTakePicture);
        btnScanBarcode = findViewById(R.id.btnScanBarcode);
        exitButton.setOnClickListener(this);
        btnTakePicture.setOnClickListener(this);
        btnScanBarcode.setOnClickListener(this);

        // Hide Temporarily

        e1.setVisibility(View.INVISIBLE);
        sendButton.setVisibility(View.INVISIBLE);
        btnScanBarcode.setVisibility(View.INVISIBLE);
    }

//    void disableReciever(Context context){
//        ComponentName componentName = new ComponentName(context, BackgroundService.class);
//        PackageManager packageManager = context.getPackageManager();
//        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
//    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnTakePicture:
                startActivity(new Intent(MainActivity.this, PictureBarcodeActivity.class));
                break;
            case R.id.btnScanBarcode:
                startActivity(new Intent(MainActivity.this, ScannedBarcodeActivity.class));
                break;

            case R.id.exitButton:
                Log.v("Button", "service stopped");
                stopService( new Intent(this, BackgroundService.class));
//                disableReciever(getApplicationContext());
                break;

            case R.id.sendButton:
                MessageSender messageSender = new MessageSender();
                messageSender.execute(e1.getText().toString());
                break;
        }

    }
}
