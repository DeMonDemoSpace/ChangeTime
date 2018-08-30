package com.demon.setsystemtime;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.instacart.library.truetime.TrueTime;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int mYear, mMonth, mDay;
    int mHour, mMinute;
    Button dateChooseBtn, timeChooseBtn;
    TextView dateDisplay, timeDisplay, tvNow;
    final int DATE_DIALOG = 1;
    final int Time_DIALOG = 2;

    private long Total_Time = 0;
    private Calendar canlendardata;
    private Calendar canlendartimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateChooseBtn = findViewById(R.id.dateChoose);
        dateDisplay = findViewById(R.id.dateDisplay);

        timeChooseBtn = findViewById(R.id.timeChoose);
        timeDisplay = findViewById(R.id.timeDisplay);
        tvNow = findViewById(R.id.tv_now);
        dateChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        tvNow.setText(TrueTime.now().toString());
        timeChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Time_DIALOG);
            }
        });

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        mHour = ca.get(Calendar.HOUR_OF_DAY);
        mMinute = ca.get(Calendar.MINUTE);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        String dateStr = String.format("%d-%d-%d", mYear, mMonth + 1, mDay);
                        dateDisplay.setText(dateStr);

                        canlendardata = Calendar.getInstance();
                        canlendardata.set(Calendar.YEAR, year);
                        canlendardata.set(Calendar.MONTH, monthOfYear);
                        canlendardata.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long when_data = canlendardata.getTimeInMillis();
                        Total_Time = when_data;
                        if (Total_Time / 1000 < Integer.MAX_VALUE) {
                            SystemClock.setCurrentTimeMillis(Total_Time);
                        }
                    }
                }, mYear, mMonth, mDay);
            case Time_DIALOG:
                return new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;

                        String timeStr = String.format("%d:%d", mHour, mMinute);
                        timeDisplay.setText(timeStr);

                        canlendartimer = Calendar.getInstance();
                        canlendartimer.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        canlendartimer.set(Calendar.MINUTE, minute);
                        canlendartimer.set(Calendar.SECOND, 0);
                        canlendartimer.set(Calendar.MILLISECOND, 0);
                        long when_time = canlendartimer.getTimeInMillis();

                        if (when_time / 1000 < Integer.MAX_VALUE) {
                            SystemClock.setCurrentTimeMillis(when_time);
                        }
                    }
                }, mHour, mMinute, true);
        }
        return null;
    }

    /**
     * 网址访问
     *
     * @param url 网址
     * @return urlDate 对象网址时间
     */
    public static String VisitURL(String url) {
        String urlDate = null;
        try {
            URL url1 = new URL(url);
            URLConnection conn = url1.openConnection();  //生成连接对象
            conn.connect();  //连接对象网页
            Date date = new Date(conn.getDate());  //获取对象网址时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //设置日期格式
            urlDate = df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlDate;
    }
}

