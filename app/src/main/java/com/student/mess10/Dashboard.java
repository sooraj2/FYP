package com.student.mess10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private PieChart pieChart;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
//    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//
// ---------------------- Hooks

        drawerLayout= findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
//       toolbar = findViewById(R.id.toolbar);

//       toolbar
//        setSupportActionBar(toolbar);

//        Hide or show items

        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_profile).setVisible(false);

//Navigation Drawer Menu
//        navigationView.bringToFront();
//        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();


//        graph on dashboard
        pieChart = (PieChart) findViewById(R.id.member_pie);

        pieChart.clearChart();
        pieChart.addPieSlice(new PieModel("Remaining",7, Color.parseColor("#00BFA6")));
        pieChart.addPieSlice(new PieModel("Spent", 3, Color.parseColor("#c4c4c4")));

        pieChart.startAnimation();
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{

            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_feedback:
                startActivity(new Intent(Dashboard.this, Feedback.class));
                break;
            case R.id.nav_comlpaint:
                startActivity(new Intent(Dashboard.this, Complaint.class));
                break;
            default:
                Toast.makeText(this, item.toString()+" -- konsa?", Toast.LENGTH_SHORT).show();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }
}