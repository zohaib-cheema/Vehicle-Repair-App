package com.depauw.repairshop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.depauw.repairshop.database.DBHelper;
import com.depauw.repairshop.database.Vehicle;
import com.depauw.repairshop.databinding.ActivityAddVehicleBinding;

public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityAddVehicleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVehicleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAddVehicle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.button_add_vehicle):{
                int year =  Integer.valueOf(binding.edittextYear.getText().toString());
                String makeAndModel = binding.edittextMakeModel.getText().toString();
                double purchasePrice = Double.valueOf(binding.edittextPrice.getText().toString());
                int isNew = (binding.checkboxIsNew.isChecked()) ? 1 : 0;

                DBHelper helper = DBHelper.getInstance(this);
                Vehicle addVehicle = new Vehicle(year, makeAndModel, purchasePrice, isNew);

                long result = helper.insertVehicle(addVehicle);
                if(result>0){
                    Toast.makeText(this, R.string.addVehicle, Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
        finish();
    }
}