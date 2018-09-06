package com.voll.voll;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView tvId,tvTitle,tvDesc;
    private Button btn;
    private RequestQueue mQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvId = (TextView)findViewById(R.id.tv_id);
        tvTitle = (TextView)findViewById(R.id.tv_title) ;
        tvDesc = (TextView)findViewById(R.id.tv_description);
     //   btn = (Button)findViewById(R.id.bt_show);
        mQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        jsonParse();

    }

    private void jsonParse() {
        String url = "http://www.fittrage.com/afittrage/timeline.php?type=1/";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i=0;i<jsonArray.length();i++){
                                progressDialog.dismiss();
                                JSONObject data = jsonArray.getJSONObject(i);
                                int id = data.getInt("id");
                                String title = data.getString("title");
                                String description = data.getString("description");
                           //  textView.setText(String.valueOf(id));
                                tvId.setText(String.valueOf(id));
                                tvTitle.setText(title);
                                tvDesc.setText(description);
                               // tvId.append(String.valueOf(id) + ", " + title+ ", "+description+ "\n\n");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );
        mQueue.add(request);
    }
}
