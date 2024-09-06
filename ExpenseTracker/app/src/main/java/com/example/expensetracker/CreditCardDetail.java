package com.example.expensetracker;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;



import java.util.ArrayList;

public class CreditCardDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditcarddetail);
        ImageButton calendarButton = findViewById(R.id.calendarButton);
        ImageButton goBackButton = findViewById(R.id.goBack);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the popup layout
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_layout, null);

                // Create the PopupWindow
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                View rootView = findViewById(android.R.id.content);

                // Show the PopupWindow centered vertically and horizontally
                popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

                // Handle close button click in popup
                Button closeButton = popupView.findViewById(R.id.closePopup);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(CreditCardDetail.this, addCreditCard.class);
                startActivity(intent);*/
            }
        });
        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> visitors = new ArrayList<>();
        visitors.add(new PieEntry(15,"Food"));
        visitors.add(new PieEntry(15,"Gas"));
        visitors.add(new PieEntry(25,"Transport"));
        visitors.add(new PieEntry(25,"Gym"));
        visitors.add(new PieEntry(10,"cloth"));
        visitors.add(new PieEntry(10,"healthcare"));

        PieDataSet pieDataSet = new PieDataSet(visitors,"Expenses");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#845ec2"));  // Darker OrangeRed for Food (end of gradient)

        colors.add(Color.parseColor("#d65db1"));  // Darker RoyalBlue for Gas

        colors.add(Color.parseColor("#ff6f91"));  // Darker ForestGreen for Transport

        colors.add(Color.parseColor("#ff9671"));  // Darker Orange for Gym

        colors.add(Color.parseColor("#ffc75f"));  // Darker DeepPink for Cloth

        colors.add(Color.parseColor("#f9f871"));  // Darker LightSeaGreen for Healthcare
        pieDataSet.setColors(colors);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.fira_sans);
        pieDataSet.setValueTypeface(typeface);
        PieData pieData = new PieData(pieDataSet);
        Legend legend = pieChart.getLegend();
        legend.setTextColor(Color.WHITE);         // Set legend text color to white
        legend.setTextSize(10f);
        pieChart.setData(pieData);
        pieChart.getLegend().setEnabled(true);    // Enable legend
        pieChart.setDrawEntryLabels(false);       // Disable labels on the chart slices

        pieChart.setDrawCenterText(false);
        pieChart.animate();
    }

}

