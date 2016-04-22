package com.example.learnfile;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.jar.Manifest;

public class Main extends AppCompatActivity implements View.OnClickListener {
    private Button btnReadAsserts,btnReadRaw;
    private Button btnReadFile, btnWriteFile;
    private Button btnExtReadFile, btnExtWriteFile;
    private EditText etInput;
    private TextView tvShow;
    private EditText etExtInput;
    private TextView tvExtShow;

    private static final String FILE_NAME = "test.txt";
    private File sdcard;

    //Storage Permissions
    //For API 23+,you need to request the read/write permissions even if
    //they are already in your mannifest
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    public static void verifyStoragePermissions(Activity activity) {
        //check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permission != PackageManager.PERMISSION_GRANTED) {
            //We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnReadAsserts = (Button) findViewById(R.id.btnReadAsserts);
        btnReadRaw = (Button) findViewById(R.id.btnReadRaw);

        btnReadFile = (Button) findViewById(R.id.btnReadFile);
        btnWriteFile = (Button) findViewById(R.id.btnWriteFile);
        etInput = (EditText) findViewById(R.id.etInput);
        tvShow = (TextView) findViewById(R.id.show);

        btnExtReadFile = (Button) findViewById(R.id.btnExtReadFile);
        btnExtWriteFile = (Button) findViewById(R.id.btnExtWriteFile);
        etExtInput = (EditText) findViewById(R.id.etExtInput);
        tvExtShow = (TextView) findViewById(R.id.tvExtshow);
        sdcard = Environment.getExternalStorageDirectory().getAbsoluteFile();
        System.out.println(sdcard.getAbsolutePath());

        btnReadAsserts.setOnClickListener(this);
        btnReadRaw.setOnClickListener(this);

        btnWriteFile.setOnClickListener(this);
        btnReadFile.setOnClickListener(this);
        btnExtWriteFile.setOnClickListener(this);
        btnExtReadFile.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnExtWriteFile:
                //check permission before create file in external storage
                //Check permission
                verifyStoragePermissions(this);
                int writepermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                Toast.makeText(this,"get Permission here",Toast.LENGTH_LONG).show();
                if(writepermission != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"No permission to write storage,exit",Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    File file = new File(sdcard,"myfile.txt");
//                    if(!sdcard.exists()){
//                        Toast.makeText(this,"SD card not exist",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if(file.exists()) file.delete();
                    System.out.println(file.getAbsolutePath());
                    file.createNewFile();
                    /*
                    04-21 03:01:32.095    8189-8189/com.example.learnfile W/System.err﹕ java.io.IOException: open failed: EACCES (Permission denied)
                    04-21 03:01:32.096    8189-8189/com.example.learnfile W/System.err﹕ at java.io.File.createNewFile(File.java:939)
                     */
                    Toast.makeText(this,"SD card ready,create and write sdcard",Toast.LENGTH_SHORT).show();
                    //FileOutputStream fos =  openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    //FileOutputStream fos =  openFileOutput(file.getAbsolutePath(), Context.MODE_PRIVATE);
                    System.out.println("full:" + file.getAbsolutePath());
                    FileOutputStream fos = new FileOutputStream(file);
                    OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
                    osw.write(etExtInput.getText().toString());
                    osw.flush();
                    fos.flush();
                    osw.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
                break;
            case R.id.btnExtReadFile:
                //check permission before create file in external storage
                int readpermission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
                if(readpermission != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"No permission to read storage,exit",Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    File file = new File(sdcard,"myfile.txt");
//                    FileInputStream fis = openFileInput(file.getAbsolutePath());
                    FileInputStream fis = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
                    char[] buffer = new char[fis.available()];
                    isr.read(buffer);
                    isr.close();
                    fis.close();
                    String str = new String(buffer);
                    tvExtShow.setText(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnWriteFile:
                try {
                    FileOutputStream fos =  openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
                    osw.write(etInput.getText().toString());
                    osw.flush();
                    fos.flush();
                    osw.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnReadFile:
                try {
                    FileInputStream fis = openFileInput(FILE_NAME);
                    InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
                    char[] buffer = new char[fis.available()];
                    isr.read(buffer);
                    isr.close();
                    fis.close();
                    String str = new String(buffer);
                    tvShow.setText(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnReadAsserts:
                try {
                    InputStream is = getResources().getAssets().open("info.txt");
                    InputStreamReader isr = new InputStreamReader(is,"UTF-8"); //GBK
                    BufferedReader br = new BufferedReader(isr);
                    String in = "";
                    while((in = br.readLine())!=null){
                        Log.i("ReadAsserts", in);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btnReadRaw:
                try {
                    //InputStream is = getResources().getAssets().open("info.txt");
                    InputStream is = getResources().openRawResource(R.raw.rawinfo);
                    InputStreamReader isr = new InputStreamReader(is,"UTF-8"); //GBK
                    BufferedReader br = new BufferedReader(isr);
                    String in = "";
                    while((in = br.readLine())!=null){
                        Log.i("ReadRaw", in);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
