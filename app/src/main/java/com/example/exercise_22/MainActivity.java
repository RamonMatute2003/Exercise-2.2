package com.example.exercise_22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.exercise_22.Settings.Rest_api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    Rest_api rest_api=new Rest_api();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url=rest_api.url;
        RequestQueue queue= Volley.newRequestQueue(this);//queue=cola

        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONArray jsonArray=new JSONArray(response);
                            String[] datas=new String[jsonArray.length()];

                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject career_object=jsonArray.getJSONObject(i);//career_object=objeto carrera
                                String id=career_object.getString("id");
                                String title=career_object.getString("title");
                                String body=career_object.getString("body");
                                String data=id+" - "+title+" - "+body;
                                datas[i]=data;
                            }

                            ListView listView=findViewById(R.id.list);
                            ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, datas);//adapter=adaptador
                            listView.setAdapter(adapter);

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("W",""+error);
            }
        });

        queue.add(request);
        Button btn_post=findViewById(R.id.btn_post);

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Activity_list.class);
                startActivity(intent);
            }
        });
    }
}