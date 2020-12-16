package com.example.projects.investments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.FingerprintGestureController;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.math.MathUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button lastday, lastweek, lastmonth, lastyear, all;
    TextView lastdayvalue, lastweekvalue, lastmonthvalue, lastyearvalue, allvalue;
    int i = 0, j = 0, l = 0, k = 0, m = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastday = findViewById(R.id.lastday);
        lastweek = findViewById(R.id.lastweek);
        lastmonth = findViewById(R.id.lastmonth);
        lastyear = findViewById(R.id.lastyear);
        all = findViewById(R.id.all);

        lastdayvalue = findViewById(R.id.lastdayvalue);
        lastweekvalue = findViewById(R.id.lastweekvalue);
        lastmonthvalue = findViewById(R.id.lastmonthvalue);
        lastyearvalue = findViewById(R.id.lastyearvalue);
        allvalue = findViewById(R.id.allvalue);


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String da = dateFormat.format(date);
        Date dat = null;
        try {
            dat = dateFormat.parse(da);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calLast = Calendar.getInstance();
        calLast.setTime(dat);
        calLast.add(Calendar.DAY_OF_YEAR, -1);
        Date lastDayDate = calLast.getTime();
        String resultLast = dateFormat.format(lastDayDate);

        Calendar calWeek = Calendar.getInstance();
        calWeek.setTime(dat);
        calWeek.add(Calendar.DAY_OF_YEAR, -7);
        Date lastWeekDate = calWeek.getTime();
        String resultWeek = dateFormat.format(lastWeekDate);

        Calendar calMonth = Calendar.getInstance();
        calMonth.setTime(dat);
        calMonth.add(Calendar.MONTH, -1);
        Date lastMonthDate = calMonth.getTime();
        String resultMonth = dateFormat.format(lastMonthDate);

        Calendar calYear = Calendar.getInstance();
        calYear.setTime(dat);
        calYear.add(Calendar.YEAR, -1);
        Date lastYearDate = calYear.getTime();
        String resultYear = dateFormat.format(lastYearDate);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("data");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sipyear = 0, sipweek = 0 , sipmonth = 0, sipall = 0;
                int stocksyear = 0, stocksweek = 0, stocksmonth = 0, stocksall = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String datee = postSnapshot.child("which_date").getValue().toString();
                    String sip = postSnapshot.child("fund_summary").child("mf_sips").child("0").child("total").getValue().toString();
                    String stocks = postSnapshot.child("fund_summary").child("stocks").child("0").child("total").getValue().toString();

                    List<String> lastyear = new ArrayList<>();
                    List<Integer> sipvalueyear = new ArrayList<>();
                    List<Integer> stocksvalueyear = new ArrayList<>();
                    lastyear = getDates(resultYear,resultLast);
                    int sv,j;
                    if(lastyear.contains(datee)){
                       j = Integer.valueOf(sip);
                       sv = Integer.valueOf(stocks);
                        sipvalueyear.add(j);
                        stocksvalueyear.add(sv);
                    }
                    for(int i=0; i<sipvalueyear.size(); i++){
                        sipyear += sipvalueyear.get(i);
                    }
                    for(int z=0; z<stocksvalueyear.size(); z++){
                        stocksyear += stocksvalueyear.get(z);
                    }

                    List<String> lastweek = new ArrayList<>();
                    List<Integer> sipvalueweek = new ArrayList<>();
                    List<Integer> stocksvalueweek= new ArrayList<>();
                    lastweek = getDates(resultWeek,resultLast);
                    if(lastweek.contains(datee)){
                        j = Integer.valueOf(sip);
                        sv = Integer.valueOf(stocks);
                        sipvalueweek.add(j);
                        stocksvalueweek.add(sv);
                    }
                    for(int i=0; i<sipvalueweek.size(); i++){
                        sipweek += sipvalueweek.get(i);
                    }
                    for(int z=0; z<stocksvalueweek.size(); z++){
                        stocksweek += stocksvalueweek.get(z);

                    }

                    List<String> lastmonth = new ArrayList<>();
                    List<Integer> sipvaluemonth = new ArrayList<>();
                    List<Integer> stocksvaluemonth= new ArrayList<>();
                    lastmonth = getDates(resultMonth,resultLast);
                    if(lastmonth.contains(datee)){
                        j = Integer.valueOf(sip);
                        sv = Integer.valueOf(stocks);
                        sipvaluemonth.add(j);
                        stocksvaluemonth.add(sv);
                    }
                    for(int i=0; i<sipvaluemonth.size(); i++){
                        sipmonth += sipvaluemonth.get(i);
                    }
                    for(int z=0; z<stocksvaluemonth.size(); z++){
                        stocksmonth += stocksvaluemonth.get(z);

                    }

                    if(resultLast.equals(datee)){
                        j = Integer.valueOf(sip);
                        sv = Integer.valueOf(stocks);
                        i = j + sv;
                    }

                    List<Integer> sipvalueall = new ArrayList<>();
                    List<Integer> stocksvalueall= new ArrayList<>();
                        j = Integer.valueOf(sip);
                        sv = Integer.valueOf(stocks);
                        sipvalueall.add(j);
                        stocksvalueall.add(sv);

                    for(int i=0; i<sipvalueall.size(); i++){
                        sipall += sipvalueall.get(i);
                    }
                    for(int z=0; z<stocksvalueall.size(); z++){
                        stocksall += stocksvalueall.get(z);

                    }
                }
                 l = sipyear + stocksyear;
                 j = sipweek + stocksweek;
                 k = sipmonth + stocksmonth;
                 m = sipall + stocksall;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        lastyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastyearvalue.setText("Rs. "+String.valueOf(l));
            }
        });
        lastweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastweekvalue.setText("Rs. "+String.valueOf(j));
            }
        });
        lastmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastmonthvalue.setText("Rs. "+String.valueOf(k));
            }
        });
        lastday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastdayvalue.setText("Rs. "+String.valueOf(i));
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allvalue.setText("Rs. "+String.valueOf(m));
            }
        });
    }

    public List<String> getDates(String start, String end) {
        List<Date> dates = new ArrayList<Date>();
        DateFormat formatter;
        String str_date = start;
        String end_date = end;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate = (Date) formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null;
        try {
            endDate = (Date) formatter.parse(end_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long interval = 24 * 1000 * 60 * 60;
        long endTime = endDate.getTime();
        long curTime = startDate.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }
        List<String> d = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            Date lDate = (Date) dates.get(i);
            String ds = formatter.format(lDate);
           // System.out.println(ds);
            d.add(ds);
        }
        return d;
    }
}