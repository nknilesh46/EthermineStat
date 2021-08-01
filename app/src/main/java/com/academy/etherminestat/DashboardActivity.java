package com.academy.etherminestat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class DashboardActivity extends AppCompatActivity {
    TextView AddressTv;
    private RequestQueue mRequestQue;
    private String URL = "https://api.ethermine.org/miner/:miner/dashboard/";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        AddressTv = (TextView) findViewById(R.id.AddressTv);
        mRequestQue = Volley.newRequestQueue(this);
        String Address =  (String)getIntent().getSerializableExtra("SAVED_ADDRESS");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedAddress = preferences.getString("Address", "");
        AddressTv.setText(savedAddress);
        //Toast.makeText(this,Address,Toast.LENGTH_SHORT).show();
        fetchData(savedAddress);
    }

    private void fetchData(String savedAddress) {
        JSONObject json = new JSONObject();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL,
                json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "response  "+response, Toast.LENGTH_LONG);
                        Log.d("MUR", "onResponse: ");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MUR", "onError: "+error.networkResponse);
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
               // header.put("content-type","application/json");
                header.put("miner","0x2F0DE43D24e1042FE4b85508e5Be5D1F4145269E");
                return header;
            }
        };
        mRequestQue.add(request);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.ibutton) {
//            Intent intent = new Intent(this, InfoActivity.class);
//            intent.putExtra("FileName", "SelectEnv.jpeg");
//            startActivity(intent);
            return true;
        }

        if (id == R.id.clear_address) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Address",null);
            editor.apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            DashboardActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
