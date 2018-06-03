package com.patang.agora;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.heatmaps.HeatmapTileProvider;


import android.graphics.Color;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


import android.*;
import android.Manifest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowCloseListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
//public  class superActivity extends

public class MapActivity extends AppCompatActivity implements  OnMarkerClickListener, OnMapReadyCallback{

    // variables for popup window
    private Marker mMarker;
    private PopupWindow mPopupWindow;
    private int mWidth;
    private int mHeight;
    //______________________________________

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;


    private GoogleMap mMap;

    Double lat[] = new Double[500];
    Double lng[] = new Double[500];
    Double temp[] = new Double[500];
    Double alpha[] = new Double[500];
    LatLng latLng[] = new LatLng[500];
    Graph tempGraph[] = new Graph[500];
    Graph humidityGraph[] = new Graph[500];
    int color[] = new int[500];
    //    String s = getIntent().getStringExtra("EXTRA_SESSION_ID");
    String selectedPara = "Temperature";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String s = getIntent().getStringExtra("EXTRA_SESSION_ID");
        String s2 = "2";
        if (s != null){
            char first = s.charAt(0);
//            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            if (first == '2') {
                selectedPara = "Humidity";
            } else {
                selectedPara = "Temperature";
            }
        }

        setContentView(R.layout.activity_map);

        getGraphData();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mDrawer = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);


