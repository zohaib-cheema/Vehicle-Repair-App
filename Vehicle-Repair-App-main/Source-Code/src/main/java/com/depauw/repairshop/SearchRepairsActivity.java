package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.databinding.ActivitySearchRepairsBinding;

import java.util.List;

public class SearchRepairsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    // Member
    private ActivitySearchRepairsBinding binding;

    // Global
    DBHelper helper = DBHelper.getInstance(this);
    List<RepairWithVehicle> repairWithVehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchRepairsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonFindRepairs.setOnClickListener(this);
        binding.listviewResults.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case(R.id.button_find_repairs):{
                String str = binding.edittextSearchPhrase.getText().toString();
                if(str==null){
                    repairWithVehicles = helper.getRepairsWithVehicle(" ");
                }
                else{
                    repairWithVehicles = helper.getRepairsWithVehicle(str);
                }
                CustomAdapter adapter = new CustomAdapter(this, repairWithVehicles);
                binding.listviewResults.setAdapter(adapter);
                break;
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long l) {
        RepairWithVehicle vehicleRepairID = (RepairWithVehicle) parent.getItemAtPosition(position);
        int id = vehicleRepairID.getRepair().getRid();
        int result = helper.deleteRepair(id);

        if(result>0){
            Toast.makeText(this, R.string.deleteRepair, Toast.LENGTH_LONG).show();

            repairWithVehicles.clear();
            String str = binding.edittextSearchPhrase.getText().toString();
            repairWithVehicles = helper.getRepairsWithVehicle(str);

            CustomAdapter adapter = new CustomAdapter(this, repairWithVehicles);
            binding.listviewResults.setAdapter(adapter);
            binding.listviewResults.invalidateViews();
        }
        return true;
    }
}