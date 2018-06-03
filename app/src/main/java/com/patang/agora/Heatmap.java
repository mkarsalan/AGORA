package com.patang.agora;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class Heatmap extends AppCompatActivity {


    private static final String TAG = "Heatmap";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heatmap);

        if (isServiesOK()) {
            init();
        }
        mDrawer = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);
    }
    public void selectItemDrawer(MenuItem menuItem){
        android.support.v4.app.Fragment myFragment = null;
        Class fragmentClass;

        switch (menuItem.getItemId()){
            case R.id.param:
                fragmentClass = Fragment1.class;
                break;
//            case R.id.Thresholds:
//                fragmentClass = Fragment2.class;
//                break;
//            case R.id.Nodes:
//                fragmentClass = Settings.class;
//                break;
//            case R.id.download:
//                fragmentClass = Fragment3.class;
//                break;
            default:
                fragmentClass = Heatmap.class;
                break;
        }
        try {
            myFragment = (android.support.v4.app.Fragment)fragmentClass.newInstance();
        }
        catch (Exception e){
            e.printStackTrace();

        }
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flcontent, myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();



    }
    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        Button moveToMap = (Button) findViewById(R.id.moveToMap);
        moveToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Heatmap.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }



//        AIzaSyCI_PKfCqvEwXWkVhQe6CCs1RRI1Uqj5cs

    public boolean isServiesOK(){
        Log.d(TAG, "isServersOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Heatmap.this);
        if (available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Heatmap.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        } else{
            Toast.makeText(this,"You can't make map request", Toast.LENGTH_SHORT).show();
        }
        return  false;
    }


}
