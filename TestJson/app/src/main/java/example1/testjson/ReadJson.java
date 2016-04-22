package example1.testjson;

import android.app.Activity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by user on 3/25/16.
 */
public class ReadJson extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(getAssets().open("test.json"),"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader br =new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while((line = br.readLine())!= null) {
                builder.append(line);
            }
            br.close();
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject root = null;
        try {
            root = new JSONObject(builder.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("cat="+root.getString("cat"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//            {
//                "languages":[
//                {"id":1,"ide":"Eclipse","name":"Java"},
//                {"id":2,"ide":"XCode","name":"Swift"},
//                {"id":3,"ide":"Visual Studio","name":"C#"}
//                ],
//                "cat":"it"
//            }
        try {
            JSONArray array = root.getJSONArray("languages");
            for(int i = 0; i < array.length(); i++){
                JSONObject lan = array.getJSONObject(i);
                System.out.println("-------------ReadJson----------------");
                System.out.println("id="+lan.getInt("id"));
                System.out.println("name="+lan.getString("name"));
                System.out.println("ide="+lan.getString("ide"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
