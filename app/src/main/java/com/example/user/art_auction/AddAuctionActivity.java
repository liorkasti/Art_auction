package com.example.user.art_auction;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddAuctionActivity extends AppBasicMenuActivity {

    private static final String TAG = "Messeges";

    Date today = new Date();

    private TextView tvStartDate, tvStartTime, tvEndDate, tvEndTime;
    private Button btnGoToCalander, btnGoToCalander2;
    private String startDate, endDate, startTime, endTime,endTimeToActiveCalc;

    static final int PICK_START_DATE_REQUEST = 1;  // The request code
    static final int PICK_END_DATE_REQUEST = 2;  // The request code


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auction);
        //ui for date
        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        btnGoToCalander = (Button) findViewById(R.id.btnGoToCalander);
        //set start date
        btnGoToCalander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickDateIntent = new Intent(AddAuctionActivity.this, CalendarActivity.class);
                startActivityForResult(pickDateIntent, PICK_START_DATE_REQUEST);
            }
        });
        //set end date
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        btnGoToCalander2 = (Button) findViewById(R.id.btnGoToCalander2);
        //set date
        btnGoToCalander2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickDateIntent = new Intent(AddAuctionActivity.this, CalendarActivity.class);
                startActivityForResult(pickDateIntent, PICK_END_DATE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent pickIntent) {
        super.onActivityResult(requestCode, resultCode, pickIntent);
        // Check which request we're responding to

        if (requestCode == PICK_START_DATE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                startDate = pickIntent.getStringExtra("date");
                Log.i(TAG, "yyyy:HH:mm---------------startDate " + startDate);
                tvStartDate.setText(startDate);
            }
        }
        if (requestCode == PICK_END_DATE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                endDate = pickIntent.getStringExtra("date");
                Log.i(TAG, "yyyy:HH:mm---------------endDate " + endDate);
                tvEndDate.setText(endDate);
            }
        startTime = startDate + " " + startTime;
        endTime = endDate + " " + endTime;
        }
    }

    //TimePicker with DialogFragment
    public void btnGoToTimePicker(View v) {
        DialogFragment newFragment = new TPFragmentStart();
        newFragment.show(getSupportFragmentManager(), "timePicker");
        Log.i(TAG, "HH:mm---------------startTime " + startDate);

    }

    //TimePicker with DialogFragment
    public void btnGoToTimePicker2(View v) {
        DialogFragment newFragment = new TPFragmentEnd();
        newFragment.show(getSupportFragmentManager(), "timePicker2");
        Log.i(TAG, "HH:mm---------------endTime " + endTime);

    }


    public void addAuction(final View view) {
        //todo: validation

        final EditText auctionName = (EditText) findViewById(R.id.auctionName);
        final EditText auctionDesc = (EditText) findViewById(R.id.auctionDesc);

        String url = "http://10.0.2.2:8080/auction/add";


        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvEndTime = (TextView) findViewById(R.id.tvEndTime);

        startTime = tvStartTime.getText().toString();
        endTime = tvEndTime.getText().toString();
        Log.i(TAG, "addAuction HH:mm-startTime " + startTime);
        Log.i(TAG, "addAuctionHH:mm-endTime " + endTime);

        //validation Params:

        //check auctionName
        if (auctionName.length() == 0) { // check auctionName exists
            Toast.makeText(this, "Missing Auction Name", Toast.LENGTH_SHORT).show();
            auctionName.setError("Please enter Name");
//            auctionName.setText("");
        }
        //check auctionDesc
        if (auctionDesc.length() == 0) { // check auctionDesc exists
            Toast.makeText(this, "Missing Auction Description", Toast.LENGTH_SHORT).show();
            auctionDesc.setError("Please enter Name");
//            auctionName.setText("");
        }

        //combine date+time
        if (startDate != null && startTime != null) {
            startTime = startDate + " " + startTime;
            Log.i(TAG, "dd-MM-yyyy:HH:mm-startTime Param to send: " + startTime);
        } else {
            Toast.makeText(this, "Missing Start Date", Toast.LENGTH_SHORT).show();
            btnGoToCalander.setError("Please enter Start Date");
        }
        if (endDate != null && endTime != null) {
            endTimeToActiveCalc =  endTime;
            endTime = endDate + " " + endTime;
            Log.i(TAG, "dd-MM-yyyy:HH:mm-endTime Param to send: " + endTime);
        } else {
            Toast.makeText(this, "Missing End Date", Toast.LENGTH_SHORT).show();
            btnGoToCalander.setError("Please enter End Date");
        }

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //set the id from response as session id
                        //UserSessionSingleton.getInstance(AddAuctionActivity.this).loginUser(response);
                        Toast.makeText(view.getContext(), "ok " + response, Toast.LENGTH_LONG);
                        Intent myIntent = new Intent(AddAuctionActivity.this, AddAuctionItemActivity.class);
                        myIntent.putExtra("auctionId", response);
                        startActivity(myIntent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String body = "";
                try {
                    body = new String(error.networkResponse.data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), "Error" + body, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("title", auctionName.getText().toString());
                params2.put("description", auctionDesc.getText().toString());
                params2.put("startTime", startTime);
                params2.put("endTime", endTime);
                params2.put("user", UserSessionSingleton.getInstance(AddAuctionActivity.this).getSessionId());
                params2.put("isSilent", "false");
                return params2;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        RequestQueueSingleton.getInstance(AddAuctionActivity.this).addToRequestQue(request);

        Toast.makeText(this, "Saved! The Auction will be active util: " + endTime, Toast.LENGTH_LONG).show();
    }
}