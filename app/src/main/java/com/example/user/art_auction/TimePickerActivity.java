package com.example.user.art_auction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TimePicker;

/**
 * Created by user on 17/03/2018.
 */

class TimePickerActivity extends AppCompatActivity {

    private static final String TAG = "TimePickerActivity";

    private TimePicker mTimePicker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_picker_layout);

        Log.d(TAG, "on creat");

//        mTimePicker = (TimePicker) mTimePicker.findViewById(R.id.timePicker2);
//        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                mTimePicker.setHour(hourOfDay);
//                mTimePicker.setMinute(minute);
//
//                String time = hourOfDay + ":" + minute;
//
//
//                Intent intent = new Intent(TimePickerActivity.this, AddAuctionActivity.class);
//                intent.putExtra("time", time);
//                startActivity(intent);
//            }
//        });
    }
}
