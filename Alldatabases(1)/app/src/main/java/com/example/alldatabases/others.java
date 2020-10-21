package com.example.alldatabases;

import android.graphics.Color;
import android.util.Log;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;

import java.util.ArrayList;

public class others {
//    TMapPoint tMapPointStart = new TMapPoint(37.570841, 126.985302); // SKT타워(출발지)
//    TMapPoint tMapPointEnd = new TMapPoint(37.551135, 126.988205); // N서울타워(목적지)
//    TMapPolyLine tMapPolyLine;
//        try {
//        tMapPolyLine = new TMapData().findPathData(tMapPointStart, tMapPointEnd);
//        tMapPolyLine.setLineColor(Color.BLUE);
//        tMapPolyLine.setLineWidth(2);
//        ArrayList<TMapPoint> arr = tMapPolyLine.getLinePoint();
//        tmapview.addTMapPolyLine("Line1", tMapPolyLine);
////
//        Log.d("77",arr.toString());
////            if(arr.contains(tMapPointEnd)) {
////                tmapview.addTMapPolyLine("Line1", tMapPolyLine);
////            }
//        TMapMarkerItem tmp = new TMapMarkerItem();
//        tmp.setTMapPoint(arr.get(3));
//        tmapview.addMarkerItem("1",tmp);
//
//    }catch(Exception e) {
//        e.printStackTrace();
//    }





//        btn_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               getData("http://"+urladdress+"/PHP_connection.php");
//            }
//        });
//        btn_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    PHPRequest request = new PHPRequest("http://"+urladdress+"/Data_insert.php");
//                    String result = request.PhPtest(String.valueOf(data1.getText()),String.valueOf(data2.getText()),String.valueOf(data3.getText()),String.valueOf(data4.getText()));
//                    Log.i("########","AAAAAAAA"+result);
//                    if(result=="ok"){
//                        Toast.makeText(getApplication(),"들어감",Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText(getApplication(),"안 들어감",Toast.LENGTH_SHORT).show();
//                        Log.i("#######", "| don't insert");
//                    }
//                }catch (MalformedURLException e){
//                    e.printStackTrace();
//                }
//            }
//        });





//    protected void showList(){
//        try{
//            JSONObject jsonObj = new JSONObject(myJSON);
//            peoples = jsonObj.getJSONArray(TAG_RESULTS);
//
//            for(int i =0;i<peoples.length();i++){
//                JSONObject c = peoples.getJSONObject(i);
//                String lati = c.getString(TAG_LATI);
//                String longi = c.getString(TAG_LONGI);
//                String black = c.getString(TAG_BLACK);
//                String speed = c.getString(TAG_SPEED);
//                //String aaa = c.getString(TAG_AAA);
//
//                Log.i("###", "|wwwwwwwwwwwwwwwwwwwwwwwww"+speed);
//              //  if(Integer.parseInt(id) ==27){
//              //      Toast.makeText(this.getApplicationContext(),"27살입니다.", Toast.LENGTH_SHORT).show();
//             //   }
//
//                HashMap<String, String> persons = new HashMap<String, String>();
//                //put은 데이터 집어넣기
//                persons.put(TAG_LATI,lati); // TAG_ID : id
//                persons.put(TAG_LONGI,longi);
//                persons.put(TAG_BLACK,black);
//                persons.put(TAG_SPEED,speed);
//                //persons.put(TAG_AAA,aaa);
//
//                personList.add(persons);
//
//            }
//            ListAdapter adapter = new SimpleAdapter(
//                    MainActivity.this, personList, R.layout.list_item,
//                    new String[]{TAG_LATI,TAG_LONGI,TAG_BLACK,TAG_SPEED},
//                    new int[]{R.id.id,R.id.name,R.id.address,R.id.speed}
//            );
//
////            list.setAdapter(adapter);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
}
