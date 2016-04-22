package example1.httppost;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(example1.httppost.R.layout.activity_main);
        findViewById(example1.httppost.R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<String, Void, Void>() {

                    @Override
                    protected Void doInBackground(String... params) {
                        try {
                            URL url = new URL(params[0]);
                            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                            connection.setDoInput(true);
                            connection.setDoOutput(true);
                            connection.setRequestMethod("POST");
                            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
                            BufferedWriter bw = new BufferedWriter(osw);
                            bw.write("sssss");
                            bw.flush();

                            InputStream is = connection.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
                            BufferedReader br = new BufferedReader(isr);
                            String line;
                            while((line = br.readLine())!=null) {
                                System.out.println(line);
                            }
                            br.close();
                            isr.close();
                            is.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute("http://www.baidu.com");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(example1.httppost.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == example1.httppost.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
