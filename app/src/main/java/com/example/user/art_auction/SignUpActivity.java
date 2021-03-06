package com.example.user.art_auction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppBasicMenuActivity {


//    EditText userName;
//    EditText password;
//    TextView dataView;
    EditText firstName, lastName, eMail, password;
    TextView dataView;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        firstName = (EditText) findViewById(R.id.firstNameInput);
        lastName = (EditText) findViewById(R.id.lastNameInput);
        eMail = (EditText) findViewById(R.id.eMailInput);
        password = (EditText) findViewById(R.id.passwordInput);
        dataView = (TextView) findViewById(R.id.dataTextView);

    }

    public void saveData(final View view) {
        //todo: if login display Save login info
        SharedPreferences loginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginData.edit();
        editor.putString("First Name", firstName.getText().toString());
        editor.putString("Last Name", lastName.getText().toString());
        editor.putString("email", eMail.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.apply();

        String url = "http://10.0.2.2:8080/user/add";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent myIntent = new Intent(SignUpActivity.this, AuctionsGalleryActivity.class);
                        startActivity(myIntent);
                        //Toast.makeText(view.getContext(), "ok " + response, Toast.LENGTH_LONG);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body = "";
                try {
                    if( error.networkResponse.data != null) {
                        body = new String(error.networkResponse.data, "UTF-8");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), "Error" + body, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("fName", firstName.getText().toString());
                params2.put("lName", lastName.getText().toString());
                params2.put("email", eMail.getText().toString());
                params2.put("password", password.getText().toString());
                return params2;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };
        requestQueue.add(request);
    }
    public void getData(View view) {
        SharedPreferences loginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String name = loginData.getString("First Name", "");
        name += " " + loginData.getString("Last Name", " ");
        String email = loginData.getString("email", "");
        String pw = loginData.getString("password", "");
        String msg = "Saved User Name: " + name + "\nSaved email: " + email + "\nSaved Password: " + pw;
        dataView.setText(msg);
    }
    public void signUp(View view) {
        Intent myIntent = new Intent(SignUpActivity.this, AuctionActivity.class);
        startActivity(myIntent);
    }
}