//        Intent in=getIntent();
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            int value = bundle.getInt("myData", 2);
//            Log.v("in mainactivity", "" + value);
//            Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            case R.id.download:
                fragmentClass = Fragment3.class;
                break;
            default:
                fragmentClass = MapActivity.class;
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

    public void getGraphData(){
        FirebaseDatabase.getInstance().getReference().child("Graph").child(selectedPara).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 1;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Graph graph = snapshot.getValue(Graph.class);
                    tempGraph[i] = graph;
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        FirebaseDatabase.getInstance().getReference().child("Graph").child("Humidity").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                int i = 1;
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Graph graph = snapshot.getValue(Graph.class);
//                    humidityGraph[i] = graph;
//                    i++;
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    public int getColorFromReading(double temp, double alpha){
        if (temp > 30 && temp < 40) {
            return Color.argb((int)alpha * 255, 255, 255, 0);
        } else if (temp > 40){
            return Color.argb((int)alpha * 255, 255, 0, 0);
        } else {
            return Color.argb((int)alpha * 255, 0, 255, 0);
        }
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void interpolatePoints(LatLng p1, LatLng p2, LatLng p3, int color1, int color2, int color3){

        int max_latitude = Math.max(Math.max((int) (p1.latitude * 10000), (int) (p2.latitude * 10000)), (int) (p3.latitude * 10000));
        int min_latitude = Math.min(Math.min((int) (p1.latitude * 10000), (int) (p2.latitude * 10000)), (int) (p3.latitude * 10000));
        int max_longitude = Math.max(Math.max((int) (p1.longitude * 10000), (int) (p2.longitude * 10000)), (int) (p3.longitude * 10000));
        int min_longitude = Math.min(Math.min((int) (p1.longitude * 10000), (int) (p2.longitude * 10000)), (int) (p3.longitude * 10000));

        double x1 = ((p1.longitude * 10000) - min_longitude) / 10;
        double x2 = ((p2.longitude * 10000) - min_longitude) / 10;
        double x3 = ((p3.longitude * 10000) - min_longitude) / 10;
        double y1 = (max_latitude - (p1.latitude * 10000)) / 10;
        double y2 = (max_latitude - (p2.latitude * 10000)) / 10;
        double y3 = (max_latitude - (p3.latitude * 10000)) / 10;

        int heightBitmap = (max_latitude - min_latitude) / 10;
        int widthBitmap = (max_longitude - min_longitude) / 10;

        Bitmap myBitmap = Bitmap.createBitmap(widthBitmap, heightBitmap, Bitmap.Config.ARGB_8888);
        for (int x = 0; x < widthBitmap; x++) {
            for (int y = 0; y < heightBitmap; y++) {
                double weight1 = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) / ((y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3));
                double weight2 = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) / ((y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3));
                double weight3 = 1 - weight1 - weight2;

                int A1 = (int)((double)((color1 >> 24) & 0xff) * weight1);
                int R1 = (int)((double)((color1 >> 16) & 0xff) * weight1);
                int G1 = (int)((double)((color1 >>  8) & 0xff) * weight1);
                int B1 = (int)((double)((color1      ) & 0xff) * weight1);

                int A2 = (int)((double)((color2 >> 24) & 0xff) * weight2);
                int R2 = (int)((double)((color2 >> 16) & 0xff) * weight2);
                int G2 = (int)((double)((color2 >>  8) & 0xff) * weight2);
                int B2 = (int)((double)((color2      ) & 0xff) * weight2);

                int A3 = (int)((double)((color3 >> 24) & 0xff) * weight3);
                int R3 = (int)((double)((color3 >> 16) & 0xff) * weight3);
                int G3 = (int)((double)((color3 >>  8) & 0xff) * weight3);
                int B3 = (int)((double)((color3      ) & 0xff) * weight3);

                int A = A1 + A2 + A3;
                int R = R1 + R2 + R3;
                int B = B1 + B2 + B3;
                int G = G1 + G2 + G3;

                int color = (A & 0xff) << 24 | (R & 0xff) << 16 | (G & 0xff) << 8 | (B & 0xff);

                if ((weight1 >= 0 && weight1 <= 1) && (weight2 >= 0 && weight2 <= 1) && (weight3 >= 0 && weight3 <= 1)) {
                    //myBitmap.setPixel(x, y, (int)((double)color1*weight1) + (int)((double)color2*weight2) + (int)((double)color3*weight3) );
                    myBitmap.setPixel(x, y, color );
                    //myBitmap.setPixel(x, y, Barycentric(x, y, (int)x1, (int)y1, (int)x2, (int)y2, (int)x3, (int)y3, color1, color2, color3) );
                } else {
//                        myBitmap.setPixel(x, y, Color.argb(255, 255,255,255));
                }
            }
        }
        LatLngBounds PosBounds = new LatLngBounds(
                new LatLng(min_latitude * 0.0001, min_longitude * 0.0001),       // South west corner
                new LatLng(max_latitude * 0.0001, max_longitude * 0.0001));      // North east corner

        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(myBitmap);
        GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions().image(bitmapDescriptor).positionFromBounds(PosBounds)
                .transparency(0.5f);

        mMap.addGroundOverlay(groundOverlayOptions);
    }

    /** Called when the user clicks a marker. */


    public boolean onMarkerClick(final Marker marker) {

        int temp = Integer.parseInt(marker.getTitle());
        System.out.println("temp -> " + temp);
        Intent I = new Intent(MapActivity.this, Popup.class);
        Bundle b = new Bundle();
        int[] tempArr = new int[25];
        tempArr[1] = tempGraph[temp].hour01;
        tempArr[2] = tempGraph[temp].hour02;
        tempArr[3] = tempGraph[temp].hour03;
        tempArr[4] = tempGraph[temp].hour04;
        tempArr[5] = tempGraph[temp].hour05;
        tempArr[6] = tempGraph[temp].hour06;
        tempArr[7] = tempGraph[temp].hour07;
        tempArr[8] = tempGraph[temp].hour08;
        tempArr[9] = tempGraph[temp].hour09;
        tempArr[10] = tempGraph[temp].hour10;
        tempArr[11] = tempGraph[temp].hour11;
        tempArr[12] = tempGraph[temp].hour12;
        tempArr[13] = tempGraph[temp].hour13;
        tempArr[14] = tempGraph[temp].hour14;
        tempArr[15] = tempGraph[temp].hour15;
        tempArr[16] = tempGraph[temp].hour16;
        tempArr[17] = tempGraph[temp].hour17;
        tempArr[18] = tempGraph[temp].hour18;
        tempArr[19] = tempGraph[temp].hour19;
        tempArr[20] = tempGraph[temp].hour20;
        tempArr[21] = tempGraph[temp].hour21;
        tempArr[22] = tempGraph[temp].hour22;
        tempArr[23] = tempGraph[temp].hour23;
        tempArr[24] = tempGraph[temp].hour24;

        b.putIntArray("tempArr",tempArr);
        I.putExtras(b);

//
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int)(0*dm.widthPixels);
        int height = (int)(0.25*dm.heightPixels);
        View mainView = getLayoutInflater().inflate(R.layout.activity_database, null);

        PopupWindow popupWindow = new PopupWindow(mainView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.showAtLocation(findViewById(R.id.map), Gravity.CENTER_HORIZONTAL, width, height); //map is the fragment on the activity layout where I put the map
        Explode e = new Explode();
//        popupWindow.setEnterTransition(e);
//        popupWindow.setExitTransition(e);

        LineChart mChart = mainView.findViewById(R.id.Linechart);


        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();
        for(int i = 1; i<25;i++){
            yValues.add(new Entry(i, tempArr[i]));
        }
        LineDataSet set1 = new LineDataSet(yValues, "");

        set1.setFillAlpha(110);
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        set1.setDrawFilled(true);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //set1.setDrawValues(false);
        set1.setFillAlpha(150);
        set1.setDrawCircles(false);
        set1.setValueTextColor(Color.parseColor("#ffffff"));
        set1.setColor(Color.parseColor("#ffffff"));


        //set1.setFillColor(16010050);
        set1.setFillColor(0x740141);
        // set1.setFillDrawable(gradientDrawable);

        mChart.setData(data);


        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json);
        googleMap.setMapStyle(style);

        mMap = googleMap;

        LatLng p15 = new LatLng(31.5204, 74.3587);
