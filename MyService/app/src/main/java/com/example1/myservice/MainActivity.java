package com.example1.myservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText etData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etData = (EditText)findViewById(R.id.etData);
        findViewById(R.id.btnStartService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("data", etData.getText().toString());
                i.setClassName("com.example1.myservice","com.example1.myservice.MyService");
                System.out.println("etData="+etData.getText().toString());
                startService(i);
                //startService(new Intent(MainActivity.this, MyService.class));
            }
        });
        findViewById(R.id.btnStopService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClassName("com.example1.myservice", "com.example1.myservice.MyService");
                stopService(i);
            }
        });
    }
}
