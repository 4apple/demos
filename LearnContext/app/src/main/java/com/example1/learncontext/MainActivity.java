package com.example1.learncontext;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView tv;
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*tv = new TextView(MainActivity.this);
        //tv.setText("My textView");
        tv.setText(R.string.hello_world);
        //setContentView(R.layout.activity_main);
        setContentView(tv);
        System.out.println(getResources().getText(R.string.hello_world));*/
        /*ImageView iv = new ImageView(this);
        iv.setImageResource(R.mipmap.ic_launcher);*/
        setContentView(R.layout.main1);
        System.out.println("Main1 onCreate");
        tv = (TextView)findViewById(R.id.textView);
        et =(EditText)findViewById(R.id.editText);
        tv.setText("Shared data:" + getApp().getTextData());
        findViewById(R.id.btnSaveData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((App)getApplicationContext()).setTextData(et.getText().toString());
                tv.setText("Shared data:" + et.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public App getApp() {
        return (App) getApplicationContext();
    }
}