//        mMap.addMarker(new MarkerOptions().position(p15).title("p15"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p15,12.0f));



//        LatLng p1 = new LatLng(31.4704, 74.4108);
//        LatLng p2 = new LatLng(31.5799, 74.3561);
//        LatLng p3 = new LatLng(31.5578, 74.3624);
//        LatLng p11 = new LatLng(31.4845, 74.2974);
//        LatLng p10 = new LatLng(31.4711, 74.24192);
//        LatLng p5 = new LatLng(31.476, 74.3045);
////        LatLng p15 = new LatLng(33.8898, 73.4318);
//        LatLng p13 = new LatLng(31.604948, 74.572325);
//        LatLng p8 = new LatLng(31.6001, 74.3397);
//        LatLng p4 = new LatLng(31.4626, 74.3309);
//        LatLng p6 = new LatLng(31.5879, 74.3151);
//        LatLng p14 = new LatLng(31.579, 74.3058);
//        LatLng p7 = new LatLng(31.5402, 74.3954);
//        LatLng p9 = new LatLng(31.4777, 74.3294);
//        LatLng p12 = new LatLng(31.5351, 74.3206);


//        int color1  = Color.argb(255,255,0,0);
//        int color2  = Color.argb(0,255,0,0);
//        int color3  = Color.argb(255,255,0,0);
//        int color4  = Color.argb(0,255,0,0);
//        int color5  = Color.argb(255,255,0,0);
//        int color6  = Color.argb(0,255,0,0);
//        int color7  = Color.argb(255,255,0,0);
//        int color8  = Color.argb(0,255,0,0);
//        int color9  = Color.argb(255,255,0,0);
//        int color10  = Color.argb(0,255,0,0);
//        int color11  = Color.argb(255,255,0,0);
//        int color12  = Color.argb(0,0,255,0);
//        int color13  = Color.argb(255,0,255,0);
//        int color14  = Color.argb(0,255,0,0);
//        int color15  = Color.argb(255,0,255,0);
//        int color16  = Color.argb(0,255,255,0);

