package com.student.mess10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class Dashboard extends AppCompatActivity {

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
        pieChart = (PieChart) findViewById(R.id.member_pie);

        pieChart.clearChart();
        pieChart.addPieSlice(new PieModel("Active",3, Color.parseColor("#00BFA6")));
        pieChart.addPieSlice(new PieModel("Recovered", 7, Color.parseColor("#c4c4c4")));

        pieChart.startAnimation();


    }
}