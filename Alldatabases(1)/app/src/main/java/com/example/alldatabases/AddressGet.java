package com.example.alldatabases;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;

import java.util.ArrayList;

public class AddressGet extends AppCompatActivity {
    double[] temp;
    ArrayList<String> itemsS = new ArrayList<String>();
    ArrayList<TMapPOIItem> items = new ArrayList<TMapPOIItem>();
    TMapPoint[] positions = new TMapPoint[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_address);

        Intent addressIntent = getIntent();
        temp = addressIntent.getDoubleArrayExtra("requestAddress");

        Button startBtn = (Button)findViewById(R.id.startBtn);
        Button endBtn = (Button)findViewById(R.id.arrivalBtn);
        Button returnAddress = (Button)findViewById(R.id.doFindLoad);
        final EditText startEdit = (EditText)findViewById(R.id.startEdt);
        final EditText endEdit = (EditText)findViewById(R.id.arrivalEdt);
        final ListView list = (ListView)findViewById(R.id.searchResult_list_item);


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TMapData tmapdata = new TMapData();
                String tmp = startEdit.getText().toString();

                ArrayAdapter adapter;
                items.clear();
                itemsS.clear();

                if (tmp == null)    return;
                try {
                    items = tmapdata.findAllPOI(tmp);
                    for (int i=0; i < items.size(); i++){
                        itemsS.add(items.get(i).name);
                    }
                    adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, itemsS);
                    list.setAdapter(adapter);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TMapData tmapdata = new TMapData();
                String tmp = endEdit.getText().toString();
                final ArrayAdapter adapter;
                items.clear();
                itemsS.clear();

                if (tmp == null)    return;

                try {
                    items = tmapdata.findAllPOI(tmp);
                    for (int i=0; i < items.size(); i++){
                        itemsS.add(items.get(i).name);
                    }
                    adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, itemsS);
                    list.setAdapter(adapter);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        returnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp[0] = positions[0].getLatitude();
                temp[1] =  positions[0].getLongitude();
                temp[2] =  positions[1].getLatitude();
                temp[3] =  positions[1].getLongitude();
                Log.d("d7", ""+temp[0]);
                Log.d("d7", ""+temp[1]);
                Log.d("d7", ""+temp[2]);
                Log.d("d7", ""+temp[3]);

                Intent intent  = new Intent();
                intent.putExtra("reponseAddress",temp);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(startEdit.isFocused()){
                    startEdit.setText(itemsS.get(position));
                    positions[0] = items.get(position).getPOIPoint();
                    Log.d("d7", positions[0].toString());
                }
                else if(endEdit.isFocused()){
                    endEdit.setText(itemsS.get(position));
                    positions[1] = items.get(position).getPOIPoint();
                    Log.d("d7",  items.get(position).toString());
                    Log.d("d7", positions[1].toString());
                }
                else{

                }
            }
        });
//        setResult(RESULT_OK, addressIntent);
//        finish();

    }
}