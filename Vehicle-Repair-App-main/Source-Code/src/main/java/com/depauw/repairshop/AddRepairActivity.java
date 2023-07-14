package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Repair;
import com.depauw.repairshop.database.Vehicle;
import com.depauw.repairshop.databinding.ActivityAddRepairBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddRepairActivity extends AppCompatActivity implements View.OnClickListener{

    // Member
    private ActivityAddRepairBinding binding;

    // Global
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRepairBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper helper = DBHelper.getInstance(this);
        List<Vehicle> vehicles = helper.getAllVehicles();

        List<String> spinnerDisplay = new ArrayList<>();

        for (Vehicle v : vehicles) {
                spinnerDisplay.add(v.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerDisplay);
        binding.spinnerVehicles.setAdapter(adapter);
        binding.edittextRepairDate.setOnClickListener(this);
        binding.buttonAddRepair.setOnClickListener(this);
    }

    // Get inputted date
    private DatePickerDialog.OnDateSetListener datepicker_repairdate_dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String displayDay = (dayOfMonth < 10) ? ("0" + dayOfMonth) : ("" + dayOfMonth);
            int newMonth = month + 1;
            String displayMonth = (newMonth < 10) ? ("0" + newMonth) : ("" + newMonth);
            String date = "" + year + "-" + displayMonth + "-" + displayDay;
            binding.edittextRepairDate.setText(date);
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.edittext_repair_date):{
                myCalendar = Calendar.getInstance();
                DatePickerDialog myPicker = new DatePickerDialog(this, datepicker_repairdate_dateSetListener, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                myPicker.show();
                break;
            }
            case (R.id.button_add_repair):{
                String date = binding.edittextRepairDate.getText().toString();
                double cost = Double.valueOf(binding.edittextRepairCost.getText().toString());
                String description = binding.edittextRepairDescription.getText().toString();

                long selected = binding.spinnerVehicles.getSelectedItemId();
                int vehicleID = (int) selected + 1;

                DBHelper helper = DBHelper.getInstance(this);
                Repair newRepair = new Repair(vehicleID,date, cost, description);
                long result = helper.insertRepair(newRepair);

                if(result>=0){
                    Toast.makeText(this, R.string.addRepair, Toast.LENGTH_LONG).show();
                }
                finish();
            }
        }
    }
}