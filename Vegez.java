package com.voll.voll;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Vegez extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VegAdapter vegAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<VegData> vegList;
    private RequestQueue mQueue;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegez);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setIcon(R.drawable.ic_launcher);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.show();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mQueue = Volley.newRequestQueue(this);
        vegList = new ArrayList<>();
        jsonParse();
    }

    private void jsonParse() {
        String url = "https://api.myjson.com/bins/19s4tg";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("vegetables");
                            progressDialog.dismiss();
                            for (int i = 0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                VegData vegData = new VegData(jsonObject.getString("name"),jsonObject.getString("price"),jsonObject.getString("url"));
                                vegList.add(vegData);



                            }
                            VegAdapter vegAdapter = new VegAdapter(vegList,getApplicationContext());
                            recyclerView.setAdapter(vegAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        );
        mQueue.add(request);
    }
}
