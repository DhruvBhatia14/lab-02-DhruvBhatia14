package com.example.listycity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    private boolean deleteMode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button addButton = (Button) findViewById(R.id.addButton);
        cityList = findViewById(R.id.city_list);

        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi" };
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
                if (deleteMode) {
                    dataList.remove(paramInt);
                    cityAdapter.notifyDataSetChanged();
                    deleteMode = false;
                }
            }
        });

        addButton.setOnClickListener(v -> {
            EditText typedInput = new EditText(MainActivity.this);
            new android.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Type in the city you want to add")
                    .setView(typedInput)
                    .setPositiveButton("Done", (dialog, which) -> {
                        String enteredCity = typedInput.getText().toString().trim();
                        boolean preExisting = false;
                        for (String city: dataList) {
                            if (city.equalsIgnoreCase(enteredCity)) {
                                preExisting = true;
                                break;
                            }
                        }
                        if (!enteredCity.isEmpty() && !preExisting) {
                            dataList.add(enteredCity);
                            cityAdapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        deleteButton.setOnClickListener(v -> {
            deleteMode = true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}