package example1.testjson;

import android.app.Activity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 3/25/16.
 */
public class WriteJson extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Write JSon
//            {
//                "languages":[
//                {"id":1,"ide":"Eclipse","name":"Java"},
//                {"id":2,"ide":"XCode","name":"Swift"},
//                {"id":3,"ide":"Visual Studio","name":"C#"}
//                ],
//                "cat":"it"
//            }
        try {
            JSONObject root =new JSONObject();


            JSONObject lan1 = new JSONObject();
            lan1.put("id","1");
            lan1.put("ide","Eclispe");
            lan1.put("name","Java");

            JSONObject lan2 = new JSONObject();
            lan2.put("id","2");
            lan2.put("ide","Xcode");
            lan2.put("name","Swift");

            JSONObject lan3 = new JSONObject();
            lan3.put("id","3");
            lan3.put("name", "Visual Studio");
            lan3.put("ide", "C#");

            JSONArray array = new JSONArray();
            array.put(lan1);
            array.put(lan2);
            array.put(lan3);

            root.put("languages", array);
            root.put("cat","it");
            System.out.println("-------------WriteJson-----------------");
            System.out.println(root.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
