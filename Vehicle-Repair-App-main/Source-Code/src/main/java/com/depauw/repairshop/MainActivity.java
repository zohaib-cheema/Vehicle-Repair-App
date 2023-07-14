package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.depauw.repairshop.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAddVehicle.setOnClickListener(this);
        binding.buttonAddRepair.setOnClickListener(this);
        binding.buttonSearchRepairs.setOnClickListener(this);
    }

    public void beginIntent(Context context, Class open){
        Intent myIntent = new Intent(context , open);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.button_add_vehicle):{
                beginIntent(this, AddVehicleActivity.class);
                break;
            }
            case(R.id.button_add_repair):{
                beginIntent(this, AddRepairActivity.class);
                break;
            }
            case(R.id.button_search_repairs):{
                beginIntent(this, SearchRepairsActivity.class);
                break;
            }
        }
    }
}