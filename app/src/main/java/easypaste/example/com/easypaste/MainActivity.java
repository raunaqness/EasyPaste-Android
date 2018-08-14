package easypaste.example.com.easypaste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, BackgroundService.class);
        startService(intent);

        e1 = findViewById(R.id.editText);


    }

    public void send(View v){

        MessageSender messageSender = new MessageSender();
        messageSender.execute(e1.getText().toString());

    }

}
