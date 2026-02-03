package com.example.listcity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Declare all variables
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    EditText cityInput;
    Button addButton, deleteButton;

    // Tracks which city is selected for deletion
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize Views
        cityList = findViewById(R.id.city_list);
        cityInput = findViewById(R.id.editText_name);
        addButton = findViewById(R.id.button_add);
        deleteButton = findViewById(R.id.button_delete);

        // 2. Setup Data
        String[] cities = {"Edmonton", "Vancouver", "Toronto"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        // 3. Setup Adapter (Make sure R.layout.content exists!)
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // 4. ADD BUTTON LOGIC
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCity = cityInput.getText().toString();
                if (!newCity.isEmpty()) {
                    dataList.add(newCity);
                    cityAdapter.notifyDataSetChanged(); // Refreshes the list
                    cityInput.setText(""); // Clears input
                }
            }
        });

        // 5. LIST SELECTION LOGIC (Required for delete)
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position; // Save which item was tapped
                Toast.makeText(MainActivity.this, "Selected: " + dataList.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        // 6. DELETE BUTTON LOGIC
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != -1 && selectedPosition < dataList.size()) {
                    dataList.remove(selectedPosition);
                    cityAdapter.notifyDataSetChanged();
                    selectedPosition = -1; // Reset selection
                } else {
                    Toast.makeText(MainActivity.this, "Please select a city first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
