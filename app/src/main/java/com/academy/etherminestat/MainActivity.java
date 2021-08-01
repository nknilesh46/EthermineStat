package com.academy.etherminestat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button chat;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chat = findViewById(R.id.save);
        et = (EditText)findViewById(R.id.et);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedAddress = preferences.getString("Address", "");
        if(!savedAddress.equalsIgnoreCase(""))
        {
            //et.setText(savedAddress);  /* Address is present*/
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.putExtra("SAVED_ADDRESS", savedAddress);
            startActivity(intent);
            MainActivity.this.finish();
        }

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Address = et.getText().toString();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Address",Address);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                intent.putExtra("SAVED_ADDRESS", savedAddress);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}