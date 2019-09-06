package com.example.mygps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnLoc,btnLoc2;
    EditText codebarre;
    TextView username,passeword;
    double lat,lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLoc = (Button) findViewById(R.id.btnGetLoc);
        btnLoc2 = (Button) findViewById(R.id.btnGetLoc2);
        codebarre = (EditText) findViewById(R.id.codebarre);
        username = (TextView) findViewById(R.id.usertxt);
        passeword = (TextView) findViewById(R.id.passeword);
        btnLoc2.setOnClickListener(this);

        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GpsTracker gt = new GpsTracker(getApplicationContext());
                Location l = gt.getLocation();
                if( l == null){
                    Toast.makeText(getApplicationContext(),"GPS unable to get Value",Toast.LENGTH_SHORT).show();
                }else {
                     lat = l.getLatitude();
                     lon = l.getLongitude();
                    username.setText("          "+lat);
                    passeword.setText("        "+lon);

                   // Toast.makeText(getApplicationContext(),"GPS Lat = "+lat+"\n lon = "+lon,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void   searchUsers() {

        final ProgressDialog loading = ProgressDialog.show(this,"Recherche","S'il vous plaît, attendez");
        final String usernames = username.getText().toString().trim();
        final String passewords = passeword.getText().toString().trim();
        final String codebarres = codebarre.getText().toString().trim();




        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxJ1xPLsIYDsv1_uthOK34jfhju7ALkYOoa-QVQq7aOeG-vT1o/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(MainActivity.this,"login ou mot de passe incorrect",Toast.LENGTH_LONG).show();





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","insert");
                parmas.put("usernames",usernames);
                parmas.put("passewords",passewords);
                parmas.put("codebarres",codebarres);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);


    }


    @Override
    public void onClick(View view) {

        if(view==btnLoc2){

            searchUsers();

        }

    }

}