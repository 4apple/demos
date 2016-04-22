package com.example1.learncontext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by user on 3/28/16.
 */
public class Main2 extends Activity {
    private TextView tv;
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        System.out.println("Main2 onCreate");
        tv = (TextView)findViewById(R.id.textView);
        et =(EditText)findViewById(R.id.editText);
        tv.setText("Shared data:" + getApp().getTextData());
        findViewById(R.id.btnSaveData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((App) getApplicationContext()).setTextData(et.getText().toString());
                tv.setText("Shared data:" + et.getText().toString());
            }
        });
    }
    public App getApp() {
        return (App) getApplicationContext();
    }

}
