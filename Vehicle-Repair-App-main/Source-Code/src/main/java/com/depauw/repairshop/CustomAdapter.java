package com.depauw.repairshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<RepairWithVehicle> vehicleRepairs;

    public CustomAdapter(Context context, List<RepairWithVehicle> vehicleRepairs) {
        this.context = context;
        this.vehicleRepairs = vehicleRepairs;
    }

    @Override
    public int getCount() { return vehicleRepairs.size();}

    @Override
    public Object getItem(int i) {
        return vehicleRepairs.get(i);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_results_row, parent, false);
        }
        RepairWithVehicle rv = vehicleRepairs.get(position);

        TextView makeAndModel = convertView.findViewById(R.id.text_year_make_model);
        TextView repairDate = convertView.findViewById(R.id.text_repair_date);
        TextView repairCost = convertView.findViewById(R.id.text_repair_cost);
        TextView repairDescription = convertView.findViewById(R.id.text_repair_description);

        makeAndModel.setText(rv.getVehicle().toString());
        repairDate.setText(rv.getRepair().getDate());
        repairCost.setText(String.valueOf("$ " + rv.getRepair().getCost()));
        repairDescription.setText(rv.getRepair().getDescription());

        return convertView;
    }
}