//
//        interpolatePoints(p10,p11,p5,color10,color11,color5);
//        interpolatePoints(p14,p6,p12,color14,color6,color12);
//        interpolatePoints(p12,p6,p8,color12,color6,color8);
//        interpolatePoints(p8,p12,p2,color8,color12,color2);
//        interpolatePoints(p3,p12,p3,color3,color12,color3);
//        interpolatePoints(p3,p12,p15,color3,color12,color15);
//        interpolatePoints(p15,p3,p7,color15,color3,color7);
//        interpolatePoints(p15,p7,p1,color15,color7,color1);
//        interpolatePoints(p1,p15,p9,color1,color15,color9);
//        interpolatePoints(p9,p4,p1,color9,color4,color1);
//        interpolatePoints(p5,p9,p4,color5,color9,color4);
//        interpolatePoints(p11,p5,p15,color11,color5,color15);
//        interpolatePoints(p15,p11,p12,color15,color11,color12);
//        interpolatePoints(p12,p3,p2,color12,color3,color2);
//        interpolatePoints(p10,p5,p4,color10,color5,color4);
//        interpolatePoints(p15,p5,p9,color15,color5,color9);
//        interpolatePoints(p11,p12,p10,color11,color12,color10);
//        interpolatePoints(p10,p12,p14,color10,color12,color14);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//

//        color[1]  = Color.argb(255,255,0,0);
//        color[2]  = Color.argb(255,255,0,0);
//        color[3]  = Color.argb(255,255,0,0);
//        color[4]  = Color.argb(255,255,0,0);
//        color[5]  = Color.argb(255,255,0,0);
//        color[6]  = Color.argb(255,255,0,0);
//        color[7]  = Color.argb(255,255,0,0);
//        color[8]  = Color.argb(255,255,0,0);
//        color[9]  = Color.argb(255,255,0,0);
//        color[10]  = Color.argb(255,255,0,0);
//        color[11]  = Color.argb(255,255,0,0);
//        color[12]  = Color.argb(255,0,255,0);
//        color[13]  = Color.argb(255,0,255,0);
//        color[14]  = Color.argb(255,255,0,0);
//        color[15]  = Color.argb(255,0,255,0);
//        color[16]  = Color.argb(255,255,255,0);

        FirebaseDatabase.getInstance().getReference().child("Reading").child(selectedPara).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer i = 1;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for(DataSnapshot snap : snapshot.getChildren()){
                        Reading reading = snap.getValue(Reading.class);
//                        System.out.println(i + " alpha :" + reading.alpha);
//                        System.out.println(i + " reading :" + reading.Reading);
                        alpha[i] = reading.alpha;
                        temp[i] = reading.Reading;
                    }
                    i++;
                }
                for (int j = 1; j < i; j++) {
                    color[j] = getColorFromReading(temp[j],alpha[j]);
                }

                FirebaseDatabase.getInstance().getReference().child("Sensor").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Integer i = 1;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Sensor sensor = snapshot.getValue(Sensor.class);
//                            System.out.println(i + " lat -> " + sensor.Latitude);
//                            System.out.println(i + " lng -> " + sensor.Longitude);
                            lat[i] = sensor.Latitude;
                            lng[i] = sensor.Longitude;
                            latLng[i] = new LatLng(sensor.Latitude,sensor.Longitude);
                            i++;
                        }

                        mMap.clear();
                        for (int j = 1; j < 16; j++) {
//                            mMap.addMarker(new MarkerOptions().position(latLng[j]).title("Pos: " + j + ", Lat: " + latLng[j].latitude + ", Lng: " + latLng[j].longitude));

                            MarkerOptions mo = new MarkerOptions().position(latLng[j]).title(String.valueOf(j));
//                            mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.target));
                            mMap.addMarker(mo);

                        }

                        FirebaseDatabase.getInstance().getReference().child("Regions").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Regions regions = snapshot.getValue(Regions.class);
//                                    System.out.println(i + " Node1 -> " + regions.Node1);
//                                    System.out.println(i + " Node2 -> " + regions.Node2);
//                                    System.out.println(i + " Node3 -> " + regions.Node3);
                                    interpolatePoints(latLng[regions.Node1],latLng[regions.Node2],latLng[regions.Node3],color[regions.Node1],color[regions.Node2],color[regions.Node3]);
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }});
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }});
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);


//        mMap.OnCameraMoveListener(this);

//        public abstract void onCameraMove (){}
//        onCameraMove();
//        onCameraMoveStarted();

























    }

}