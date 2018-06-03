package com.patang.agora;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.transition.Explode;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Popup extends MapActivity {
    private static final String TAG = "MainActivity";
    private LineChart mChart;
    private Drawable gradientDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        mChart = findViewById(R.id.Linechart);
        Bundle bundle = getIntent().getExtras();
        int arrayReceived[] = bundle.getIntArray("tempArr");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = (int)(0.7*dm.widthPixels);
        int height = (int)(0.45*dm.heightPixels);
        getWindow().setLayout(width,height);
        getWindow().getAttributes().verticalMargin = 0.23F;

//                            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(width,height);
//                            View imageView = findViewById(R.id.imageView);
//                            imageView.requestLayout();
//                            imageView.setLayoutParams(params);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();
        for(int i = 1; i<25;i++){
            yValues.add(new Entry(i, arrayReceived[i]));
        }

//                            yValues.add(new Entry(1, graph.hour01));
//                            yValues.add(new Entry(2, graph.hour02));
//                            yValues.add(new Entry(3, graph.hour03));
//                            yValues.add(new Entry(4, graph.hour04));
//                            yValues.add(new Entry(5, graph.hour05));
//                            yValues.add(new Entry(6, graph.hour06));
//                            yValues.add(new Entry(7, graph.hour07));
//                            yValues.add(new Entry(8, graph.hour08));
//                            yValues.add(new Entry(9, graph.hour09));
//                            yValues.add(new Entry(10, graph.hour10));
//                            yValues.add(new Entry(11, graph.hour11));
//                            yValues.add(new Entry(12, graph.hour12));
//                            yValues.add(new Entry(13, graph.hour13));
//                            yValues.add(new Entry(14, graph.hour14));
//                            yValues.add(new Entry(15, graph.hour15));
//                            yValues.add(new Entry(16, graph.hour17));
//                            yValues.add(new Entry(17, graph.hour17));
//                            yValues.add(new Entry(18, graph.hour18));
//                            yValues.add(new Entry(19, graph.hour19));
//                            yValues.add(new Entry(20, graph.hour20));
//                            yValues.add(new Entry(21, graph.hour21));
//                            yValues.add(new Entry(22, graph.hour22));
//                            yValues.add(new Entry(23, graph.hour23));
//                            yValues.add(new Entry(24, graph.hour24));
        LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");

        set1.setFillAlpha(110);
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        dataSets.add(set1);
        LineData data = new LineData(dataSets);
        set1.setDrawFilled(true);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //set1.setDrawValues(false);
        set1.setFillAlpha(255);
        set1.setDrawCircles(false);

        //set1.setFillColor(16010050);
        //set1.setFillColor(7401417);
        // set1.setFillDrawable(gradientDrawable);

        mChart.setData(data);


    }
}
