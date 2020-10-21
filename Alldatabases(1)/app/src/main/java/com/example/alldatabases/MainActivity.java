package com.example.alldatabases;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String myJSON;
    double[] addressArr;
    TMapPolyLine tMapPolyLine;
    Boolean findloadState = false;
    double cLat=0.001, cLong=0.001;
    double distanceFlag = 1000;
    float location_interval = 0.2f; // 통지사이의 최소 변경거리 (m)
    int location_time = 100; // 통지사이의 최소 시간간격 (miliSecond)

    private static final String TAG_RESULTS = "result";
    private static final String TAG_LATI = "lati";
    private static final String TAG_LONGI = "longi";
    private static final String TAG_BLACK = "black";
    private static final String TAG_SPEED = "speed";
    private final String urladdress= "192.168.81.161";
    TMapView tmapview;
    float s_lati, l_lati, s_longi, l_longi;
    float ds_lati, dl_lati,ds_longi, dl_longi;

    ArrayList<HashMap<String, String>> personList;
    public JSONArray peoples = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkUtil.setNetworkPolicy();
        RelativeLayout mapContainer = (RelativeLayout) findViewById(R.id.mapContainer);
//        티맵 서비스 구현
        tmapview = new TMapView(this);
        tmapview.setSKTMapApiKey("l7xxf00ada6c07f44aa19c3ec8a140b85c90");
        mapContainer.addView(tmapview);
        tmapview.setIconVisibility(true);

        setGps();

        personList = new ArrayList<HashMap<String, String>>();

        FloatingActionButton doGetaddress = findViewById(R.id.findLoad);
        doGetaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),AddressGet.class);
                addressArr = new double[4];
                intent.putExtra("requestAddress",addressArr);
                startActivityForResult(intent, 1);
            }
        });



    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                tmapview.setLocationPoint(longitude, latitude);
                tmapview.setCenterPoint(longitude, latitude);
//                getData("http://"+urladdress+"/PHP_connection.php");


                // 경로안내 중일 때, 현재 위치가 갱신되면서 새로운 위치를 기준으로 다시 경로 검색 후 다시 안내
                if(findloadState) {
                    try {
                        tMapPolyLine = new TMapData().findPathData(new TMapPoint(location.getLatitude(), location.getLongitude()), new TMapPoint(addressArr[2], addressArr[3]));
                        tmapview.removeAllTMapPolyLine();
                        tmapview.addTMapPolyLine("Line1",tMapPolyLine);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                ds_lati = (float) (location.getLatitude()-cLat);
                dl_lati = (float) (location.getLatitude()+cLat);
                ds_longi = (float) (location.getLongitude()-cLong);
                dl_longi = (float) (location.getLongitude()+cLong);
               deleteData("http://"+urladdress+"/Delete_Ice.php");
                //현재 위치랑 비슷한 위치에 존재하는 블랙아이스 도로 불러오기
                // 1. 데이터베이스 읽어오기 (조건은 Lat가 location.getLatitude()-cLat ~ location.getLatitude()+cLat
                //                               Lon는 location.getLongtitude()-cLong ~ location.getLongtitude()+cLong인 도로위치
                // 2-1. 불러온 데이터가 존재할 경우
                //      2-1-1. 해당 위치에 해당하는 TmapPoint와 현재위치를 나타내는 location으로 만든 TmapPoint간의 경로를 먼저 검색
                //      2-1-2. 경로의 길이를 나타내는 distance를 통해 기존에 설정해둔 알림을 줄 거리 기준(distanceFlag) 값과 비교한다.
                //              3-1. 비교시 기준 값보다 거리가 가까울 경우
                //                   > 사용자에게 알림(근처에 블랙아이스가 존재)
                //              3-2. 비교시 기준 값보다 거리가 멀 경우 > pass
                // 2-2. 불러온 데이터가 없을 경우 > pass

                // 블루투스 값 체크

            }

        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    public void setGps() {
        final LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자(실내에선 NETWORK_PROVIDER 권장)
                location_time,
                location_interval,
                mLocationListener);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    addressArr = data.getDoubleArrayExtra("reponseAddress");
                    TMapPoint startAdd = new TMapPoint(addressArr[0],addressArr[1]);
                    TMapPoint endAdd = new TMapPoint(addressArr[2],addressArr[3]);

                    tMapPolyLine = new TMapData().findPathData(startAdd, endAdd);
                    tMapPolyLine.setLineColor(Color.RED);
                    tMapPolyLine.setLineWidth(5);
                    ArrayList<TMapPoint> arr = tMapPolyLine.getLinePoint();

                    tmapview.addTMapPolyLine("Line1", tMapPolyLine);
                    findloadState = true;
                }catch(Exception e) {
                    e.printStackTrace();
                }
            } else {   // RESULT_CANCEL
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
//        } else if (requestCode == REQUEST_ANOTHER) {
//            ...
        }
    }


    private void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            protected void onPreExecute() {
            }

            @Override
            protected String doInBackground(String... params) {

                StringBuilder sb = new StringBuilder();
                String postData = "s_lati=" + s_lati + "&" + "l_lati=" + l_lati + "&" + "s_longi=" + s_longi+ "&" + "l_longi=" + l_longi;

                try {
                    String uri = params[0];
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    if (con != null) {
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        con.setReadTimeout(5000);
                        con.setConnectTimeout(5000);
                        con.setRequestMethod("POST");
                        con.setDoInput(true);
                        con.connect();


                        OutputStream outputStream =con.getOutputStream();
                        outputStream.write(postData.getBytes("UTF-8"));
                        outputStream.flush();
                        outputStream.close();

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
                    e.printStackTrace();
                    return null;
                }

                return sb.toString().trim(); //trim()은 공백제거 문자, 신경안써도됨

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.i("###", "|" + result);
                myJSON = result;
                showResult();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
    private void deleteData(String url) {
        class deleteDB extends AsyncTask<String, Void, String> {

            protected void onPreExecute() {
            }

            @Override
            protected String doInBackground(String... params) {

                String postData = "s_lati=" + s_lati + "&" + "l_lati=" + l_lati + "&" + "s_longi=" + s_longi+ "&" + "l_longi=" + l_longi;
                String result = "true";
                try {
                    String uri = params[0];
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    if (con != null) {
                        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        con.setReadTimeout(5000);
                        con.setConnectTimeout(5000);
                        con.setRequestMethod("POST");
                        con.setDoInput(true);
                        con.connect();


                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(postData.getBytes("UTF-8"));
                        outputStream.flush();
                        outputStream.close();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

                return result;

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.equals("true")) {
                    Log.i("###", "|AAAAA 삭제되었습니다." );
                }
            }
        }
        deleteDB d = new deleteDB();
        d.execute(url);
    }
    protected void showResult(){
        try{
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i =0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String lati = c.getString(TAG_LATI);
                String longi = c.getString(TAG_LONGI);
                //String aaa = c.getString(TAG_AAA);

                HashMap<String, String> persons = new HashMap<String, String>();
                //put은 데이터 집어넣기
                //persons.put(TAG_LATI,lati); // TAG_ID : id
               // persons.put(TAG_LONGI,longi);
                //persons.put(TAG_AAA,aaa);

              //  personList.add(persons);

                Log.i("###", "|wwwwwwwwwwwwwwwwwwwwwwwww"+lati);
                Log.i("###", "|wwwwwwwwwwwwwwwwwwwwwwwww"+longi);
                //  if(Integer.parseInt(id) ==27){
                //      Toast.makeText(this.getApplicationContext(),"27살입니다.", Toast.LENGTH_SHORT).show();
                //   }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
