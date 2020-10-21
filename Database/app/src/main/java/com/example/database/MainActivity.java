package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "age";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD = "address";
    //private static final String TAG_AAA = "aaa";


    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;

    String url ="http://192.168.82.245/PHP_connection.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.listView);
        personList=new ArrayList<HashMap<String, String>>();
        getData(url);

    }


    protected void showList(){
        try{
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i =0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String address = c.getString(TAG_ADD);
                //String aaa = c.getString(TAG_AAA);

                Log.i("###", "|wwwwwwwwwwwwwwwwwwwwwwwww"+id);
                if(Integer.parseInt(id) ==27){
                    Toast.makeText(this.getApplicationContext(),"27살입니다.", Toast.LENGTH_SHORT).show();
                }

                HashMap<String, String> persons = new HashMap<String, String>();
                //put은 데이터 집어넣기
                persons.put(TAG_ID,id); // TAG_ID : id
                persons.put(TAG_NAME,name);
                persons.put(TAG_ADD,address);
                //persons.put(TAG_AAA,aaa);

                personList.add(persons);

            }
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, personList, R.layout.list_item,
                    new String[]{TAG_ID,TAG_NAME,TAG_ADD},
                    new int[]{R.id.id,R.id.name,R.id.address}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getData(String url) {
        class GetDataJSON extends AsyncTask<String,Void,String>{

            protected void onPreExecute(){
            }
            @Override
            protected String doInBackground(String...params) {

                StringBuilder sb = new StringBuilder();
                try{
                    String uri = params[0];
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();

                    if ( con != null ) {
                        con.setConnectTimeout(10000);
                        //con.setUseCaches(false);
                        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            Log.i("###", "|bggggggggggggggggggggggggg");
                            String json;
                            while ((json = bufferedReader.readLine()) != null) {
                                sb.append(json + "\n");
                            }
                            bufferedReader.close();
                        }
                        con.disconnect();
                    }

                } catch (Exception e) {
                    Log.i("###", "|aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                    e.printStackTrace();
                    return null;
                }

                return sb.toString().trim(); //trim()은 공백제거 문자, 신경안써도됨

            }

            @Override
            protected void onPostExecute(String result){
                super.onPostExecute(result);
                Log.i("###", "|" + result);
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}